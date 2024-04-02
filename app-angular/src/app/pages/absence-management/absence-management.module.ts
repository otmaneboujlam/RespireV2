import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbsenceManagementPage } from './absence-management.page';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AbsenceManagementPage
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    AbsenceManagementPage
  ]
})
export class AbsenceManagementModule { }
