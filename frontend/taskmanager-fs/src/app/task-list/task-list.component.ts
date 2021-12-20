import { Component, OnInit } from '@angular/core';
import { TaskService } from '../core/api/v1/api/task.service';
import { Task } from '../core/api/v1/model/task';
import { ReloadTasksService } from '../reload-tasks.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
	tasks:  Array<Task> = [];
	taskCount: number = 0;
	subscription: Subscription;
	pageSize: number = 5;
	currentPage: number = 1;
	numPages: number = 0;

  constructor(private readonly taskService: TaskService, private reloadTasksService: ReloadTasksService) {
		this.subscription = reloadTasksService.reloadAnnounced$.subscribe(
      event => {
        this.getTasks();
    });
	}

  ngOnInit(): void {
		this.getTasks();
  }

	getTasks(): void {
		let observable = this.taskService.listTasks(this.currentPage-1, this.pageSize);
		observable.subscribe(tasklist =>{
			console.log(tasklist);
			if(tasklist.tasks != undefined)
				this.tasks = tasklist.tasks;
			if(tasklist.count != undefined){
				this.taskCount = tasklist.count;
				this.numPages = Math.ceil(this.taskCount/this.pageSize);
			}
		});
	}

	deleteTask(	task: Task): void {
		let observable = this.taskService.deleteTask(task.id!);
		observable.subscribe(() =>{
			this.getTasks();
		});
	}

	completeTask(	task: Task): void {
		let observable = this.taskService.updateTaskStatus(task.id!);
		observable.subscribe(() =>{
			this.getTasks();
		});
	}

	ngOnDestroy() {
    this.subscription.unsubscribe();
  }

	doSomething(event: number){
		this.currentPage = event;
		this.getTasks();
	}

}
