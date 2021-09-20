package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import static org.springframework.context.annotation.ComponentScan.*;


@Configuration
@ComponentScan(//스프링 빈을 쫙 끌어서 등록을 자동으로 해주는거. @컴포넌트가 붙은 클래스를 찾아서 자동으로 다 컨테이너에 등록해줌.
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =//필터! 자동등록하는거 중에 뺄거를 지정해줌.
                Configuration.class))//이 에노테이션이 붙은건 뺄거다. 이거는 앱 컨피그에 붙으니까 수동 등록에 쓴거라서 충돌난다.
public class AutoAppConfig {//기존 예제코드를 남기기 위해서 필터 달아준것. 실무에서는 그냥 스캔하면 된다.

}