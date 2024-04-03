import { Component, TemplateRef } from '@angular/core';
import { AbsenceInfo } from '../../models/absence-info';
import { AbsenceService } from '../../providers/absence.service';
import { AbsenceInfoService } from '../../providers/absence-info.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AbsencePost } from '../../models/absence-post';
import { AbsenceScore } from '../../models/absence-score';
import { AbsencePut } from '../../models/absence-put';

@Component({
  selector: 'app-absence-management',
  templateUrl: './absence-management.page.html',
  styleUrl: './absence-management.page.css'
})
export class AbsenceManagementPage {

  absenceScore$! : AbsenceScore
  dateNow = new Date().toJSON().slice(0, 10);
  absencesInfo$! : [AbsenceInfo];
  absencePostInitialValue : AbsencePost = {
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceType": "",
  }
  getAbsencePost : AbsencePost = {
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceType": "",
  }
  getAbsencePut : AbsencePut = {
    "id": "",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceType": "",
  }
  absenceInfoInitialValue : AbsenceInfo= {
    "id": "0",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceStatus": "",
    "absenceType": "",
    "account": "",
  }
  absencePutInitialValue : AbsencePut= {
    "id": "0",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceType": ""
  }
  absenceType = [
    {id: 1, name: "CONGE_PAYE"},
    {id: 2, name: "RTT_EMPLOYEE"},
    {id: 3, name: "CONGE_SANS_SOLDE"}
  ];
  selectedValue = this.absenceType[0];
  selectedValuePut = this.absenceType[0];
  absenceToDelete: AbsenceInfo = this.absenceInfoInitialValue;
  absenceToUpdate: AbsencePut = this.absencePutInitialValue;
  isError : boolean = false;
  errorMsg$! : String;

  constructor(private absenceService : AbsenceService, private absenceInfoService : AbsenceInfoService, private modalService: NgbModal){}

  handlePut() {
    console.log(this.absenceToUpdate);
    console.log(this.getAbsencePut);
    this.getAbsencePut.id = this.absenceToUpdate.id;
    this.getAbsencePut.absenceType = this.selectedValuePut.name;
    if(this.getAbsencePut.absenceType == "RTT_EMPLOYEE"){
      this.getAbsencePut.endDate = this.getAbsencePut.startDate;
    }
    this.absenceService.putAbsence(this.getAbsencePut).subscribe({
      next: () => {
        this.absenceService.getAbsences();
        this.getAbsencePut = this.absencePutInitialValue;
        this.absenceToUpdate = this.absencePutInitialValue;
        this.selectedValuePut = this.absenceType[0];
      },
      error: err => {
        this.isError = true;
        this.errorMsg$ = err.error.message;
        setTimeout(()=> {
          this.isError=false;
        },3000);
      }
    })
  }

  handleDelete() {
    this.absenceService.deleteAbsence(this.absenceToDelete.id).subscribe({
      next: () => {
        this.absenceService.getAbsences(),
        this.absenceToDelete = this.absenceInfoInitialValue,
        this.absenceService.getAbsenceScore().subscribe({
        next: value => {this.absenceScore$ = value}
      })},
      error: err => {
        this.isError = true;
        this.errorMsg$ = err.error.message;
        setTimeout(()=> {
          this.isError=false;
        },3000);
      }
    })
  }

  handlePost() {
    this.getAbsencePost.absenceType = this.selectedValue.name;
    if(this.getAbsencePost.absenceType == "RTT_EMPLOYEE"){
      this.getAbsencePost.endDate = this.getAbsencePost.startDate;
    }
    this.absenceService.postAbsence(this.getAbsencePost).subscribe({
      next: absence => {
        this.absenceService.getAbsences();
        this.getAbsencePost = this.absencePostInitialValue;
        this.selectedValue = this.absenceType[0];
      },
      error: err => {
        this.isError = true;
        this.errorMsg$ = err.error.message;
        setTimeout(()=> {
          this.isError=false;
        },3000);
      }
    })
  }

  openModalUpdate(content: TemplateRef<any>, absence : AbsenceInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToUpdate = absence;
	}

  openModalDelete(content: TemplateRef<any>, absence : AbsenceInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToDelete = absence;
	}

  openModalPost(content: TemplateRef<any>) {
		this.modalService.open(content, { centered: true });
	}

  ngOnInit(): void {
    this.absenceInfoService.abonner().subscribe({
      next: (value: [AbsenceInfo]) => this.absencesInfo$ = value
    })
    this.absenceService.getAbsences()
    this.absenceService.getAbsenceScore().subscribe({
      next: value => {this.absenceScore$ = value}
    })
  }
}
