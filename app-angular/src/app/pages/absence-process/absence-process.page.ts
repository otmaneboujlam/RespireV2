import { Component, TemplateRef } from '@angular/core';
import { AbsenceInfo } from '../../models/absence-info';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AbsenceInfoService } from '../../providers/absence-info.service';
import { AbsenceService } from '../../providers/absence.service';
import { AbsenceProcessInfoService } from '../../providers/absence-process-info.service';

@Component({
  selector: 'app-absence-process',
  templateUrl: './absence-process.page.html',
  styleUrl: './absence-process.page.css'
})
export class AbsenceProcessPage {

  p: number = 1;
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

  absenceToValidate: AbsenceInfo = this.absenceInfoInitialValue;
  absenceToReject: AbsenceInfo = this.absenceInfoInitialValue;

  constructor(private absenceService : AbsenceService, private absenceProcessInfoService : AbsenceProcessInfoService, private modalService: NgbModal){}

  handleValidate() {
    this.absenceService.putAbsenceProcess({id : this.absenceToValidate.id, absenceStatus : "VALIDEE"}).subscribe({
      next: absence => {this.absenceService.getAbsencesProcess();this.absenceToValidate = this.absenceInfoInitialValue;},
      error: err => {this.absenceToValidate = this.absenceInfoInitialValue;}
    })
  }

  handleReject() {
    this.absenceService.putAbsenceProcess({id : this.absenceToReject.id, absenceStatus : "REJETEE"}).subscribe({
      next: absence => {this.absenceService.getAbsencesProcess();this.absenceToReject = this.absenceInfoInitialValue;},
      error: err => {this.absenceToReject = this.absenceInfoInitialValue;}
    })
  }

  openModalValidate(content: TemplateRef<any>, absence : AbsenceInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToValidate = absence;
	}

  openModalReject(content: TemplateRef<any>, absence : AbsenceInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToReject = absence;
	}

  isSortedUp : boolean = true;

  sort() {
    if(this.isSortedUp) {
      this.absencesInfo$.sort(
        (a, b) : number => {
          return Date.parse(a.startDate.slice(0, 10)) - Date.parse(b.startDate.slice(0, 10))
        }
      )
    }
    else {
      this.absencesInfo$.sort(
        (b, a) : number => {
          return Date.parse(a.startDate.slice(0, 10)) - Date.parse(b.startDate.slice(0, 10))
        }
      )
    }
    this.isSortedUp = !this.isSortedUp;
  }

  ngOnInit(): void {
    this.absenceProcessInfoService.abonner().subscribe({
      next: (value: [AbsenceInfo]) => {this.absencesInfo$ = value}
    })
    this.absenceService.getAbsencesProcess()
  }
}
