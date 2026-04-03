# Dark Mode & Component Integration Implementation Plan

> **For Antigravity:** REQUIRED WORKFLOW: Use `.agent/workflows/execute-plan.md` to execute this plan in single-flow mode.

**Goal:** Implement a persistent Dark Mode theme and integrate responsive Header and Footer components into the Angular application using Tailwind CSS.

**Architecture:** A singleton `ThemeService` will manage the theme state and persist it in `localStorage`. The `HeaderComponent` and `FooterComponent` will be native Angular components styled with Tailwind CSS, using the `dark:` prefix for theme-specific styling.

**Tech Stack:** Angular (v16.2.0), Tailwind CSS, Lucide Icons (as SVGs), TypeScript.

---

### Task 1: Setup Tailwind CSS
**Files:**
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\tailwind.config.js`
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\postcss.config.js`
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\styles.css`

**Step 1: Install dependencies**
Run: `npm install -D tailwindcss postcss autoprefixer`

**Step 2: Initialize Tailwind**
Run: `npx tailwindcss init`

**Step 3: Configure `tailwind.config.js`**
```javascript
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
    },
  },
  plugins: [],
}
```

**Step 4: Update `src/styles.css`**
```css
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer base {
  :root {
    --background: 0 0% 100%;
    --foreground: 222.2 84% 4.9%;
    --card: 0 0% 100%;
    --card-foreground: 222.2 84% 4.9%;
    --popover: 0 0% 100%;
    --popover-foreground: 222.2 84% 4.9%;
    --primary: 222.2 47.4% 11.2%;
    --primary-foreground: 210 40% 98%;
    --secondary: 210 40% 96.1%;
    --secondary-foreground: 222.2 47.4% 11.2%;
    --muted: 210 40% 96.1%;
    --muted-foreground: 215.4 16.3% 46.9%;
    --accent: 210 40% 96.1%;
    --accent-foreground: 222.2 47.4% 11.2%;
    --destructive: 0 84.2% 60.2%;
    --destructive-foreground: 210 40% 98%;
    --border: 214.3 31.8% 91.4%;
    --input: 214.3 31.8% 91.4%;
    --ring: 222.2 84% 4.9%;
    --radius: 0.5rem;
  }

  .dark {
    --background: 222.2 84% 4.9%;
    --foreground: 210 40% 98%;
    --card: 222.2 84% 4.9%;
    --card-foreground: 210 40% 98%;
    --popover: 222.2 84% 4.9%;
    --popover-foreground: 210 40% 98%;
    --primary: 210 40% 98%;
    --primary-foreground: 222.2 47.4% 11.2%;
    --secondary: 217.2 32.6% 17.5%;
    --secondary-foreground: 210 40% 98%;
    --muted: 217.2 32.6% 17.5%;
    --muted-foreground: 215 20.2% 65.1%;
    --accent: 217.2 32.6% 17.5%;
    --accent-foreground: 210 40% 98%;
    --destructive: 0 62.8% 30.6%;
    --destructive-foreground: 210 40% 98%;
    --border: 217.2 32.6% 17.5%;
    --input: 217.2 32.6% 17.5%;
    --ring: 212.7 26.8% 83.9%;
  }
}
```

**Step 5: Commit**
```bash
git add package.json tailwind.config.js postcss.config.js src/styles.css
git commit -m "chore: setup tailwind css with shadcn-like variables"
```

---

### Task 2: Implement ThemeService
**Files:**
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\theme.service.ts`

**Step 1: Write the service**
```typescript
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private darkMode = false;

  constructor() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark' || (!savedTheme && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
      this.enableDarkMode();
    }
  }

  toggle() {
    if (this.darkMode) {
      this.disableDarkMode();
    } else {
      this.enableDarkMode();
    }
  }

  private enableDarkMode() {
    this.darkMode = true;
    document.documentElement.classList.add('dark');
    localStorage.setItem('theme', 'dark');
  }

  private disableDarkMode() {
    this.darkMode = false;
    document.documentElement.classList.remove('dark');
    localStorage.setItem('theme', 'light');
  }

  isDark(): boolean {
    return this.darkMode;
  }
}
```

**Step 2: Commit**
```bash
git add src/app/theme.service.ts
git commit -m "feat: add ThemeService for dark mode management"
```

---

### Task 3: Create Header Component
**Files:**
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\header\header.component.ts`
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\header\header.component.html`

**Step 1: Implement Header Logic & Template**
(Converting React logic from `hearder.txt` to Angular)

**Step 2: Commit**
```bash
git add src/app/header/
git commit -m "feat: add responsive header with dark mode toggle"
```

---

### Task 4: Create Footer Component
**Files:**
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\footer\footer.component.ts`
- Create: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\footer\footer.component.html`

**Step 1: Implement Footer Logic & Template**
(Converting React logic from `footer.txt` to Angular)

**Step 2: Commit**
```bash
git add src/app/footer/
git commit -m "feat: add responsive footer with social links"
```

---

### Task 5: Integrate into AppComponent
**Files:**
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\app.component.ts`

**Step 1: Update AppComponent Template**
```typescript
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DashboardComponent, HeaderComponent, FooterComponent],
  template: `
    <app-header></app-header>
    <main class="min-h-screen bg-background text-foreground transition-colors duration-300">
      <app-dashboard></app-dashboard>
    </main>
    <app-footer></app-footer>
  `
})
```

**Step 2: Commit**
```bash
git add src/app/app.component.ts
git commit -m "feat: integrate header and footer into main layout"
```
