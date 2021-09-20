package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;//종료 알려주는 빈
import org.springframework.beans.factory.InitializingBean;//초기화 해주는 빈

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {//implements InitializingBean, DisposableBean {
    private String url;
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }
    public void setUrl(String url) {
        this.url = url;
    }
    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }
    public void call(String message) {//연결 후 연결된 서버에 보낼 메세지
        System.out.println("call: " + url + " message = " + message);
    }
    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    //@Override
    @PostConstruct
    public void init() {//throws Exception {// InitializingBean에서 프로퍼티스세팅(의존관계주입)이 끝나면 호출됨.
        connect();
        call("초기화 연결 메세지");
    }

    //@Override
    @PreDestroy
    public void close(){// throws Exception {//종료될때 호출
        disconnect();
    }
}//네트워크와의 연결 흐름~