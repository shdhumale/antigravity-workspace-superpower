# Edit Ticket Feature Implementation Plan

> **For Antigravity:** REQUIRED WORKFLOW: Use `.agent/workflows/execute-plan.md` to execute this plan in single-flow mode.

**Goal:** Add an edit button and edit screen for each ticket record so users can update existing tickets.

**Architecture:** We will implement a `PUT /api/v1/tickets/{id}` backend endpoint to update an existing TicketEntity. In the frontend, we'll update the TicketService to call this endpoint, and add an "Edit" button in the dashboard table. Clicking this button will display an inline or external form to edit the ticket details.

**Tech Stack:** Java, Spring Boot, Angular, TypeScript.

---

### Task 1: Backend Edit API Endpoint

**Files:**
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\backend\src\test\java\com\ticketmgmt\controller\TicketControllerTest.java`
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\backend\src\main\java\com\ticketmgmt\controller\TicketController.java`

**Step 1: Write the failing test**

Add to `TicketControllerTest.java`:
```java
    @Test
    public void testUpdateTicket() throws Exception {
        TicketEntity updatedTicket = new TicketEntity();
        updatedTicket.setId(1L);
        updatedTicket.setName("Updated");
        updatedTicket.setStatus("DONE");

        Mockito.when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(updatedTicket));
        Mockito.when(ticketRepository.save(Mockito.any(TicketEntity.class))).thenReturn(updatedTicket);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/tickets/1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated\", \"description\": \"Desc\", \"status\": \"DONE\"}"))
                .andExpect(status().isOk());
    }
```

**Step 2: Run test to verify it fails**

Run: `cd c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\backend && mvnw -Dtest=TicketControllerTest#testUpdateTicket test`
Expected: FAIL (404/405 Method Not Allowed)

**Step 3: Write minimal implementation**

Add to `TicketController.java`:
```java
    @PutMapping("/{id}")
    public ResponseEntity<TicketEntity> updateTicket(@PathVariable Long id, @RequestBody TicketEntity ticketDetails) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setName(ticketDetails.getName());
            ticket.setDescription(ticketDetails.getDescription());
            ticket.setStatus(ticketDetails.getStatus());
            TicketEntity updated = ticketRepository.save(ticket);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
```

**Step 4: Run test to verify it passes**

Run: `cd c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\backend && mvnw -Dtest=TicketControllerTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add backend/src/test/java/com/ticketmgmt/controller/TicketControllerTest.java backend/src/main/java/com/ticketmgmt/controller/TicketController.java
git commit -m "feat: backend api endpoint for editing tickets"
```

### Task 2: Frontend Ticket Service Update

**Files:**
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\ticket.service.ts`

**Step 1: Write minimal implementation**

Add the `updateTicket` method to `TicketService`:
```typescript
  updateTicket(id: number, ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}/${id}`, ticket);
  }
```

**Step 2: Commit**

```bash
git add frontend/src/app/ticket.service.ts
git commit -m "feat: ticket service update endpoint integration"
```

### Task 3: Frontend Dashboard UI editing form

**Files:**
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\dashboard\dashboard.component.ts`
- Modify: `c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend\src\app\dashboard\dashboard.component.html`

**Step 1: Implement Editing State and Logic**

Add inside `DashboardComponent`:
```typescript
  editingTicket: Ticket | null = null;

  editTicket(ticket: Ticket) {
    this.editingTicket = { ...ticket }; 
    this.isCreating = false; // Close create form if open
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
```

**Step 2: Build HTML Structure**

Add edit UI logic to `dashboard.component.html`. Create a new block for editing, just like the create form block, then add an "Edit" button to table row.
Modify table:
```html
            <td><span style="padding: 4px 8px; border-radius: 12px; font-size: 12px; background-color: #eee;">{{
                    t.status }}</span></td>
            <td>
                <button (click)="editTicket(t)" style="padding: 4px 8px; background-color: #ffc107; color: #000; border: none; border-radius: 4px; cursor: pointer;">Edit</button>
            </td>
```
(Don't forget to add an empty `<th>Action</th>` Header to the table).

Add to html below `isCreating` block:
```html
<div *ngIf="editingTicket"
    style="margin-bottom: 20px; padding: 15px; border: 1px solid #ddd; border-radius: 4px; background-color: #fff3cd;">
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h3>Edit Ticket</h3>
        <button (click)="cancelEdit()" style="padding: 4px 8px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Cancel</button>
    </div>
    <div style="margin-bottom: 10px;">
        <input type="text" [(ngModel)]="editingTicket.name" placeholder="Ticket Name"
            style="width: 100%; padding: 8px; box-sizing: border-box;">
    </div>
    <div style="margin-bottom: 10px;">
        <textarea [(ngModel)]="editingTicket.description" placeholder="Description" rows="3"
            style="width: 100%; padding: 8px; box-sizing: border-box;"></textarea>
    </div>
    <div style="margin-bottom: 10px;">
        <select [(ngModel)]="editingTicket.status" style="padding: 8px; width: 100%; box-sizing: border-box;">
            <option value="NEW">New</option>
            <option value="ASSIGNED">Assigned</option>
            <option value="DONE">Done</option>
            <option value="ESCALATE">Escalate</option>
        </select>
    </div>
    <button (click)="saveEdit()"
        style="padding: 8px 16px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">Update
        Ticket</button>
</div>
```

**Step 3: Test Functionality via compile and local run**

Terminal run: `cd c:\All_Antigravity_Project_Workspace\antigravity-workspace-superpower\frontend && npm run build`
Make sure no TS compile errors.

**Step 4: Commit**

```bash
git add frontend/src/app/dashboard/dashboard.component.ts frontend/src/app/dashboard/dashboard.component.html
git commit -m "feat: add edit ticket UI and logic in dashboard"
```
