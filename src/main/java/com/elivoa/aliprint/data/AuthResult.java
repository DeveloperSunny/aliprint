package com.elivoa.aliprint.data;

// TO USE
public class AuthResult {

	// static
	public static AuthResult pass() {
		return new AuthResult(false);
	}

	public static AuthResult needReauth() {
		return new AuthResult(true);
	}

	// fields
	private boolean needReAuth;

	// constructors
	public AuthResult() {
		// empty constructor;
	}

	public AuthResult(boolean needReauth) {
		this.needReAuth = true;
	}

	// methods
	public boolean needReAuth() {
		return needReAuth;
	}

	public boolean isNeedReAuth() {
		return needReAuth;
	}

	public AuthResult setNeedReAuth(boolean needReAuth) {
		this.needReAuth = needReAuth;
		return this;
	}

}
