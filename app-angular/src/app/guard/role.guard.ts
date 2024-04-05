import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AccountInfoService } from '../providers/account-info.service';
import { AccountInfo } from '../models/account-info';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean |
UrlTree> | boolean | UrlTree => {

  const router = inject(Router);
  const accountInfoService = inject(AccountInfoService);
  const roles : any = localStorage.getItem("TOKEN");

  if(roles != null && roles.includes(route.data['role'])){
    return true;
  }
  else {
    router.navigateByUrl("/welcome");
    return false;
  }
};
