import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CoursFormComponent } from '../cours-form/cours-form.component';
import { CoursListComponent } from '../cours-list/cours-list.component';

@Component({
  selector: 'app-cours-page',
  standalone: true,
  imports: [RouterLink, CoursFormComponent, CoursListComponent],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2 class="page-title">Gestion des cours</h2>
        <a routerLink="/" class="back-btn">← Accueil</a>
      </div>
      <div class="cards-grid">
        <app-cours-form></app-cours-form>
        <app-cours-list></app-cours-list>
      </div>
    </div>
  `
})
export class CoursPageComponent {}
