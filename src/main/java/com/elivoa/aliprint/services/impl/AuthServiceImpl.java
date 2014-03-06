package com.elivoa.aliprint.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.elivoa.aliprint.alisdk.AliSDK;
import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.dal.TokenDao;
import com.elivoa.aliprint.data.APIResponse;
import com.elivoa.aliprint.data.OrderStatus;
import com.elivoa.aliprint.data.Params;
import com.elivoa.aliprint.entity.AliOldResult;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliProduct;
import com.elivoa.aliprint.entity.AliResult;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;
import com.elivoa.aliprint.services.AuthService;
import com.google.common.collect.Lists;

/**
 * <code>

Authentication Step;

1. User open homepage of AliPrint.
2. Read Alibaba cookie (or use api) to get which user is signed in .
3. If no use is signed in, jump to authenticated page. (In development phrase use test page.) >>

4. If signed in, use ali-account to get Access Token from my db.
5. Try to access the authenticated API, 
 If success, pass the authentication. >> exit
 If not success, jump to Authentication page. >>

6. Authentication page: Open the authentication page;
7. After use input the account name and password, we can receive the temp code.
8. Use temp code to get Access Token;
9. Store the Access Token and username pair into Database.
10. Return success page to proceed normal task.

   </code>
 * 
 * 
 */
public class AuthServiceImpl implements AuthService {

	private AlibabaClient client;

	public AlibabaClient client() {
		return this.client;
	}

	// services

	@Inject
	TokenDao tokenDao;

	// constructures

	public AuthServiceImpl(
			//
			@Inject @Symbol("com.elivoa.aliprint.appkey") String appkey,
			@Inject @Symbol("com.elivoa.aliprint.securitykey") String securitykey) {
		ClientPolicy policy = ClientPolicy.getDefaultChinaAlibabaPolicy();
		policy = policy.setAppKey(appkey).setSigningKey(securitykey);
		client = new AlibabaClient(policy);
		client.start();
	}

	/**
	 * Take the responsibility to keep token update to date.
	 * 
	 * @return false if need to redirect to authorization page.
	 * @return true if passed.
	 */
	public boolean authenticate(AliToken token) throws NeedAuthenticationException {
		assert null != token;

		// if access token can use, then return directly;
		if (null != token && token.isAccessTokenAvailable()) {
			return true; // pass, and do nothing.
		}

		// if not, get user from cookie. Then get token from database;
		String signedAccount = getSignedAliAccount();
		if (null == signedAccount) {
			// if no user is in cookie, jump to authorization page.
			return false; // need auth;
		}

		AliToken savedToken = getTokenFromDB(signedAccount);
		if (null == token) {
			// if no token in database, jump to authorization page.
			return false; // need auth;
		}
		token.updateAll(savedToken); // set info into token;

		// check access token availability again.
		if (token.isAccessTokenAvailable()) {
			return true;
		} else if (token.isRefreshTokenAvailable()) {
			// use refresh token to change access token;
			refreshToken(token);
			if (token.isAccessTokenAvailable()) {
				return true;
			} else {
				return false; // access token is not available after refresh token. return to auth
								// page.
			}
			// if any error occured, goto error page. (e.g.: after half a year)
		}
		// default return false;
		return false;
	}

	// use temp code to get refresh code and access code.
	public void authorize(AliToken token, String code) {
		assert null != token;
		AuthorizationToken authorizationToken = client.getToken(code);
		token.setToken(authorizationToken);
		this.storeToken(token);
	}

	// use refresh code to change access code.
	public void refreshToken(AliToken token) {
		if (null != token && null != token.getToken()) {
			AuthorizationToken authorizationToken = client.refreshToken(token.getToken().getRefresh_token());
			token.setToken(authorizationToken);
			this.storeToken(token);
		}
	}

	/**
	 * Fake this, return null to test redirect to login page, and return "My account" to use it.
	 */
	public String getSignedAliAccount() {
		// fake return null;
		// return null;

		// fake return my account
		return "木子针织";
	}

