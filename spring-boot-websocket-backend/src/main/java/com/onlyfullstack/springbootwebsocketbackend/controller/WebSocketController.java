package com.onlyfullstack.springbootwebsocketbackend.controller;


import com.onlyfullstack.springbootwebsocketbackend.model.Hello;
import com.onlyfullstack.springbootwebsocketbackend.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@MessageMapping("/hello") // endpoint where the client will send messages or events
	@SendTo("/topic/hi")      // messeging queue endpoint where client will be listening
	public Hello greeting(User user) {
		return new Hello("Message from server: Hello " + user.getName());
	}
}
