// Eashan Vytla
// 3/29/2020
// Purpose: This class allows users to add OpModes to the OpMode directory

package InternalFiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface RegisterOpMode {
    String name() default "Untitled";
}
