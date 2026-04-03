import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Ticket {
  id?: number;
  name: string;
  description: string;
  status: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private apiUrl = 'http://localhost:8080/api/v1/tickets';

  constructor(private http: HttpClient) {}

  getAllTickets(page: number = 0, size: number = 10): Observable<PaginatedResponse<Ticket>> {
    return this.http.get<PaginatedResponse<Ticket>>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(this.apiUrl, ticket);
  }

  searchTickets(query: string, page: number = 0, size: number = 10): Observable<PaginatedResponse<Ticket>> {
    return this.http.get<PaginatedResponse<Ticket>>(`${this.apiUrl}/search?query=${query}&page=${page}&size=${size}`);
  }

  updateTicket(id: number, ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}/${id}`, ticket);
  }
}
