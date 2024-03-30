import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../providers/account.service';
import { AccountSignin } from '../../models/account-signin';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrl: './signin.page.css'
})
export class SigninPage {

  constructor(private router: Router, private accountService : AccountService){}

  isError : boolean = false;
  errorMsg$! : String;

  getCompte : AccountSignin = {
    email : "" ,
    password : ""
  };

  submit(){
    this.accountService.signin(this.getCompte).subscribe({
      next: compte => {
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
  }

}
