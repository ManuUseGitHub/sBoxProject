/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceDefinition;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * @see
 * http://stackoverflow.com/questions/15312238/how-to-get-a-javadoc-of-a-method-at-run-time
 * @author MAZE2
 */
@Retention(RUNTIME)
@Target(value = METHOD)
public @interface ServiceDef {

    public String desc() default "";

    public String[] params();
}
