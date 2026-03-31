import { Component } from '@angular/core';
import { DashboardComponent } from './dashboard/dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DashboardComponent],
  template: `
    <div class="container">
      <h1>Ticket Management System</h1>
      <app-dashboard></app-dashboard>
    </div>
  `
})
export class AppComponent {
  title = 'Ticket Management System';
}
