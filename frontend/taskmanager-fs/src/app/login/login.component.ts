import { Component, OnInit } from '@angular/core';
import { UserVO } from './User'
import { UserService } from '../core/api/v1/api/user.service';
import { User } from '../core/api/v1/model/user';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
	user: UserVO= new UserVO();
	remoteUser: User|null = null;
	error: boolean = false;
	loading: boolean = false;
	errorMsg: string = "";

	constructor(private readonly userService: UserService) {}

  ngOnInit(): void {
		this.userService.defaultHeaders.set('Access-Control-Allow-Origin', '*');
  }

	submit(f:NgForm){
		let authHeader="Basic " + btoa(f.value.username+":"+f.value.password);
		let observable = this.userService.login(authHeader);
		this.loading=true;
		localStorage.setItem('autHeader', authHeader);

		observable.subscribe(user =>{
			this.remoteUser = user;
			console.log(user);
			localStorage.setItem('currentUser', JSON.stringify(user));
			this.loading=false;
		},err => {
			this.errorMsg= err.message;
			this.error=true;
			console.log('HTTP Error', err);
			this.loading=false;
			localStorage.removeItem('autHeader');
		});
	}

}