	public void storeToken(AliToken token) {
		try {
			tokenDao.saveToken(token);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public AliToken getTokenFromDB(String account) {
		AliToken token = null;
		try {
			token = tokenDao.getTokenByOwner(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return token;
	}

	// get date API

	public Object getAccountInfo(AliToken token, String memberId) {

		Request apiRequest = new Request("cn.alibaba.open", "member.get", 1);
		apiRequest.setParam("memberId", memberId);
		apiRequest.setAccessToken(token.accessToken());
		Object result = null;
		try {
			result = client.send(apiRequest, null, AliSDK.authPolicy());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;

	}

	// get cached memberId;
	public String getMemberId(AliToken token) {
		if (null != token) {
			if (token.getMemberId() == null) {
				String memberId = changeToMemberId(token.getLoginId());
				token.setMemberId(memberId);
				// TODO save member id to database;
			}
		}
		return token.getMemberId();
	}

	// change to MemberId by LoginId / (ResourceOwner in AuthorizationToken)
	public String changeToMemberId(String loginId) {
		Map<String, String> map = changeToMemberIds(loginId);
		if (null != map) {
			return map.get(loginId);
		}
		return null;
	}

	public Map<String, String> changeToMemberIds(String... loginIds) {
		List<String> loginIdList = Lists.newArrayList();
		for (String loginId : loginIds) {
			loginIdList.add(loginId);
		}
		return changeToMemberIds(loginIdList);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> changeToMemberIds(List<String> loginIds) {
		Request apiRequest = new Request("cn.alibaba.open", "convertMemberIdsByLoginIds", 1);
		apiRequest.setParam("loginIds", loginIds);
		// apiRequest.setAccessToken(token.accessToken());
		try {
			Object result = client.send(apiRequest, null, AliSDK.noAuthPolicy());
			if (null != result && result instanceof Map) {
				return (Map<String, String>) result;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	// list orders
	// TODO 买家id和卖家id必须填一个。
	public AliResult<AliOrder> listOrders(AliToken token, OrderStatus status, int pagesize, int page, Params params) {
		// 1. get member ID, this api is cached. TODO supply an api that force refresh memberID if
		// anything is wrong;

		Request req = new Request("cn.alibaba.open", "trade.order.list.get", 2);
		params = Params.init(params);

		String memberId = this.getMemberId(token);
		params.add("sellerMemberId", memberId);

		if (null != status) {
			params.add("orderStatusEnum", status.toString());
		}
		params.add("pageSize", pagesize); // 33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
		params.add("page", page);
		Params.injectParameters(req, params);

		// 返回的结果一般是合法的json串，用户需要自己处理
		try {
			req.setAccessToken(token.accessToken());
			APIResponse resp = APIResponse.warp(client.send(req, null, AliSDK.authPolicy()));
			return AliResult.newOrderListResult(resp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	public AliOldResult<AliProduct> listProducts(AliToken token, int pagesize, int page, Params params) {
		Request req = new Request("cn.alibaba.open", "offer.getAllOfferList", 1);
		req.setParam("sellerMemberId", token.getMemberId());
		req.setParam("type", "SALE");
		req.setParam("returnFields", new String[] { "offerId", "detailsUrl", "offerStatus", "subject",
				"qualityLevel", "productUnitWeight", "imageListdd " });
		// 不好用的属性："unitPrice"

		// if (null != status) {
		// req.setParam("orderStatus", status.toString());
		// }
		req.setParam("pageSize", pagesize);
		req.setParam("page", page);
		Params.injectParameters(req, params);

		try {
			req.setAccessToken(token.accessToken());
			APIResponse resp = APIResponse.warp(client.send(req, null, AliSDK.authPolicy()));
			System.out.println(resp.data);
			return AliOldResult.newProductListResult(resp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}
}
