export * from './greeting.service';
import { GreetingService } from './greeting.service';
export * from './user.service';
import { UserService } from './user.service';
export const APIS = [GreetingService, UserService];
