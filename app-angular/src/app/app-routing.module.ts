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

const routes: Routes = [
  { path: 'signin', component: SigninPage},
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'welcome', component: WelcomePage},
      { path: 'absencemanagement', component: AbsenceManagementPage},
      { path: 'planning', component: PlanningPage},
      { path: 'absenceprocess', component: AbsenceProcessPage},
      { path: 'syntheticview', component: SyntheticViewPage},
      { path: 'publicholiday', component: PublicHolidayPage},
      { path: 'profile', component: ProfilePage},
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
