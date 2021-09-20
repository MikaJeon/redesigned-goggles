package hello.core.autoWired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{
        //호출 안됨
        @Autowired(required = false)//주입할 빈이 없으면 오류가 나는데 나도 무시하자~ 라는 뜻
        public void setNoBean1(Member member) {//이 멤버는 스프링 빈이 아니다.
            System.out.println("setNoBean1 = " + member);
        }
        //null 호출
        @Autowired
        public void setNoBean2(@Nullable Member member) {//널을 허용한다. 없으면 널 출력해라~
            System.out.println("setNoBean2 = " + member);
        }
        //Optional.empty 호출
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> member) {//널 허용하는거랑 같음 옵셔널로 감싸자
            System.out.println("setNoBean3 = " + member);
        }
    }

}
