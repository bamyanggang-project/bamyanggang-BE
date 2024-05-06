package jjon.bamyanggang.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// stomp 접속 주소 url => /ws-stomp
		registry.addEndpoint("/ws-bamyanggang") // 연결될 엔드포인트
				.setAllowedOriginPatterns("*");
	}
	
	// 메시지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// enableSimpleBroker : 내장 메시지 브로커를 사용하기 위한 메소드
		// prefix가 붙은 메시지를 발행할 경우, 메시지 브로커가 이를 처리한다.
		// 메시지를 구독하는 요청 url => 메시지를 받을 때
		registry.enableSimpleBroker("/sub");
		// 메시지 핸들러로 라우팅 되는 prefix를 파라미티로 지정할 수 있다.
		// 메시지 가공 처리가 필요한 경우, 가공 핸들러로 메시지를 라우팅 되도록하는 설정
		// 메시지를 발행하는 요청 url = > 메시지 보낼 때
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
	
}
