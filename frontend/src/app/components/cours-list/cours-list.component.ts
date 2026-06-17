import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { CoursService, Cours } from '../../services/cours.service';
import { UtilisateurService, Utilisateur } from '../../services/user.service';
import { ActualisationService } from '../../services/refresh.service';

@Component({
  selector: 'app-cours-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cours-list.component.html',
  styleUrl: './cours-list.component.css'
})
export class CoursListComponent implements OnInit, OnDestroy {

  cours: Cours[] = [];
  enseignantsMap: { [id: string]: string } = {};
  private sub?: Subscription;

  constructor(
    private coursService: CoursService,
    private utilisateurService: UtilisateurService,
    private actualisationService: ActualisationService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.sub = this.actualisationService.coursChangees.subscribe(() => this.loadCours());
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }

  loadAll() {
    this.utilisateurService.getAllUtilisateurs().subscribe(users => {
      users.forEach(u => {
        if (u.id) {
          this.enseignantsMap[u.id] = `${u.prenom} ${u.nom}`;
        }
      });
    });
    this.loadCours();
  }

  loadCours() {
    this.coursService.getAllCours().subscribe(c => this.cours = c);
  }

  getNomEnseignant(id?: string): string {
    if (!id) return '';
    return this.enseignantsMap[id] || id;
  }
}
