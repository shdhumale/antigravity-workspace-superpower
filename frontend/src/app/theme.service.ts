import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private darkMode = false;

  constructor() {
    try {
      const savedTheme = localStorage.getItem('theme');
      if (savedTheme === 'dark' || (!savedTheme && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
        this.enableDarkMode();
      }
    } catch {
      // Fallback to light mode if localStorage is unavailable
    }
  }

  toggle(): void {
    if (this.darkMode) {
      this.disableDarkMode();
    } else {
      this.enableDarkMode();
    }
  }

  private enableDarkMode(): void {
    this.darkMode = true;
    document.documentElement.classList.add('dark');
    try {
      localStorage.setItem('theme', 'dark');
    } catch { /* ignore */ }
  }

  private disableDarkMode(): void {
    this.darkMode = false;
    document.documentElement.classList.remove('dark');
    try {
      localStorage.setItem('theme', 'light');
    } catch { /* ignore */ }
  }

  isDark(): boolean {
    return this.darkMode;
  }
}
