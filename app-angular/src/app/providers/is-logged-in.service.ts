import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IsLoggedInService {

  isLoggedInSub = new BehaviorSubject<boolean>(false);

  constructor() { }

  publier(isLoggedIn: boolean){
    this.isLoggedInSub.next(isLoggedIn);
  }

  abonner() : Observable<boolean>{
    return this.isLoggedInSub.asObservable();
  }
}
