package com.elivoa.aliprint.alisdk;

import java.sql.Timestamp;

import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;

public class AliToken {

	private AuthorizationToken token;
	private Timestamp updateTime;
	private Timestamp createTime;

	// methods

	public boolean isAccessTokenAvailable() {
		if (null != token && null != token.getAccess_token() && token.getExpires_in() > 0) {
			if (token.getExpires_time().getTime() - System.currentTimeMillis() > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean isRefreshTokenAvailable() {
		// TODO why authorizationToken.getRefreshTokenTimeout() is null? to finish this.
		return true;
	}

	public String accessToken() {
		if (null == this.token || !this.isAccessTokenAvailable()) {
			throw new NeedAuthenticationException();
		}
		return this.getToken().getAccess_token();
	}

	public Long getAliid() {
		if (null == this.token) {
			return null;
		}
		return this.getToken().getAliId();
	}

	public String getMemberId() {
		Long aliid = this.getAliid();
		if (null == aliid) {
			return "";
		}
		return String.format("b2b-%s", aliid);
	}

	// accessors

	public AuthorizationToken getToken() {
		return token;
	}

	public void setToken(AuthorizationToken token) {
		this.token = token;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void updateAll(AliToken savedToken) {
		if (null == savedToken) {
			return;
		}
		this.setCreateTime(savedToken.getCreateTime());
		this.setUpdateTime(savedToken.getUpdateTime());
		this.setToken(savedToken.getToken());
	}

}
