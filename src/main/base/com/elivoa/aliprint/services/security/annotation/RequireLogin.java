package com.elivoa.aliprint.services.security.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * RequireLogin
 * 
 * When a Tapestry page is annotated with this annotation, user not login will
 * be redirected to login page.
 * 
 * @Author Bo Gao [elivoa|gmail.com], Nov 6, 2011 At Home
 * @version 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@UseWith({ PAGE })
public @interface RequireLogin {

	boolean admin() default false;

	/**
	 * Required roles. Match rule: ANY
	 */
	public String[] roles() default {};

	/*
	 * Define returned pages.
	 */
	Class<?> loginPageClass() default Object.class;

	String loginPage() default "";

	Class<?> returnPageClass() default Object.class;

	String returnPage() default "";

}
