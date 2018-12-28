/*
 * This code demonstrates how to define an annotation.
 * 
 * @author Yashwant Golecha (ygolecha@gmail.com)
 * @version 1.0
 * 
 */

package priv.zxy.moonstep.AnnotationsSamble;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 为了便于了解注释的额外代码，与项目无关
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Todo {
    public enum Priority {LOW, MEDIUM, HIGH}
    public enum Status {STARTED, NOT_STARTED}    
    String author() default "Yash";
    Priority priority() default Priority.LOW;
    Status status() default Status.NOT_STARTED;
}
