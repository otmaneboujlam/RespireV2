import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbsenceInfo } from '../models/absence-info';
import { AbsenceInfoService } from './absence-info.service';
import { AbsencePost } from '../models/absence-post';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService {

  constructor(private http: HttpClient, private absenceInfoService : AbsenceInfoService) { }

  getAbsences = ()=> {
    return this.http.get<[AbsenceInfo]>("http://localhost:8082/api/absence/currentuser",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    }).subscribe(
      {
        next: absencesInfo => this.absenceInfoService.publier(absencesInfo)
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
}
