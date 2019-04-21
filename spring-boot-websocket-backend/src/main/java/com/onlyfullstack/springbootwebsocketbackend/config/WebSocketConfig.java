package com.onlyfullstack.springbootwebsocketbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * Configure message broker options.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/onlyfullstack"); // prefix for every @MessageController path
	}

	/**
	 * Register STOMP endpoints mapping each to a specific URL and (optionally)
	 * enabling and configuring SockJS fallback options.
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
		.addEndpoint("/onlyfullstack-stomp-endpoint") // Where the client will connect to the STOMP server
		.setAllowedOrigins("http://localhost:4200")
		.withSockJS();
	}
}