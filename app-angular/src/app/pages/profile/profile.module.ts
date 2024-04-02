import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfilePage } from './profile.page';



@NgModule({
  declarations: [
    ProfilePage
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ProfilePage
  ]
})
export class ProfileModule { }
