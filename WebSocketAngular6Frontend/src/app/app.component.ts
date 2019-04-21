import { Component, OnInit } from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  greetings: string[] = [];
  
  disabled = true;
  
  name: string;

  private stompClient = null;

  userForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      userName: ['', Validators.required]
    });
  }

  connectToWebsocketWithStomp() {
    const socket = new SockJS('http://localhost:8080/onlyfullstack-stomp-endpoint');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({}, function (frame) {
      _this.showUserNameForm(true);
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe('/topic/hi', function (hello) {
        _this.showGreeting(JSON.parse(hello.body).greeting);
      });
    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.showUserNameForm(false);
    console.log('Disconnected!');
  }

  sendName() {
    this.stompClient.send(
      '/onlyfullstack/hello',
      {},
      JSON.stringify({ 'name': this.name })
    );
  }

  submit() {
    this.stompClient.send(
      '/onlyfullstack/hello',
      {},
      JSON.stringify({ 'name': this.userForm.value.userName })
    );
  }

  showGreeting(message) {
    this.greetings.push(message);
  }

  showUserNameForm(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
    }
  }
}
