package com.elivoa.aliprint.dev;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CodeSnippets
 * 
 * When there is a code or method is very good, mark with this annotation and
 * collect it as a code snippet later.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 23, 2011]
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface CodeSnippets {

}
