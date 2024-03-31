import { Component } from '@angular/core';
import { IsLoggedInService } from './providers/is-logged-in.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'app-angular';
  isLogged$! : boolean;

  constructor(private isLoggedIn : IsLoggedInService){}

  ngOnInit(){
    this.isLoggedIn.abonner().subscribe({
      next: (value: boolean) => {this.isLogged$ = value}
    })
  }
}
