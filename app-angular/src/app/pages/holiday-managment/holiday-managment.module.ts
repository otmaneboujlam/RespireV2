import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HolidayManagmentPage } from './holiday-managment.page';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';



@NgModule({
  declarations: [
    HolidayManagmentPage
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgxPaginationModule
  ],
  exports: [
    HolidayManagmentPage
  ]
})
export class HolidayManagmentModule { }
