import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import { TaskService } from '../core/api/v1/api/task.service';
import { Task } from '../core/api/v1/model/task';
import { User } from '../core/api/v1/model/user';
import { ReloadTasksService } from '../reload-tasks.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {
	activeAdd: boolean = false;

  constructor(private readonly taskService: TaskService, private reloadTasksService: ReloadTasksService) {
	}

  ngOnInit(): void {
  }

	logout(): void {
		localStorage.removeItem('autHeader');
		localStorage.removeItem('currentUser');
	}

	addTask(f:NgForm) : void {

		let strUser: string|null = localStorage.getItem('currentUser');
		if(strUser!=null) {
			let user: User|null = <User>(JSON.parse(strUser));
			let task = <Task>({
		    title: f.value.title,
		    description: f.value.description,
				creationDateTime: (new Date()).toISOString(),
				owner: user.username
		 	});

			let observable = this.taskService.createTask(task);
			observable.subscribe(() =>{
				f.reset();
				this.activeAdd = false;
				this.reloadTasksService.announceReloadTasks("voy");
			});
		} else {
			this.logout();
		}
	}

}
