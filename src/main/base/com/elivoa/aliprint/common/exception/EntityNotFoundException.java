package com.elivoa.aliprint.common.exception;

import com.elivoa.aliprint.pages.ExceptionReport;

/**
 * EntityNotFoundException
 * 
 * NotFound基类
 * 
 * @author Elivoa [Dec 17, 2011] 添加构造函数。再用name取entity的时候，不能用Long的id作为PK.
 */
public class EntityNotFoundException extends Exception implements ContextAwareException {

	private static final long serialVersionUID = -4264206404954566359L;

	private Class<?> clazz;
	private Long pk;
	private String key;

	public EntityNotFoundException(Long id) {
		this.setPk(id);
	}

	public EntityNotFoundException(Class<?> clazz, Long id) {
		this.setPk(id);
	}

	public EntityNotFoundException(Class<?> clazz, String key) {
		this.setKey(key);
	}

	public String getRedirectPageName() {
		return ExceptionReport.class.getSimpleName();
	}

	public Object[] getContext() {
		return new Object[] { super.getMessage() };
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
