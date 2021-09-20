package hello.core.Scan.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.ComponentScan.Filter;
public class ComponentFilterAppConfigTest {
    @Test
    void filterScan() {
        ApplicationContext ac = new
                AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);//자기자신 불러옴. 즉 자기 자신이 스캔한 컴포넌트들 불러옴.
        BeanA beanA = ac.getBean("beanA", BeanA.class);//자기자신이 스캔한 컴포넌트 중 에이 가져옴
        assertThat(beanA).isNotNull();
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));//비도 가져옴. 근데 제외필터 걸었으니 없을 것
    }
    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                    MyIncludeConponent.class),//포함할 필터
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                    MyExcludeConponent.class)//포함하지 않을 필터
    )
    static class ComponentFilterAppConfig {
    }
}