# Design: Dark Mode & Component Integration (Angular + Tailwind)

## Overview
Implement **Dark Mode** and integrate the **Header** and **Footer** components into the existing Angular application. The components will be converted from the provided React/TSX prompts into native Angular components, styled with Tailwind CSS for high performance and visual excellence.

## Architecture & Theme Management

### 1. Theme Service (`ThemeService`)
- A singleton service: `ThemeService`.
- **Purpose**: Manage the application's theme state (light vs dark).
- **Core Logic**:
    - Manage a boolean state `isDarkMode`.
    - Persist the theme in `localStorage.getItem('theme')`.
    - Toggle the `dark` class on the `<html>` or `<body>` element.
    - Default to `light` or user's system preference.

### 2. Tailwind CSS Configuration
- Install `tailwindcss`, `postcss`, and `autoprefixer`.
- Enable `darkMode: 'class'` in `tailwind.config.js`.
- Add local `src/styles.css` containing Tailwind's `@tailwind` directives.

## Component Design

### 1. Header Component (`app-header`)
- **Template**: Based on `hearder.txt` React code.
- **Logic**:
    - Toggle a boolean `open` for the mobile navigation menu.
    - Use `@HostListener('window:scroll')` to toggled a `scrolled` boolean when the y-offset > 10 pixels.
    - Call `ThemeService.toggle()` from a theme-switcher button containing Sun/Moon icons.
- **Responsive Behavior**: Sticky top, blurring background on scroll, and a mobile-friendly slide-down menu.

### 2. Footer Component (`app-footer`)
- **Template**: Based on `footer.txt` React code.
- **Logic**:
    - Responsive grid containing link groups: `Company`, `Community`, `Support`, `Legal`.
    - Integrated social icons and app store buttons (Apple & Google Play) using SVG paths or icons.
- **Visuals**: Subtle borders and clean typography to match the premium aesthetics.

## Data Flow & State Management

- **Theme Lifecycle**:
    1. `ThemeService` initializes, checks `localStorage`, and applies the correct theme class to the root element.
    2. Any component (like `app-header`) can trigger `toggle()` through user interaction.
    3. `ThemeService` updates the DOM and storage accordingly.

## Error Handling & Testing

- **Error Handling**: Graceful fallback to light mode if `localStorage` fails. Fallback icons for navigation links.
- **Testing**:
    - **Unit Tests**: Ensure `ThemeService` correctly toggles state and persists to storage.
    - **Visual Testing**: Verify dark mode contrast and accessibility on all primary screens.

## Acceptance Criteria
- [ ] Dark Mode toggles correctly and persists on refresh.
- [ ] `app-header` changes background style on scroll.
- [ ] Mobile menu in `app-header` works flawlessly.
- [ ] `app-footer` is fully responsive and contains all required links.
- [ ] All components look premium and use consistent Tailwind styling.
