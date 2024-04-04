import { Component, Input } from '@angular/core';
import { AccountService } from '../../providers/account.service';
import { AccountInfo } from '../../models/account-info';
import { AccountInfoService } from '../../providers/account-info.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  isMenuCollapsed = true;

  firstName$! : String;
  roles$! : String[];

  constructor(private accountService : AccountService, private accountInfoService : AccountInfoService){}

  ngOnInit(): void {
    this.accountInfoService.abonner().subscribe({
      next: (value: AccountInfo) => {this.firstName$ = value.firstName; this.roles$ = value.roles}
    })
    this.accountService.getCurrentUser()
  }

  submit(){
    this.accountService.signout()
  }

}
