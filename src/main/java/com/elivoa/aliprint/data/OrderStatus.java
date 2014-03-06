package com.elivoa.aliprint.data;

/**
 * <code>// old version 
waitbuyerpay, // (等待买家付款)
waitsellersend, // (等待卖家发货)
waitbuyerreceive, // (等待买家收货)
success, // (交易成功)
cancel, // (交易取消，违约金等交割完毕);

// 即时到账交易共有4个状态：
// waitbuyerpay, // (等待买家付款)
// waitsellersend, // (等待卖家发货),
// success, // (交易成功),
// cancel, // (交易取消，违约金等交割完毕)

// 分阶段交易包括：
// waitbuyerpay, // (等待买家付款)
// waitsellersend, // (等待卖家发货)
// waitbuyerreceive, // (等待买家收货)
// success(交易成功), //
// cancel, // (交易取消，违约金等交割完毕)
waitselleract, // (等待卖家操作)
waitbuyerconfirmaction, // (等待买家确认操作)
waitsellerpush, // (等待卖家推进)
</code>
 */
public enum OrderStatus {
	// 担保交易共有5个状态：

	// new version;
	SUCCESS, CANCEL, WAIT_BUYER_PAY, WAIT_SELLER_SEND, WAIT_BUYER_RECEIVE
}
