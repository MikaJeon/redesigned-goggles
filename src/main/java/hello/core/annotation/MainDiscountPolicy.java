package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy { //이렇게 해주면 MainDiscountPolicy 이 에노테이션을 쓰면 위의 내용이 다 동작함
    //사용하는 이유? 쿼리파이어 쓰면 되긴 하는데, 문제는 오타를 못 잡아줌. 뭐가 문제인지 몰라서 컴파일 오류가 안남.
    //이렇게 애노테이션을 만들어서 에노테이션 자체로 쓰면 컴파일 바로 잡아줌
    //여기서 이렇게 애노테이션 만들고, 애노테이션의 내용이 있는 파일과 애노테이션으로 해당 파일을 호출하는 파일에 달아줌.
}
