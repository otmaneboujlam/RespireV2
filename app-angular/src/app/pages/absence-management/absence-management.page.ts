import { Component, TemplateRef } from '@angular/core';
import { AbsenceInfo } from '../../models/absence-info';
import { AbsenceService } from '../../providers/absence.service';
import { AbsenceInfoService } from '../../providers/absence-info.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-absence-management',
  templateUrl: './absence-management.page.html',
  styleUrl: './absence-management.page.css'
})
export class AbsenceManagementPage {

  dateNow = new Date().toJSON().slice(0, 10);

  absencesInfo$! : [AbsenceInfo];
  absenceInfoInitialValue : AbsenceInfo= {
    "id": "0",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceStatus": "",
    "absenceType": "",
    "account": "",
  }
  absenceToDelete: AbsenceInfo = this.absenceInfoInitialValue;

  constructor(private absenceService : AbsenceService, private absenceInfoService : AbsenceInfoService, private modalService: NgbModal){}

  handleDelete() {
    this.absenceService.deleteAbsence(this.absenceToDelete.id).subscribe({
      next: () => {this.absenceService.getAbsences(), this.absenceToDelete = this.absenceInfoInitialValue},
      error: err => {console.log(err.error.message)}
    })
  }

  openVerticallyCentered(content: TemplateRef<any>, absence : AbsenceInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToDelete = absence;
	}

  ngOnInit(): void {
    this.absenceInfoService.abonner().subscribe({
      next: (value: [AbsenceInfo]) => this.absencesInfo$ = value
    })
    this.absenceService.getAbsences()
  }
}
