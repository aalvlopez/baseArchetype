import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'taskmanager-fs';

	checkLogin(){
		return localStorage.getItem('autHeader') ==null || localStorage.getItem('currentUser')==null;
	}
}
