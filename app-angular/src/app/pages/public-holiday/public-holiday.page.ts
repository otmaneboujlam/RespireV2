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

  ngOnInit(): void {
    this.absenceService.getAbsencesOrganization().subscribe({
      next: (values: [AbsenceOrganizationInfo]) => { this.absencesOrganizationInfo$ = values.filter(v => v.date.slice(0, 4) === this.dateNow && v.absenceStatus === "VALIDEE")}
    })
  }
}
