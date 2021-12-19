import { Component } from '@angular/core';
import { ReloadTasksService } from './reload-tasks.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [ReloadTasksService]
})
export class AppComponent {
  title = 'taskmanager-fs';

	checkLogin(){
		return localStorage.getItem('autHeader') ==null || localStorage.getItem('currentUser')==null;
	}
}
