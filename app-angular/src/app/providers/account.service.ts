import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccountInfo } from '../models/account-info';
import { AccountSignin } from '../models/account-signin';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  signin = (compte : AccountSignin)=> {
    return this.http.post<any>("http://localhost:8082/api/signin", compte,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  signout = ()=> {
    return this.http.get<any>("http://localhost:8082/api/signout", {
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  getCurrentUser = ()=> {
    return this.http.get<AccountInfo>("http://localhost:8082/api/currentuser",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

}
