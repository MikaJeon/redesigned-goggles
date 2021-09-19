package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
//얼마 할인 되었는지를 리턴할 인터페이스(정액 정률 둘다)
    int discount(Member member, int price);
}
