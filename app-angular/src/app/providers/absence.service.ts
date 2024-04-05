import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbsenceInfo } from '../models/absence-info';
import { AbsenceInfoService } from './absence-info.service';
import { AbsencePost } from '../models/absence-post';
import { AbsenceProcessInfoService } from './absence-process-info.service';
import { AbsenceUpdateStatus } from '../models/absence-update-status';
import { AbsenceScore } from '../models/absence-score';
import { AbsenceOrganizationInfo } from '../models/absence-organization-info';
import { AbsencePut } from '../models/absence-put';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService {

  constructor(private http: HttpClient, private absenceInfoService : AbsenceInfoService, private absenceProcessInfoService : AbsenceProcessInfoService) { }

  getAbsencesOrganization = ()=> {
    return this.http.get<[AbsenceOrganizationInfo]>("http://localhost:8082/api/absenceorganization/organization",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  getAbsenceScore = () => {
    return this.http.get<AbsenceScore>("http://localhost:8082/api/currentuser/absencescore",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  getAbsences = ()=> {
    return this.http.get<[AbsenceInfo]>("http://localhost:8082/api/absence/currentuser",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    }).subscribe(
      {
        next: absencesInfo => this.absenceInfoService.publier(absencesInfo.sort(
          (a, b) : number => {
            return Date.parse(b.startDate.slice(0, 10)) - Date.parse(a.startDate.slice(0, 10))
          }
          ))
      }
    );
  }

  deleteAbsence = (id : String)=> {
    return this.http.delete<any>("http://localhost:8082/api/absence/"+id,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    });
  }

  postAbsence = (absence : AbsencePost)=> {
    return this.http.post<any>("http://localhost:8082/api/absence", absence,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  getAbsencesProcess = ()=> {
    return this.http.get<[AbsenceInfo]>("http://localhost:8082/api/absence/group/EN_ATTENTE_VALIDATION",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    }).subscribe(
      {
        next: absencesInfo => this.absenceProcessInfoService.publier(absencesInfo.sort(
          (a, b) : number => {
            return Date.parse(b.startDate.slice(0, 10)) - Date.parse(a.startDate.slice(0, 10))
          }
          ))
      }
    );
  }

  putAbsenceProcess = (absence : AbsenceUpdateStatus)=> {
    return this.http.put<any>("http://localhost:8082/api/absence/process", absence,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }

  putAbsence = (absence : AbsencePut)=> {
    return this.http.put<any>("http://localhost:8082/api/absence", absence,{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
  }
}
