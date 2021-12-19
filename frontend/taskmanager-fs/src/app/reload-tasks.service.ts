import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class ReloadTasksService {
	private reloadAnnouncedSource = new Subject<string>();
	reloadAnnounced$ = this.reloadAnnouncedSource.asObservable();

  constructor() { }

	announceReloadTasks(event: string){
		console.log("entra servicio");

		this.reloadAnnouncedSource.next(event);
	}
}
