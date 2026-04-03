import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TicketService, Ticket, PaginatedResponse } from '../ticket.service';
import { Subject, debounceTime, distinctUntilChanged, takeUntil } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit, OnDestroy {
  tickets: Ticket[] = [];
  searchQuery: string = '';
  
  // Pagination
  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 10;

  newTicket: Ticket = { name: '', description: '', status: 'NEW' };
  isCreating: boolean = false;

  private searchTerms = new Subject<string>();
  private destroy$ = new Subject<void>();

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.loadTickets();

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(term => {
      this.searchQuery = term;
      this.search();
    });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadTickets(page: number = this.currentPage) {
    this.ticketService.getAllTickets(page, this.pageSize).subscribe({
      next: (data: PaginatedResponse<Ticket>) => {
        this.tickets = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
      },
      error: (err) => console.error('Error fetching tickets', err)
    });
  }

  onSearchChange(term: string) {
    this.searchTerms.next(term);
  }

  search() {
    if(!this.searchQuery.trim()) {
      this.loadTickets(0);
      return;
    }
    this.ticketService.searchTickets(this.searchQuery, 0, this.pageSize).subscribe({
      next: (data: PaginatedResponse<Ticket>) => {
        this.tickets = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
      },
      error: (err) => console.error('Error searching tickets', err)
    });
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.loadTickets(page);
    }
  }

  toggleCreate() {
    this.isCreating = !this.isCreating;
  }

  createTicket() {
    this.ticketService.createTicket(this.newTicket).subscribe({
      next: (ticket) => {
        this.loadTickets(this.currentPage); // Reload current page
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
          this.loadTickets(this.currentPage);
          this.editingTicket = null;
        },
        error: (err) => console.error('Error updating ticket', err)
      });
    }
  }
}
