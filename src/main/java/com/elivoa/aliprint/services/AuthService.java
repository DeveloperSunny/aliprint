package com.elivoa.aliprint.services;

import java.util.List;
import java.util.Map;

import com.alibaba.openapi.client.AlibabaClient;
import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.data.OrderStatus;
import com.elivoa.aliprint.data.Params;
import com.elivoa.aliprint.entity.AliOldOrder;
import com.elivoa.aliprint.entity.AliOldResult;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliProduct;
import com.elivoa.aliprint.entity.AliResult;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;

public interface AuthService {

	AlibabaClient client();

	boolean authenticate(AliToken token) throws NeedAuthenticationException;

	/**
	 * @return 登陆的阿里巴巴账号，用这个来进一步取得库里面缓存的refresh_token。返回Null表示没有账号已经登陆。
	 */
	String getSignedAliAccount();

	/**
	 * 用临时code来认证。一般由 /Authorization 入口进来。
	 */
	void authorize(AliToken token, String code);

	/**
	 * Get access token, refresh token and their expire times from database via account name.
	 * 
	 * @param account
	 * @return tokens, if null means no stored token found. need re-authenticate again.
	 */
	AliToken getTokenFromDB(String account);

	void storeToken(AliToken token);

	void refreshToken(AliToken token);

	Object getAccountInfo(AliToken token, String memberId);

	// switch between ids.

	String changeToMemberId(String loginId);

	Map<String, String> changeToMemberIds(String... loginIds);

	Map<String, String> changeToMemberIds(List<String> loginIds);

	// list order
	AliResult<AliOrder> listOrders(AliToken token, OrderStatus status, int pagesize, int page, Params params,
			boolean withPrivateAddress, boolean withRightMemo);

	AliOrder getOrder(AliToken token, long orderId, Params params);

	AliOldResult<AliOldOrder> oldListOrders(AliToken token, OrderStatus status, int pagesize, int page, Params params);

	AliOldResult<AliProduct> listProducts(AliToken token, int pagesize, int page, Params params);

	// 发货

	boolean sendAll(AliToken token, Long orderId, String logisticsBillNo);

	boolean send(AliToken token, String memberId, Long orderId, String orderEntryIds, String logisticsBillNo);

}
