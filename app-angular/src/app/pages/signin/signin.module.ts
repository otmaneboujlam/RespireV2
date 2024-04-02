import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninPage } from './signin.page';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    SigninPage
  ],
  imports: [
    CommonModule,
    FormsModule,
  ],
  exports: [
    SigninPage
  ]
})
export class SigninModule { }
