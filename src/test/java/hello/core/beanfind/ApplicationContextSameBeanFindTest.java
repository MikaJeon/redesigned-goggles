package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
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

class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new
            AnnotationConfigApplicationContext(SameBeanConfig.class);//앱컨피그 만들때 밑에 따로 만든
    //세임빈컨피그를 넣어줬으니 이 코드는 세임빈 컴피그만 가지고 실행된다.
    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
        //DiscountPolicy bean = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(MemberRepository.class)); //멤버리포지토리를 의존하는 구현체는 밑에 따로 설정해서 여러개임.
    }
    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1",
                MemberRepository.class);//타입으로 바로 조회하지 말고 이름을 넣어주자! 그러면 1번만 가져온다.
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }
    @Test
    @DisplayName("특정 타입을 모두 조회하기")//둘다 꺼내고 싶어!
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType =
                ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {//key라는 스트링 값을 맵의 키에 넣어주고
            System.out.println("key = " + key + " value = " +
                    beansOfType.get(key));//키값과 키를 통해서 가져올 벨류 값을 출력함
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);//빈이 두개 등록되어있을것이다
    }
    @Configuration
    static class SameBeanConfig {//중복 빈을 만들어주자! 클래스 안에 클래스 쓸때 스테틱 붙여주면 이 안에서만 쓸거라는 의미
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();//멤버리포지토리를 의존하는 값 1
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();//멤버리포지토리를 의존하는 값 2
        }
    }
}