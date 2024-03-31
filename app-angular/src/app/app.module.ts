import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { WelcomeModule } from './pages/welcome/welcome.module';
import { SharedModule } from './shared/shared.module';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { SigninModule } from './pages/signin/signin.module';
import { AbsenceManagementModule } from './pages/absence-management/absence-management.module';
import { PlanningModule } from './pages/planning/planning.module';
import { AbsenceProcessModule } from './pages/absence-process/absence-process.module';
import { SyntheticViewModule } from './pages/synthetic-view/synthetic-view.module';
import { PublicHolidayModule } from './pages/public-holiday/public-holiday.module';
import { ProfileModule } from './pages/profile/profile.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    WelcomeModule,
    SigninModule,
    SharedModule,
    AppRoutingModule,
    HttpClientModule,
    AbsenceManagementModule,
    PlanningModule,
    AbsenceProcessModule,
    SyntheticViewModule,
    PublicHolidayModule,
    ProfileModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
