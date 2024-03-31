import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbsenceManagementPage } from './absence-management.page';
import { SharedModule } from '../../shared/shared.module';



@NgModule({
  declarations: [
    AbsenceManagementPage
  ],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class AbsenceManagementModule { }
