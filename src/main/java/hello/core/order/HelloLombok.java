package hello.core.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter//이 애노테이션만 붙이면 코드 안짜도 알아서 그 역할을 한다.
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args){
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("adad");

        String name = helloLombok.getName();
        System.out.println("name = " + name);
    }
}
