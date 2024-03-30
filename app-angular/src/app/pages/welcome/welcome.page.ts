import { Component } from '@angular/core';
import { AccountService } from '../../providers/account.service';
import { AccountInfo } from '../../models/account-info';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrl: './welcome.page.css'
})
export class WelcomePage {

  constructor(private accountService : AccountService){}

  account$! : AccountInfo;

  ngOnInit(): void {
    this.accountService.getCurrentUser().subscribe(
      {
        next: account => this.account$ = account
      }
    );
  }
}
