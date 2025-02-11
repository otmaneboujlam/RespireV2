import { Component } from '@angular/core';
import { AbsenceOrganizationInfo } from '../../models/absence-organization-info';
import { AbsenceService } from '../../providers/absence.service';

@Component({
  selector: 'app-public-holiday',
  templateUrl: './public-holiday.page.html',
  styleUrl: './public-holiday.page.css'
})
export class PublicHolidayPage {

  dateNow = new Date().toJSON().slice(0, 4);
  absencesOrganizationInfo$! : AbsenceOrganizationInfo[];

  constructor(private absenceService : AbsenceService){}

  p: number = 1;

  isSortedUp : boolean = true;

  sort() {
    if(this.isSortedUp) {
      this.absencesOrganizationInfo$.sort(
        (a, b) : number => {
          return Date.parse(a.date.slice(0, 10)) - Date.parse(b.date.slice(0, 10))
        }
      )
    }
    else {
      this.absencesOrganizationInfo$.sort(
        (b, a) : number => {
          return Date.parse(a.date.slice(0, 10)) - Date.parse(b.date.slice(0, 10))
        }
      )
    }
    this.isSortedUp = !this.isSortedUp;
  }

  ngOnInit(): void {
    this.absenceService.getAbsencesOrganization().subscribe({
      next: (values: [AbsenceOrganizationInfo]) => { this.absencesOrganizationInfo$ = values.filter(v => v.date.slice(0, 4) === this.dateNow && v.absenceStatus === "VALIDEE").sort(
        (a, b) : number => {
          return Date.parse(b.date.slice(0, 10)) - Date.parse(a.date.slice(0, 10))
        }
        )}
    })
  }
}
