package com.elivoa.aliprint.alisdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.RequestPolicy;

public class AliSDK {

	private static RequestPolicy basePolicy = new RequestPolicy().setContentCharset("UTF-8").setTimeout(6000);

	// private static RequestPolicy authPolicy;

	public static RequestPolicy authPolicy() {
		RequestPolicy authPolicy = basePolicy.clone();
		authPolicy.setNeedAuthorization(true).setUseSignture(true);
		return authPolicy;
	}

	public static RequestPolicy noAuthPolicy() {
		return basePolicy.clone();
	}

	// ///////////////////////////
	@Deprecated
	public static AliSDK create(String appkey, String securitykey, String code) throws InterruptedException,
			ExecutionException, TimeoutException {
		// 第一步：初始化AlibabaClient
		ClientPolicy policy = ClientPolicy.getDefaultChinaAlibabaPolicy();
		policy = policy.setAppKey(appkey).setSigningKey(securitykey);
		AlibabaClient client = new AlibabaClient(policy);
		client.start();

		// 第二步：调用getToken或者refreshToken方法获取accessToken

		AuthorizationToken authorizationToken = client.getToken(code);

		// 或者：
		// AuthorizationToken authorizationToken = client.refreshToken(YOUR_REFRESH_TOKEN);

		// 第三步：调用send方法实现api 的调用

		// 1、调用开放数据，无需授权
		RequestPolicy noAuthPolicy = basePolicy.clone();

		Object result = client.send(new Request("system", "currentTime"), null, noAuthPolicy);
		System.out.println(result);

		// 2、调用隐私数据，需要用户授权
		RequestPolicy testPolicy = basePolicy.clone();
		testPolicy.setNeedAuthorization(true).setUseSignture(true);
		Request apiRequest = new Request("cn.alibaba.open", "trade.order.orderDetail.get", 1);
		apiRequest.setParam("orderId", "545016659020595");
		apiRequest.setAccessToken(authorizationToken.getAccess_token());
		Object orderDetail1 = client.send(apiRequest, null, testPolicy);

		System.out.println(orderDetail1);
		// 第四步：释放AlibabaClient

		if (client != null) {
			client.shutdown();
		}
		return new AliSDK();

	}

}
