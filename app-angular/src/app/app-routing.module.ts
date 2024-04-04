import { PublicHolidayPage } from './pages/public-holiday/public-holiday.page';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomePage } from './pages/welcome/welcome.page';
import { SigninPage } from './pages/signin/signin.page';
import { authGuard } from './guard/auth.guard';
import { AbsenceManagementPage } from './pages/absence-management/absence-management.page';
import { PlanningPage } from './pages/planning/planning.page';
import { AbsenceProcessPage } from './pages/absence-process/absence-process.page';
import { SyntheticViewPage } from './pages/synthetic-view/synthetic-view.page';
import { ProfilePage } from './pages/profile/profile.page';
import { HolidayManagmentPage } from './pages/holiday-managment/holiday-managment.page';
import { roleGuard } from './guard/role.guard';

const routes: Routes = [
  { path: 'signin', component: SigninPage},
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'welcome', component: WelcomePage},
      { path: 'absencemanagement', component: AbsenceManagementPage, canActivate: [roleGuard], data: {
        role: 'ROLE_USER'
      }},
      { path: 'planning', component: PlanningPage, canActivate: [roleGuard], data: {
        role: 'ROLE_USER'
      }},
      { path: 'absenceprocess', component: AbsenceProcessPage, canActivate: [roleGuard], data: {
        role: 'ROLE_MANAGER'
      }},
      { path: 'syntheticview', component: SyntheticViewPage, canActivate: [roleGuard], data: {
        role: 'ROLE_MANAGER'
      }},
      { path: 'publicholiday', component: PublicHolidayPage, canActivate: [roleGuard], data: {
        role: 'ROLE_USER'
      }},
      { path: 'publicholidaymanagment', component: HolidayManagmentPage, canActivate: [roleGuard], data: {
        role: 'ROLE_ADMIN'
      }},
      { path: 'profile', component: ProfilePage, canActivate: [roleGuard], data: {
        role: 'ROLE_USER'
      }},
      { path: '', pathMatch: 'full', redirectTo: '/welcome'},
      { path: '**', component: WelcomePage},
    ]
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
