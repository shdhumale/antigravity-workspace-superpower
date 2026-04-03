import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
})
export class FooterComponent {
  currentYear = new Date().getFullYear();

  footerLinks = [
    {
      title: 'Product',
      links: [
        { href: '#', label: 'Dashboard' },
        { href: '#', label: 'Create Ticket' },
        { href: '#', label: 'Search Tickets' },
        { href: '#', label: "What's New" },
      ],
    },
    {
      title: 'Company',
      links: [
        { href: '#', label: 'About Us' },
        { href: '#', label: 'Blog' },
        { href: '#', label: 'Careers' },
        { href: '#', label: 'Press' },
      ],
    },
    {
      title: 'Support',
      links: [
        { href: '#', label: 'Help Topics' },
        { href: '#', label: 'Getting Started' },
        { href: '#', label: 'FAQs' },
        { href: '#', label: 'Report an Issue' },
      ],
    },
    {
      title: 'Legal',
      links: [
        { href: '#', label: 'Terms & Conditions' },
        { href: '#', label: 'Privacy Notice' },
        { href: '#', label: 'Cookie Notice' },
        { href: '#', label: 'Trust Center' },
      ],
    },
  ];

  socialLinks = [
    { name: 'Twitter', href: '#' },
    { name: 'LinkedIn', href: '#' },
    { name: 'GitHub', href: '#' },
    { name: 'Facebook', href: '#' },
  ];
}
