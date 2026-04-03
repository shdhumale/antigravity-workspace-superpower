import { Component } from '@angular/core';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DashboardComponent, HeaderComponent, FooterComponent],
  template: `
    <div class="min-h-screen flex flex-col bg-white dark:bg-slate-950 text-slate-900 dark:text-white transition-colors duration-300">
      <app-header></app-header>
      <main class="flex-1">
        <app-dashboard></app-dashboard>
      </main>
      <app-footer></app-footer>
    </div>
  `
})
export class AppComponent {
  title = 'Ticket Management System';
}
