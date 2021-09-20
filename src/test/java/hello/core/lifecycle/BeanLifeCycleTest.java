package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new
                AnnotationConfigApplicationContext(LifeCycleConfig.class);//라이프사이클컨피그를 컨테이너에 등록
        NetworkClient client = ac.getBean(NetworkClient.class);//빈을 조회함
        ac.close(); //스프링 컨테이너를 종료, ConfigurableApplicationContext 필요 그냥 애플리케이션컨텍스트는 안됨. 더 상위의 것 호출
    }
    @Configuration
    static class LifeCycleConfig {
        @Bean//(initMethod = "init", destroyMethod = "close")//네트워크클라이언트가 호출된 결과물이 빈에 등록이 됨.
        //이 괄호 안에 시작할때 무슨 메서드 실행할지, 종료 할때 어떤거 실행할지 지정해주면 그게 실행 됨.
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();//네트워크클라이언트 생성자 호출. 이때 url없어서 null
            networkClient.setUrl("http://hello-spring.dev");//세터로 url 넣어줌.
            return networkClient;
        }
    }
}