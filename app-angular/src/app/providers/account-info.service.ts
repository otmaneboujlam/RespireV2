import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { AccountInfo } from '../models/account-info';

@Injectable({
  providedIn: 'root'
})
export class AccountInfoService {

  accountInfoInitialValue : AccountInfo= {
    "id": "4",
    "firstName": "s",
    "lastName": "s",
    "email": "user@respire.com",
    "image": "",
    "paidHolidayLastYear": "1.0",
    "paidHolidayThisYear": "3.16",
    "employeeRtt": "6",
    "group": "Recherche",
    "department": "Recherche & Développement",
    "organization": "Diginamic",
    "startDate": "2024-03-01",
    "roles": [
        "ROLE_USER"
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
