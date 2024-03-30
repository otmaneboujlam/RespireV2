import { Component } from '@angular/core';
import { AccountService } from '../providers/account.service';
import { AccountInfo } from '../models/account-info';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private accountService : AccountService, private router : Router){}

  account$! : AccountInfo;

  ngOnInit(): void {
    this.accountService.getCurrentUser().subscribe(
      {
        next: account => this.account$ = account
      }
    );
  }

  submit(){
    this.accountService.signout().subscribe({
      next: () => {
        this.router.navigateByUrl("/signin")
      },
      error: err => {
        console.log(err)
      }
    });
  }
}
