import { Component } from '@angular/core';
import { AccountInfo } from '../../models/account-info';
import { AccountInfoService } from '../../providers/account-info.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrl: './welcome.page.css'
})
export class WelcomePage {

  constructor(private accountInfoService : AccountInfoService){}

  account$! : AccountInfo;

  ngOnInit(): void {
    this.accountInfoService.abonner().subscribe({
      next: (value: AccountInfo) => this.account$ = value
    })
  }

}
