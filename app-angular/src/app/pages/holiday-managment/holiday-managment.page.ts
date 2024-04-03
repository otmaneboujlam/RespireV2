import { Component, TemplateRef } from '@angular/core';
import { AbsenceOrganizationInfo } from '../../models/absence-organization-info';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AbsenceOrganizationPost } from '../../models/absence-organization-post';
import { AbsenceOrganizationService } from '../../providers/absence-organization.service';
import { AbsenceOrganizationPut } from '../../models/absence-organization-put';

@Component({
  selector: 'app-holiday-managment',
  templateUrl: './holiday-managment.page.html',
  styleUrl: './holiday-managment.page.css'
})
export class HolidayManagmentPage {

  dateNow = new Date().toJSON().slice(0, 10);
  absencesOrganizationInfo$! : [AbsenceOrganizationInfo];
  absencePostInitialValue : AbsenceOrganizationPost = {
    "date": "",
    "organization": "",
    "reason": "",
    "absenceOrganizationType": "",
  }
  absencePutInitialValue : AbsenceOrganizationPut = {
    "id": "",
    "date": "",
    "organization": "",
    "reason": "",
    "absenceOrganizationType": "",
  }
  getAbsencePost : AbsenceOrganizationPost = {
    "date": "",
    "organization": "",
    "reason": "",
    "absenceOrganizationType": "",
  }
  getAbsencePut : AbsenceOrganizationPut = {
    "id": "",
    "date": "",
    "organization": "",
    "reason": "",
    "absenceOrganizationType": "",
  }
  absenceInfoInitialValue : AbsenceOrganizationInfo= {
    "id": "0",
    "date": "",
    "organization": "",
    "reason": "",
    "absenceStatus": "",
    "absenceType": ""
  }
  absenceType = [
    {id: 1, name: "JOUR_FERIE"},
    {id: 2, name: "RTT_EMPLOYEUR"}
  ];
  selectedValue = this.absenceType[0];
  selectedValuePut = this.absenceType[0];
  absenceToDelete: AbsenceOrganizationInfo = this.absenceInfoInitialValue;
  absenceToUpdate: AbsenceOrganizationPut = this.absencePutInitialValue;
  isError : boolean = false;
  errorMsg$! : String;

  constructor(private modalService: NgbModal, private absenceOrganiztionService : AbsenceOrganizationService){}

  handlePut() {
    this.getAbsencePut.id = this.absenceToUpdate.id;
    this.getAbsencePut.absenceOrganizationType = this.selectedValuePut.name;
    console.log(this.getAbsencePut)
    this.absenceOrganiztionService.putAbsenceOrganization(this.getAbsencePut).subscribe({
      next: () => {
        this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
          next: value => this.absencesOrganizationInfo$ = value,
          error: err => console.log(err)
        });
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
    this.absenceOrganiztionService.deleteAbsenceOrganization(this.absenceToDelete.id).subscribe({
      next: () => {
        this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
          next: value => this.absencesOrganizationInfo$ = value,
          error: err => console.log(err.error.message)
        }),
        this.absenceToDelete = this.absenceInfoInitialValue
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

  handlePost() {
    this.getAbsencePost.absenceOrganizationType = this.selectedValue.name;
    this.absenceOrganiztionService.postAbsenceOrganization(this.getAbsencePost).subscribe({
      next: absence => {
        this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
          next: value => this.absencesOrganizationInfo$ = value,
          error: err => console.log(err.error.message)
        });
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

  openModalUpdate(content: TemplateRef<any>, absence : AbsenceOrganizationInfo) {
    this.getAbsencePut.id = absence.id
    this.getAbsencePut.date = absence.date
    this.getAbsencePut.absenceOrganizationType = absence.absenceType
    this.getAbsencePut.reason = absence.reason
    this.getAbsencePut.organization = absence.organization
    if(absence.absenceType === "JOUR_FERIE") {
      this.selectedValuePut = this.absenceType[0];
    }
    else {
      this.selectedValuePut = this.absenceType[1];
    }
		this.modalService.open(content, { centered: true });
    this.absenceToUpdate.id = absence.id
    this.absenceToUpdate.date = absence.date
    this.absenceToUpdate.absenceOrganizationType = absence.absenceType
    this.absenceToUpdate.reason = absence.reason
    this.absenceToUpdate.organization = absence.organization
	}

  openModalDelete(content: TemplateRef<any>, absence : AbsenceOrganizationInfo) {
		this.modalService.open(content, { centered: true });
    this.absenceToDelete = absence;
	}

  openModalPost(content: TemplateRef<any>) {
		this.modalService.open(content, { centered: true });
	}

  ngOnInit(): void {
    this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
      next: value => this.absencesOrganizationInfo$ = value,
      error: err => console.log(err)
    })
  }
}
