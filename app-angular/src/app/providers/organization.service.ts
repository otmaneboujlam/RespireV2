import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrganizationInfo } from '../models/organization-info';

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {

  constructor(private http: HttpClient) { }

  getOrganizations = ()=> {
    return this.http.get<[OrganizationInfo]>("http://localhost:8082/api/organization",{
      headers: new HttpHeaders({
      "Content-Type": "application/json"
      }),
      withCredentials : true
    })
}
}
