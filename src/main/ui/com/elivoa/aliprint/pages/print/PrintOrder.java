package com.elivoa.aliprint.pages.print;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.dal.AgentDao;
import com.elivoa.aliprint.data.Params;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliOrderEntity;
import com.elivoa.aliprint.entity.SellAgent;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;
import com.elivoa.aliprint.services.AuthService;

@Import(//
library = "context:js/print/LodopFuncs.js", //
stylesheet = "context:css/print/express-order-print.css"//
)
public class PrintOrder {

	private static SellAgent defaultAgent;
	static {
		defaultAgent = new SellAgent();
		defaultAgent.setSenderName("李");
		defaultAgent.setSenderPhone("13004211803");
	}

	long orderId;

	@Property
	AliOrder order;

	@Property
	SellAgent agent;

	@Property
	String senderPhone;

	@Property
	String senderInfo;

	void onActivate(long orderId) {
		this.orderId = orderId;
	}

	Object setupRender() throws MalformedURLException {
		if (0 == this.orderId) {
			throw new RuntimeException("missing order id");
		}

		// validate
		try { // TODO exception not needed.
			if (!service.authenticate(this.token)) {
				return new URL(auth_redirect_url);
			}
		} catch (NeedAuthenticationException e) {
			e.printStackTrace();
			return new URL(auth_redirect_url);
		}

		// AliResult<AliOrder> orders = this.service.listOrders(token, null, 1, 1,
		// Params.create().add("id", this.orderId).add("@withAlias"), true, true);
		// AliResult<AliOrder> orders = this.service.listOrders(token, null, 1, 1,
		// Params.create().add("id", this.orderId).add("@withAlias"), true, true);

		// System.out.println(orders.getTotal());
		// if (null != orders && orders.getModels().size() > 0) {
		// this.order = orders.getModels().get(0);
		// }

		this.order = this.service.getOrder(token, this.orderId,
				Params.create().add("@withAlias").add("@withFullAddress").add("@withSenderInfo"));
		try {
			this.agent = agentDao.getAgent(order.getBuyerLoginId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (null == this.agent) {
			this.agent = defaultAgent;
		}

		initContent();

		return null;
	}

	@Property
	int contentFontSize;// 14px or 16px;

	@Property
	int realClothesCount;

	@Property
	String contentHTML;

	public void initContent() {
		boolean hasGift = false;
		this.contentFontSize = 10;
		StringBuilder sb = new StringBuilder();
		int total = 0;
		for (AliOrderEntity entity : this.order.getEntities()) {
			if (total > 0 && total < this.order.getEntities().size()) {
				sb.append("|SPLITER|");
			}
			String alias = entity.getAlias();
			if (null == alias || alias.trim().length() == 0) {
				alias = entity.getProductName();
			}
			if (!alias.equalsIgnoreCase("赠品")) {
				hasGift = true;
				total += entity.getQuantity();
			}
			sb.append(alias);

			// color and size;
			Map<String, String> specInfo = entity.getSpecInfo();
			String 颜色 = specInfo.get("颜色");
			if (null != 颜色) {
				颜色 = 颜色.trim().replaceAll("色", "");
				颜色 = 颜色.replaceAll("\\(.*\\)", "").trim();
				颜色 = 颜色.replaceAll("（.*）", "").trim();
				颜色 = 颜色.replaceAll("【.*】?", "").trim();
			} else {
				颜色 = "";
			}
			String size = specInfo.get("尺码");
			if (null != size) {
				size = size.replaceAll("\\(.*\\)", "").trim();
				size = size.replaceAll("（.*）", "").trim();
				size = size.replaceAll("【.*】?", "").trim();
				if (size.trim().equals("均码")) {
					size = "";
				}
			} else {
				size = "";
			}
			sb.append(" ").append(颜色).append(size);

			// quantity;
			double quantity = entity.getQuantity();
			if (quantity > 1) {
				sb.append("×").append(new Double(quantity).intValue());
			}
		}
		this.realClothesCount = total;
		String spliter = ", ";
		if (this.realClothesCount >= 3) {
			spliter = "<br/>";
		}
		this.contentHTML = sb.toString().replace("|SPLITER|", spliter);
	}

	public int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	public int getDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	private void setContent2() {

	}

	private void setContent3() {

	}

	private void setContentN() {

	}

	@Inject
	AuthService service;

	@Inject
	AgentDao agentDao;

	@SessionState(create = true)
	AliToken token;

	private static String auth_redirect_url = "http://gw.open.1688.com/auth/authorize.htm?client_id=1010132&site=china&redirect_uri=http://localhost:8080/aliprint/authorization&_aop_signature=14C23331781F7594FB5FA10C32CE8AE4DD13FB4D";

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.appkey")
	String appkey;

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.securitykey")
	String securitykey;

}
