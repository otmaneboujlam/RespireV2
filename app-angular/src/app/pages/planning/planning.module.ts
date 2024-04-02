import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanningPage } from './planning.page';
import { FullCalendarModule } from '@fullcalendar/angular';



@NgModule({
  declarations: [
    PlanningPage
  ],
  imports: [
    CommonModule,
    FullCalendarModule
  ],
  exports: [
    PlanningPage
  ]
})
export class PlanningModule { }
