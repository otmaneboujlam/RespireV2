import { PlanningService } from './../../providers/planning.service';
import { Component } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import { AbsenceInfo } from '../../models/absence-info';
import { AbsenceOrganizationInfo } from '../../models/absence-organization-info';


@Component({
  selector: 'app-planning',
  templateUrl: './planning.page.html',
  styleUrl: './planning.page.css'
})
export class PlanningPage {

  events : any = [];
  isOkAbsence : boolean = false;
  isOkAbsenceOrganization : boolean = false;

  constructor(private planningService : PlanningService){}

  colorEvent = (type: any) => {
		if (type === 'RTT_EMPLOYEE') {
			return '#30b16d';
		}
		if (type === 'RTT_EMPLOYEUR') {
			return '#d71a4a';
		}
		if (type === 'CONGE_PAYE') {
			return '#48289f';
		}
		if (type === 'CONGE_SANS_SOLDE') {
			return '#a938a7';
		}
		if (type === 'JOUR_FERIE') {
			return '#973c3c';
		}
    else return ""
	};

  ngOnInit(): void {
    this.planningService.getAbsences().subscribe({
      next: (values: [AbsenceInfo]) => {
        this.isOkAbsence = true;
        for(let value of values) {
          if(value.absenceStatus=="VALIDEE") {
            this.events.push({
              title: value.absenceType,
              start: Date.parse(value.startDate),
              end: Date.parse(value.endDate),
              allDay: true,
              backgroundColor : this.colorEvent(value.absenceType)
            })
          }
        }
      }
    })
    this.planningService.getAbsencesOrganization().subscribe({
      next: (values: [AbsenceOrganizationInfo]) => {
        this.isOkAbsenceOrganization = true;
        for(let value of values) {
          if(value.absenceStatus=="VALIDEE") {
            this.events.push({
              title: value.absenceType,
              start: Date.parse(value.date),
              end: Date.parse(value.date),
              allDay: true,
              backgroundColor : this.colorEvent(value.absenceType)
            })
          }
        }
      }
    })
  }

  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin],
    initialView: 'dayGridMonth',
    weekends: true
  };
}
