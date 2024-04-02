import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbsenceInfo } from '../models/absence-info';
import { AbsenceOrganizationInfo } from '../models/absence-organization-info';

@Injectable({
  providedIn: 'root'
})
export class PlanningService {

  constructor(private http: HttpClient) { }

  getAbsences = ()=> {
    return this.http.get<[AbsenceInfo]>("http://localhost:8082/api/absence/currentuser",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  getAbsencesOrganization = ()=> {
    return this.http.get<[AbsenceOrganizationInfo]>("http://localhost:8082/api/absenceorganization/organization",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

}
