import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThemeService } from '../theme.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  open = false;
  scrolled = false;

  links = [
    { label: 'Dashboard', href: '#' },
    { label: 'Tickets', href: '#' },
    { label: 'About', href: '#' },
  ];

  constructor(public themeService: ThemeService) {}

  ngOnInit(): void {}

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    this.scrolled = window.scrollY > 10;
  }

  toggleMenu(): void {
    this.open = !this.open;
    if (this.open) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = '';
    }
  }

  toggleTheme(): void {
    this.themeService.toggle();
  }
}
