import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbsenceProcessPage } from './absence-process.page';
import { NgxPaginationModule } from 'ngx-pagination';


@NgModule({
  declarations: [
    AbsenceProcessPage
  ],
  imports: [
    CommonModule,
    NgxPaginationModule
  ],
  exports: [
    AbsenceProcessPage
  ]
})
export class AbsenceProcessModule { }
