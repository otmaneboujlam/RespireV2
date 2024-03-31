import { Component } from '@angular/core';
import { AbsenceInfo } from '../../models/absence-info';
import { AbsenceService } from '../../providers/absence.service';
import { AbsenceInfoService } from '../../providers/absence-info.service';

@Component({
  selector: 'app-absence-management',
  templateUrl: './absence-management.page.html',
  styleUrl: './absence-management.page.css'
})
export class AbsenceManagementPage {

  absencesInfo$! : [AbsenceInfo];

  constructor(private absenceService : AbsenceService, private absenceInfoService : AbsenceInfoService){}

  ngOnInit(): void {
    this.absenceInfoService.abonner().subscribe({
      next: (value: [AbsenceInfo]) => this.absencesInfo$ = value
    })
    this.absenceService.getAbsences()
  }
}
