import { Component } from '@angular/core';
import { AccountInfo } from '../../models/account-info';
import { AccountInfoService } from '../../providers/account-info.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrl: './profile.page.css'
})
export class ProfilePage {

  constructor(private accountInfoService : AccountInfoService){}

  account$! : AccountInfo;

  ngOnInit(): void {
    this.accountInfoService.abonner().subscribe({
      next: (value: AccountInfo) => this.account$ = value
    })
  }

}
