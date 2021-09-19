package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

//진짜 고객 요청이 올때마다 계속 new로 객체가 새로 생길까?
public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);//다른 내용이 출력됨
        System.out.println("memberService2 = " + memberService2);//다른 내용이 출력 됨.
        //memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);//일치하는지 확인해도.. 같지 않다는 결과가 나옴!
    }//웹 어플리케이션은 고객 요청이 참 많은데 매번 객체를 만든다면,,,?? 메모리 낭비가 너무나 심해질것!

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        //new SingletonService(); 이렇게 new로 생성하면 걍 프라이빗때문에 안된다고 컴파일 오류에 걸림
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance();
        //참조값이 같은 것을 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);
        //isSameAs랑 isEqual이랑 차이. 세임은 == 이고 이퀄은 자바의 equal 메서드임.
        singletonService1.logic();
    }//이렇게 만들어 놓고 서비스에서는 getInstance메서드 넣어서 리턴받으면 됨. 그러나 하나하나 해주면 불편하다!
//스프링 컨테이너에서 기본적으로 객체를 싱글톤으로 만들어서 관리해준다! 싱글톤 패턴이 적용되면 요청이 많이 들어와도 있는 객체를 재활용해
//성능이 상당히 좋아지게 될 것이다!

//그러나 인스턴스 뉴 만들어서 저장하고.. 리턴 인스턴스하고.. 프라이빗 하는 코드를 꼭 짜야한다는 싱글톤 패턴 구현 코드가 꼭 들어간다.
//클라이언트가 구체 클래스에 의존하게 된다.(멤버서비스임플.겟인스턴스 이렇게 가져와야 해서 안된다)

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);//스프링 컨테이너 방식으로
        //appconfig의 내용을 받아서 객체 생성후 get빈으로 서비스 받아온다!
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService1 = ac.getBean("memberService",
                MemberService.class);
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService2 = ac.getBean("memberService",
                MemberService.class);
        //참조값이 같은 것을 확인. 스프링이 처음에 컨테이너에 빈 등록한걸 같은걸 그대로 반환해주는 것.
        //싱글톤 패턴 코드 짜기 없이 싱글톤으로 사용 가능하다!
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        //memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
