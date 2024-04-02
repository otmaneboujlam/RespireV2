import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomePage } from './welcome.page';



@NgModule({
  declarations: [
    WelcomePage
  ],
  imports: [
    CommonModule
  ],
  exports: [
    WelcomePage
  ]
})
export class WelcomeModule { }
