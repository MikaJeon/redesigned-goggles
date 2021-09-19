package hello.core.singleton;

public class SingletonService {
    //1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();//자기자신을 내부에 프라이빗으로 스태틱으로 가짐.
    //스태틱으로 가지면 클래스 레벨에 올라가기 때문에 딱 한가지만 생성되게 됨. 자기자신을 객체 인스턴스로 생성해서 변수로 가지고 있음.
    //2. public으로 열어서 객체 인스터스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;//조회를 할때는 이걸로 조회함
    }
    //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
    }//이렇게 유일한 생성자를 생성자를 프라이빗으로 생성하면 다른데서 new생성자로 SingtomService를 가지고 올 수 없음.
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}//getInstance메서드를 통해서만 인스턴스를 반환할 수 있음. 그러나 기존 생성된 객체는 딱 1개이기 때문에
//항상 같은 내용이 반환 될 것! 완벽한 싱글톤~