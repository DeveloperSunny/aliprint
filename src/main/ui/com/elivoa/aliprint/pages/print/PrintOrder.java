package com.elivoa.aliprint.pages.print;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.dal.AgentDao;
import com.elivoa.aliprint.dal.PrintHistoryDao;
import com.elivoa.aliprint.data.OrderStatus;
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

	@Property
	Long orderId;

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
				return new URL(auth_url);
			}
		} catch (NeedAuthenticationException e) {
			e.printStackTrace();
			return new URL(auth_url);
		}

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
			if (!OrderStatus.WAIT_SELLER_SEND.toString().equalsIgnoreCase(entity.getEntryStatus())) {
				continue; // pass entities with status other than WAIT_SELLER_SEND;
			}
			if (total > 0 && total < this.order.getEntities().size()) {
				sb.append("|SPLITER|");
			}
			String alias = entity.getAlias();
			if (null == alias || alias.trim().length() == 0) {
				alias = entity.getProductName();
			}
			if (!alias.equalsIgnoreCase("赠品") && !alias.equalsIgnoreCase("赠")) {
				hasGift = true;
				total += entity.getQuantity();
			}
			sb.append(alias);

			// color and size;
			Map<String, String> specInfo = entity.getSpecInfo();
			if (null != specInfo) {
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
			}
			// sb.append("[").append(entity.getEntryStatus()).append("]");

			// quantity;
			double quantity = entity.getQuantity();
			if (quantity > 1) {
				sb.append("×").append(new Double(quantity).intValue());
			}
		}

		this.realClothesCount = total;
		String spliter = ", ";
		if (this.realClothesCount < 3) {
			spliter = "<br/>";
		}
		this.contentHTML = sb.toString().replace("|SPLITER|", spliter);
		if (this.realClothesCount < 2) {
			this.contentHTML = "<br>" + this.contentHTML;
		}
	}

	public int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public int getDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	public String getMobile() {
		StringBuilder sb = new StringBuilder();
		if (order.getToMobile() != null && order.getToMobile().trim().length() > 0
				&& !order.getToMobile().trim().equalsIgnoreCase("null")) {
			sb.append(order.getToMobile());
		}
		if (sb.length() > 0) {
			sb.append(",");
		}
		if (order.getToPhone() != null && order.getToPhone().trim().length() > 0
				&& !order.getToPhone().trim().equalsIgnoreCase("null")) {
			sb.append(order.getToPhone());
		}
		return sb.toString();
	}

	// send zone

	@Property
	String trackingNo;

	@InjectComponent
	Zone sendZone;

	@Inject
	Block sendSuccessBlock;

	@Component
	Form sendForm;

	// send...
	Object onSuccess() {

		this.service.sendAll(token, orderId, this.trackingNo);
		boolean success = false;
		if (success) {
			return sendSuccessBlock;
		} else {
			sendForm.recordError("错误：！！！");
			return sendForm;
		}
	}

	// services

	@Inject
	AuthService service;

	@Inject
	PrintHistoryDao historydao;

	@Inject
	AgentDao agentDao;

	@SessionState(create = true)
	AliToken token;

	@Inject
	@Symbol("com.elivoa.aliprint.authurl")
	String auth_url;

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.appkey")
	String appkey;

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.securitykey")
	String securitykey;

}
