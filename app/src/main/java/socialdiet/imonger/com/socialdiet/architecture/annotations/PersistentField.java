package socialdiet.imonger.com.socialdiet.architecture.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Francisco on 28/08/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PersistentField {

    String column();

    int length() default 10;

    boolean id() default false;

    boolean autoincrement() default false;

    String dbType() default "text";
}
