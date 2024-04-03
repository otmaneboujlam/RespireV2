import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HolidayManagmentPage } from './holiday-managment.page';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HolidayManagmentPage
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    HolidayManagmentPage
  ]
})
export class HolidayManagmentModule { }
