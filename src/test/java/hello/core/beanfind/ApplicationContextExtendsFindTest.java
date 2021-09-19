package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new
            AnnotationConfigApplicationContext(TestConfig.class);//테스트컴피그를 돌린다.
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() {
        //DiscountPolicy bean = ac.getBean(DiscountPolicy.class);//이렇게 하면 에러가 난다!
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(DiscountPolicy.class));//뜬 에러를 설정해주자.
    }
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy",
                DiscountPolicy.class);//타입은 디스카운트, 실제 구현은 레이트 디스카운트가 나올 것.
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
        //받아온 값이 레이트디스카운트가 맞냐 맞으면 성공 틀리면 에러
    }
    @Test
    @DisplayName("특정 하위 타입으로 조회")//별로 좋은 방법은 아님
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);//하나밖에 없는 자식 타입을 바로 가져오기
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType =
                ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);//사이즈가 두개면 통과
        for (String key : beansOfType.keySet()) {//키값 지정해주고
            System.out.println("key = " + key + " value=" +
                    beansOfType.get(key));//키와 벨류 출력하기
        }
    }
    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);//오브젝트 타입을 다 꺼내자!
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value=" +
                    beansOfType.get(key));//스프링에 있는 모든 빈, 스프링 내부 빈까지 다 튀어나옴
        }//오브젝트는 가장 상위 객체이기 때문! 자바는 모두 오브젝트이다.
    }
    @Configuration
    static class TestConfig {//빈을 등록한다! discountpolicy로 조회하면 자식이 두개다!
        @Bean
        public DiscountPolicy rateDiscountPolicy() {//의존관계 주입할때 여기를 참고하기 때문에 부모타입으로 받아오는게 좋다.
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
