import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AccountService } from '../providers/account.service';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, statee: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean |
UrlTree> | boolean | UrlTree => {

  const router = inject(Router);
  const accountService = inject(AccountService);

  if(true){
    return true;
  }
  else {
    router.navigateByUrl("/login");
    return false;
  }
};
