import { Injectable } from '@angular/core';
import { AbsenceInfo } from '../models/absence-info';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AbsenceInfoService {

  absenceInfoInitialValue : AbsenceInfo= {
    "id": "",
    "startDate": "",
    "endDate": "",
    "reason": "",
    "absenceStatus": "",
    "absenceType": "",
    "account": "",
}

  absenceInfoSub = new BehaviorSubject<[AbsenceInfo]>([this.absenceInfoInitialValue]);

  constructor() { }

  publier(absencesInfo: [AbsenceInfo]){
    this.absenceInfoSub.next(absencesInfo);
  }

  abonner() : Observable<[AbsenceInfo]>{
    return this.absenceInfoSub.asObservable();
  }
}
