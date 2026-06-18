import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MembreFormComponent } from '../membre-form/membre-form.component';
import { MembresListComponent } from '../membres-list/membres-list.component';

@Component({
  selector: 'app-membre-page',
  standalone: true,
  imports: [RouterLink, MembreFormComponent, MembresListComponent],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2 class="page-title">Gestion des membres</h2>
        <a routerLink="/" class="back-btn">← Accueil</a>
      </div>
      <div class="cards-grid">
        <app-membre-form></app-membre-form>
        <app-membres-list></app-membres-list>
      </div>
    </div>
  `
})
export class MembrePageComponent {}
