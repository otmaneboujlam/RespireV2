import { Injectable } from '@angular/core';
import { AbsenceInfo } from '../models/absence-info';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AbsenceProcessInfoService {

  absenceInfoInitialValue : AbsenceInfo= {
    "id": "",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceStatus": "",
    "absenceType": "",
    "account": "",
  }

  absenceProcessInfoSub = new BehaviorSubject<[AbsenceInfo]>([this.absenceInfoInitialValue]);

  constructor() { }

  publier(absencesInfo: [AbsenceInfo]){
    this.absenceProcessInfoSub.next(absencesInfo);
  }

  abonner() : Observable<[AbsenceInfo]>{
    return this.absenceProcessInfoSub.asObservable();
  }
}
