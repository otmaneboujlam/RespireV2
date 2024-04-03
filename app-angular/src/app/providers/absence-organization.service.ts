import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbsenceOrganizationInfo } from '../models/absence-organization-info';
import { AbsenceOrganizationPost } from '../models/absence-organization-post';
import { AbsenceOrganizationPut } from '../models/absence-organization-put';

@Injectable({
  providedIn: 'root'
})
export class AbsenceOrganizationService {

  constructor(private http: HttpClient) { }

  getAbsencesOrganizationAll = ()=> {
    return this.http.get<[AbsenceOrganizationInfo]>("http://localhost:8082/api/absenceorganization/all",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  deleteAbsenceOrganization = (id : String)=> {
    return this.http.delete<any>("http://localhost:8082/api/absenceorganization/"+id,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    });
  }

  postAbsenceOrganization = (absence : AbsenceOrganizationPost)=> {
    return this.http.post<any>("http://localhost:8082/api/absenceorganization", absence,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  putAbsenceOrganization = (absence : AbsenceOrganizationPut)=> {
    return this.http.put<any>("http://localhost:8082/api/absenceorganization", absence,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }
}
