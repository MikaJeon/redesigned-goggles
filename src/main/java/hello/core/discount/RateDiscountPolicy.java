package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
@Qualifier("mainDiscountPolicy")//같은 타입의 빈이 존재하니 구분을 위한 추가 옵션을 줌
@Primary // 같은 타입의 빈이 여러개 있을 때 우선권을 주는 애노테이션
//보통 메인에 프라이머리, 서브에는 퀄리파이어로 호출하거나 이름을 지정해서 사용하는 식으로 많이 씀
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        }else {
            return 0;
        }
    }
}
