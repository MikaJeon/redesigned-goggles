package hello.core.Scan.filter;


import java.lang.annotation.*;

@Target(ElementType.TYPE)//타입이면 클래스 레벨에 붙음
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeConponent {
}
