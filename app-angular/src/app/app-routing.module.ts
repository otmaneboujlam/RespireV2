import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomePage } from './pages/welcome/welcome.page';
import { SigninPage } from './pages/signin/signin.page';

const routes: Routes = [
  { path: 'signin', component: SigninPage},
  { path: 'welcome', component: WelcomePage}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
