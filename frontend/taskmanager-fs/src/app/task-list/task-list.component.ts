import { Component, OnInit } from '@angular/core';
import { TaskService } from '../core/api/v1/api/task.service';
import { Task } from '../core/api/v1/model/task';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
	tasks: Task[] = [];

  constructor(private readonly taskService: TaskService) { }

  ngOnInit(): void {
		this.getTasks();
		let autHeader:string|null = localStorage.getItem('autHeader');
		if(autHeader!=null)
			this.taskService.defaultHeaders.set('Athorization', autHeader);
  }

	getTasks(): void {
		let observable = this.taskService.listTasks(0,10);
		observable.subscribe(tasks =>{
			this.tasks = tasks;
		});
	}

	deleteTask(	task: Task): void {
		let observable = this.taskService.deleteTask(task.id!);
		observable.subscribe(() =>{
			this.getTasks();
		});
	}

	completeTask(	task: Task): void {
		let autHeader:string|null = localStorage.getItem('autHeader');
		if(autHeader!=null)
			this.taskService.defaultHeaders.set('Athorization', autHeader);
		let observable = this.taskService.updateTaskStatus(task.id!);
		observable.subscribe(() =>{
			this.getTasks();
		});
	}

}
