import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccountInfo } from '../models/account-info';
import { AccountSignin } from '../models/account-signin';
import { AccountInfoService } from './account-info.service';
import { IsLoggedInService } from './is-logged-in.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient, private router : Router, private isLoggedIn : IsLoggedInService, private accountInfoService : AccountInfoService) { }

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
    }).subscribe(
      {
        next: () => {
          sessionStorage.removeItem("TOKEN");
          this.isLoggedIn.publier(false);
          this.router.navigateByUrl("/signin")
        },
        error: err => {
          console.log(err)
        }
      }
    )
  }

  getCurrentUser = ()=> {
    return this.http.get<AccountInfo>("http://localhost:8082/api/currentuser",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    }).subscribe(
      {
        next: accountInfo => this.accountInfoService.publier(accountInfo)
      }
    );
  }

}
