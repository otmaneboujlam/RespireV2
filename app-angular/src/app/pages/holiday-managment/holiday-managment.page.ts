import { Component, TemplateRef } from '@angular/core';
import { AbsenceOrganizationInfo } from '../../models/absence-organization-info';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AbsenceOrganizationPost } from '../../models/absence-organization-post';
import { AbsenceOrganizationService } from '../../providers/absence-organization.service';
import { AbsenceOrganizationPut } from '../../models/absence-organization-put';
import { OrganizationService } from '../../providers/organization.service';
import { OrganizationInfo } from '../../models/organization-info';

@Component({
  selector: 'app-holiday-managment',
  templateUrl: './holiday-managment.page.html',
  styleUrl: './holiday-managment.page.css'
})
export class HolidayManagmentPage {

  dateNow = new Date().toJSON().slice(0, 10);
  dateMax = this.dateNow.slice(0,4)+"-12-31"
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
  organizations$! : OrganizationInfo[];
  selectedValue = this.absenceType[0];
  selectedValuePut = this.absenceType[0];
  selectedValueOrganization$! : OrganizationInfo;
  selectedValueOrganizationPut$! : OrganizationInfo;
  absenceToDelete: AbsenceOrganizationInfo = this.absenceInfoInitialValue;
  absenceToUpdate: AbsenceOrganizationPut = this.absencePutInitialValue;
  isError : boolean = false;
  errorMsg$! : String;

  constructor(private modalService: NgbModal, private absenceOrganiztionService : AbsenceOrganizationService, private organizationService : OrganizationService){}

  handlePut() {
    this.getAbsencePut.id = this.absenceToUpdate.id;
    this.getAbsencePut.absenceOrganizationType = this.selectedValuePut.name;
    this.getAbsencePut.organization = this.selectedValueOrganizationPut$.name;
    this.absenceOrganiztionService.putAbsenceOrganization(this.getAbsencePut).subscribe({
      next: () => {
        this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
          next: value => this.absencesOrganizationInfo$ = value,
          error: err => console.log(err)
        });
        this.getAbsencePut = this.absencePutInitialValue;
        this.absenceToUpdate = this.absencePutInitialValue;
        this.selectedValuePut = this.absenceType[0];
        this.selectedValueOrganizationPut$ = this.organizations$[0];
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
    this.getAbsencePost.organization = this.selectedValueOrganization$.name;
    this.absenceOrganiztionService.postAbsenceOrganization(this.getAbsencePost).subscribe({
      next: absence => {
        this.absenceOrganiztionService.getAbsencesOrganizationAll().subscribe({
          next: value => this.absencesOrganizationInfo$ = value,
          error: err => console.log(err.error.message)
        });
        this.getAbsencePost = this.absencePostInitialValue;
        this.selectedValue = this.absenceType[0];
        this.selectedValueOrganization$ = this.organizations$[0];
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
    for(let orga of this.organizations$) {
      if(absence.organization === orga.name){
        this.selectedValueOrganizationPut$ = orga;
        break;
      }
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

    this.organizationService.getOrganizations().subscribe({
      next: value => {
        this.organizations$ = value;
        if(value.length){
          this.selectedValueOrganization$ = value[0];
          this.selectedValueOrganizationPut$ = value[0];
        }
      }
    })
  }
}
