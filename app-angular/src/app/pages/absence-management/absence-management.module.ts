import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbsenceManagementPage } from './absence-management.page';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';



@NgModule({
  declarations: [
    AbsenceManagementPage
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgxPaginationModule
  ],
  exports: [
    AbsenceManagementPage
  ]
})
export class AbsenceManagementModule { }
