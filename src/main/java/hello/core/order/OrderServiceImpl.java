package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor//생성자를 만들어주는 롬복! 개쩐다 파이널 붙은거를 기준으로 생성자로 만들어줌. 생성자도 있고 파이널도 있고 코드는 짧다 감동 그 자체
public class OrderServiceImpl implements OrderService{

    //@Autowired
    private final MemberRepository memberRepository;
    //@Autowired
    private final DiscountPolicy discountPolicy; // 필드 주입. 앱 컨피그에서 getbean으로 이 값을 가져와도 잘 나옴.
    //이 경우 의존관계를 이 필드에 바로 넣어버리는 것. 프라이빗이라도 가능하다. 코드가 간결하고 좋으나
    //노란줄이 그어진(필드인젝션낫리커멘드-추천안한다)걸로 봐서 외부에서 변경이 불가능해서 테스트를 할 수 없다!
    //스프링이 없는 상태에서 테스트를 해보고 싶으면 테스트 작동이 안됨.
    //직접 디비에 접근하지 않은 상태에서 더미 데이터를 넣어서 테스트를 해보고싶어도 변경할 방법이 없어서
    //테스트를 할 수 없다. 순수 자바로 테스트 불가능...
    //얘! 컨테이너에 있는거! 넣어버린다! 하면 끝남. 변경 불가능. 값을 넣고 싶으면 세터를 따로 열어서 작동시켜야 함. 번거로움
    //그럴거면 그냥 세터에 오토와이드하는게 나음
    //테스트코드 처럼 한번 보고 말 코드나 컨피그레이션같은 스프링에서만 쓰는 애너테이션이 붙은 클래스에서는 써도 됨.
    //예를 들어 앱 컨피그 내에 오토와이드 a a;하고 그 밑에 바로 @bean 메서드(리턴 구현체(a))이런 식으로 앱 컨피그 내
    //주입해줄 객체를 오토와이드해서 주입하는건 괜찮음. 수동 등록을 할 경우에는 사용해도 괜찮다는 의미

    @Autowired//생성자 주입. 생성자의 경우 앱컨피그에서 넣을 값 바꿀 수 있음.
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;//이 이름을 타입 이름과 같이 하면 오류가 남.
        //왜냐? 디스카운트폴리시를 임플리먼트 한 클래스가 두개라서 같은 타입이 두개니까 찾을 수가 없음.
        //이럴때 스프링이 필드 이름으로 한번 더 찾아줌. 이름이 같은게 있다면 그걸로 di를 함.
        //아니면 지금 쿼리파이어 지정한대로 옵션 값을 주면 같은 옵션을 지닌 클래스로 di가 됨.

    } //이 생성자를 리콰이어드 애노테이션이 걍 만들어줌

//    @Autowired//수정자 주입. 수정자의 경우 호출시에 메서드 호출해서 넣을 값 바꿀 수 있음
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//
//



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
