package jjon.bamyanggang.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
// 스프링 웹소켓 활성화
@EnableWebSocket
// 메시지 브로커 활성화
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// WebSocket 서버 연결 엔드 포인트
		registry.addEndpoint("/ws-bamyanggang")
				// 모든 도메인 허용
				.setAllowedOriginPatterns("*");
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// enableSimpleBroker : 내장 메시지 브로커를 사용하기 위한 메소드
		// "/sub"가 붙은 발행된 메시지를 메시지 브로커가 처리해서 구독한 클라이언트에게 보내준다.
		registry.enableSimpleBroker("/sub");

		// 메시지 가공처리를 위해, 가공 핸들러로 메시지를 라우팅 되도록 설정한다.
		// "/pub"가 붙은 url 매핑 요청으로 라우팅
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
	
}
