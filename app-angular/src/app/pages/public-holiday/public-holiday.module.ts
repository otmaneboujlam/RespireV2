import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicHolidayPage } from './public-holiday.page';
import { NgxPaginationModule } from 'ngx-pagination';



@NgModule({
  declarations: [
    PublicHolidayPage
  ],
  imports: [
    CommonModule,
    NgxPaginationModule
  ],
  exports: [
    PublicHolidayPage
  ]
})
export class PublicHolidayModule { }
