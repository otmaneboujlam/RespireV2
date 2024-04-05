import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../providers/account.service';
import { AccountSignin } from '../../models/account-signin';
import { IsLoggedInService } from '../../providers/is-logged-in.service';
import { AccountInfoService } from '../../providers/account-info.service';
import { AccountInfo } from '../../models/account-info';
import * as crypto from 'crypto-js';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrl: './signin.page.css'
})
export class SigninPage {

  constructor(private router: Router, private accountService : AccountService, private isLoggedIn : IsLoggedInService, private accountInfoService : AccountInfoService){}

  isError : boolean = false;
  errorMsg$! : String;

  getCompte : AccountSignin = {
    email : "" ,
    password : ""
  };

  submit(){
    this.accountService.signin(this.getCompte).subscribe({
      next: compte => {
        sessionStorage.setItem("TOKEN", "true");
        this.isLoggedIn.publier(true);
        this.router.navigateByUrl("/welcome")
      },
      error: err => {
        this.isError = true;
        this.errorMsg$ = "Email ou mot de passe incorrect";
        setTimeout(()=> {
          this.isError=false;
        },2000);
      }
    });
    this.accountInfoService.abonner().subscribe({
      next: (value: AccountInfo) => sessionStorage.setItem("TOKEN", crypto.AES.encrypt("["+value.roles+"]", "secret").toString())
    })
  }

}
