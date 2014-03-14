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
import com.beust.jcommander.internal.Maps;
import com.elivoa.aliprint.alisdk.AliSDK;
import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.dal.AgentDao;
import com.elivoa.aliprint.dal.ProductDao;
import com.elivoa.aliprint.dal.TokenDao;
import com.elivoa.aliprint.data.APIResponse;
import com.elivoa.aliprint.data.OrderStatus;
import com.elivoa.aliprint.data.Params;
import com.elivoa.aliprint.entity.AliOldOrder;
import com.elivoa.aliprint.entity.AliOldResult;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliOrderEntity;
import com.elivoa.aliprint.entity.AliProduct;
import com.elivoa.aliprint.entity.AliResult;
import com.elivoa.aliprint.entity.ProductAlias;
import com.elivoa.aliprint.entity.SellAgent;
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

	@Inject
	ProductDao productDao;

	@Inject
	AgentDao agentDao;

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
		// System.out.println(result);
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

	/**
	 * List orders with full information.
	 * 
	 * @param withPrivateAddress
	 *            use old API to fetch order list again to gain private address information which
	 *            used in address printing.
	 * @param withUserMemo
	 *            use another API for each order to fetch user memo, including buyer's private
	 *            memo.(here must be a bug.)
	 * @param params__withAlias
	 *            get product alias from database;(for short)
	 * @param params__withSenderInfo
	 *            专为代发考虑，发件人信息，电话以及自定义信息；
	 */
	public AliResult<AliOrder> listOrders(AliToken token, OrderStatus status, int pagesize, int page, Params params,
			boolean withPrivateAddress, boolean withUserMemo) {
		Request req = new Request("cn.alibaba.open", "trade.order.list.get", 2);
		params = Params.init(params);

		String memberId = this.getMemberId(token);
		params.add("sellerMemberId", memberId);

		if (null != status) {
			params.add("orderStatusEnum", status.toString());
		}
		params.add("pageSize", pagesize);
		params.add("page", page);
		Params.injectParameters(req, params);

		try {
			req.setAccessToken(token.accessToken());
			APIResponse resp = APIResponse.warp(client.send(req, null, AliSDK.authPolicy()));
			AliResult<AliOrder> result = AliResult.newOrderListResult(resp);
			if (null == result) {
				return null;
			}
			// extra data;
			if (withPrivateAddress) {
				fillPrivateAddress(token, result.getModels(), status, pagesize, page, null);
			}
			if (withUserMemo) {
				overrideOrderMemo(token, result);
			}
			if (params.getBoolean("@withAlias")) {
				this.fillAlias(result.getModels());
			}

			// get sender info.
			if (params.getBoolean("@withSenderInfo")) {
				this.fillSenderInfo(result.getModels());
			}

			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void fillPrivateAddress(AliToken token, List<AliOrder> models, OrderStatus status, int pagesize,
			int page, Params params) {
		AliOldResult<AliOldOrder> oldListOrders = this.oldListOrders(token, status, pagesize, page, params);
		// copy address info into the list.
		// copyAddressIntoNewList(result, oldListOrders);
		if (null != oldListOrders) {
			for (AliOldOrder oldOrder : oldListOrders.getReturns()) {
				for (AliOrder order : models) {
					// order id matches
					if (oldOrder.getId() == order.getId()) {
						order.setToFullName(oldOrder.getToFullName());
						order.setToMobile(oldOrder.getToMobile());
						order.setToArea(oldOrder.getToArea());
						order.setToPost(oldOrder.getToPost());
					}
				}
			}
		}

	}

	private void overrideOrderMemo(AliToken token, AliResult<AliOrder> result) {
		if (null != result) {
			for (AliOrder order : result.getModels()) {
				AliOrder newOrder = this.getOrder(token, order.getId(), Params.create("needOrderMemoList", true));
				if (null != newOrder) {
					order.setOrderMemoList(newOrder.getOrderMemoList());
					order.setSellerOrderMemo(newOrder.getSellerOrderMemo());
					order.setBuyerOrderMemo(newOrder.getBuyerOrderMemo());
				}
			}
		}
	}

	private void fillSenderInfo(AliOrder... orders) {
		fillSenderInfo(Lists.newArrayList(orders));
	}

	private void fillSenderInfo(List<AliOrder> orders) {
		Map<String, SellAgent> map = Maps.newHashMap();
		for (AliOrder order : orders) {
			map.put(order.getBuyerLoginId(), null);
		}
		try {
			List<SellAgent> agents = agentDao.getAgents(map.keySet());
			if (null != agents) {
				for (SellAgent agent : agents) {
					map.put(agent.getAliid(), agent);
				}
			}
			for (AliOrder order : orders) {
				SellAgent agent = map.get(order.getBuyerLoginId());
				if (null != agent) {
					order.setSellAgent(agent);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillAlias(AliOrder... orders) {
		fillAlias(Lists.newArrayList(orders));
	}

	private void fillAlias(List<AliOrder> orders) {
		// fill map with ids;
		Map<Long, ProductAlias> map = Maps.newHashMap();
		for (AliOrder order : orders) {
			List<AliOrderEntity> entities = order.getEntities();
			if (null != entities) {
				for (AliOrderEntity entity : entities) {
					map.put(entity.getSourceId(), null);
				}
			}
		}

		try {
			List<ProductAlias> aliaslist = productDao.getProductAlias(map.keySet());
			if (null != aliaslist) {
				for (ProductAlias alias : aliaslist) {
					map.put(alias.getId(), alias);
				}
			}
			for (AliOrder order : orders) {
				List<AliOrderEntity> entities = order.getEntities();
				if (null != entities) {
					for (AliOrderEntity entity : entities) {
						ProductAlias alias = map.get(entity.getSourceId());
						if (null != alias) {
							entity.setAlias(alias.getAlias());
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public AliOrder getOrder(AliToken token, long orderId, Params params) {
		Request req = new Request("cn.alibaba.open", "trade.order.detail.get", 1);
		params = Params.init(params);
		params.add("id", orderId);
		Params.injectParameters(req, params);
		// id Long 是 订单号
		// needOrderEntries boolean 否 是否需要订单明细 true
		// needInvoiceInfo boolean 否 是否需要发票信息 true
		// needOrderMemoList boolean 否 是否需要订单备注 true
		// needLogisticsOrderList boolean 否 是否需要物流单信息
		try {
			req.setAccessToken(token.accessToken());
			APIResponse resp = APIResponse.warp(client.send(req, null, AliSDK.authPolicy()));
			AliOrder order = new AliOrder(resp.getResp("orderModel"));

			if (params.getBoolean("@withFullAddress")) {
				List<AliOrder> ordersInstead = Lists.newArrayList();
				ordersInstead.add(order);
				fillPrivateAddress(token, ordersInstead, null, 1, 1, Params.create().add("orderId", orderId));
			}

			if (params.getBoolean("@withAlias")) {
				this.fillAlias(order);
			}

			// get sender info.
			if (params.getBoolean("@withSenderInfo")) {
				this.fillSenderInfo(order);
			}
			return order;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	public AliOldResult<AliOldOrder> oldListOrders(AliToken token, OrderStatus status, int pagesize, int page,
			Params params) {
		Request req = new Request("cn.alibaba.open", "trade.order.orderList.get", 1);
		req.setParam("sellerMemberId", token.getMemberId());
		if (null != status) {
			req.setParam("orderStatus", status.toOldStatusString());
		}
		req.setParam("pageSize", pagesize);
		req.setParam("pageNO", page);
		Params.injectParameters(req, params);
		try {
			req.setAccessToken(token.accessToken());
			APIResponse resp = APIResponse.warp(client.send(req, null, AliSDK.authPolicy()));
			AliOldResult<AliOldOrder> result = AliOldResult.newOrderListResult(resp);
			return result; // System.out.println(resp.data);
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
		req.setParam("orderBy", "gmt_modified:asc");
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
