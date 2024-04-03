import { Component, TemplateRef } from '@angular/core';
import { AbsenceOrganizationInfo } from '../../models/absence-organization-info';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AbsenceOrganizationPost } from '../../models/absence-organization-post';
import { AbsenceOrganizationService } from '../../providers/absence-organization.service';

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
  getAbsencePost : AbsenceOrganizationPost = {
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
  absenceToDelete: AbsenceOrganizationInfo = this.absenceInfoInitialValue;
  isError : boolean = false;
  errorMsg$! : String;

  constructor(private modalService: NgbModal, private absenceOrganiztionService : AbsenceOrganizationService){}

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
