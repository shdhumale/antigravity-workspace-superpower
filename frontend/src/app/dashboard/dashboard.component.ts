import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TicketService, Ticket } from '../ticket.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  tickets: Ticket[] = [];
  searchQuery: string = '';
  
  newTicket: Ticket = { name: '', description: '', status: 'NEW' };
  isCreating: boolean = false;

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.loadTickets();
  }

  loadTickets() {
    this.ticketService.getAllTickets().subscribe({
      next: (data) => this.tickets = data,
      error: (err) => console.error('Error fetching tickets', err)
    });
  }

  search() {
    if(!this.searchQuery.trim()) {
      this.loadTickets();
      return;
    }
    this.ticketService.searchTickets(this.searchQuery).subscribe({
      next: (data) => this.tickets = data,
      error: (err) => console.error('Error searching tickets', err)
    });
  }

  toggleCreate() {
    this.isCreating = !this.isCreating;
  }

  createTicket() {
    this.ticketService.createTicket(this.newTicket).subscribe({
      next: (ticket) => {
        this.tickets.push(ticket);
        this.isCreating = false;
        this.newTicket = { name: '', description: '', status: 'NEW' };
      },
      error: (err) => console.error('Error creating ticket', err)
    });
  }

  editingTicket: Ticket | null = null;

  editTicket(ticket: Ticket) {
    this.editingTicket = { ...ticket }; 
    this.isCreating = false;
  }

  cancelEdit() {
    this.editingTicket = null;
  }

  saveEdit() {
    if(this.editingTicket && this.editingTicket.id) {
      this.ticketService.updateTicket(this.editingTicket.id, this.editingTicket).subscribe({
        next: (updated) => {
          const index = this.tickets.findIndex(t => t.id === updated.id);
          if (index !== -1) {
            this.tickets[index] = updated;
          }
          this.editingTicket = null;
        },
        error: (err) => console.error('Error updating ticket', err)
      });
    }
  }
}
