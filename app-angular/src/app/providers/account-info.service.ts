import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { AccountInfo } from '../models/account-info';

@Injectable({
  providedIn: 'root'
})
export class AccountInfoService {

  accountInfoInitialValue : AccountInfo= {
    "id": "",
    "firstName": "",
    "lastName": "",
    "email": "",
    "image": "",
    "paidHolidayLastYear": "",
    "paidHolidayThisYear": "",
    "employeeRtt": "",
    "group": "",
    "department": "",
    "organization": "",
    "startDate": "",
    "roles": [

    ]
}

  accountInfoSub = new BehaviorSubject<AccountInfo>(this.accountInfoInitialValue);

  constructor() { }

  publier(accountInfo: AccountInfo){
    this.accountInfoSub.next(accountInfo);
  }

  abonner() : Observable<AccountInfo>{
    return this.accountInfoSub.asObservable();
  }
}
