import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CompetitionService, Competition } from '../../services/competition.service';
import { UserService, User } from '../../services/user.service';

@Component({
  selector: 'app-competition-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './competition-page.component.html',
  styleUrl: './competition-page.component.css'
})
export class CompetitionPageComponent implements OnInit {

  competitions: Competition[] = [];
  enseignants: User[] = [];
  membres: User[] = [];
  enseignantsMap: { [id: string]: string } = {};

  newComp: Competition = {
    titre: '',
    niveauCible: 1,
    dateHeure: '',
    lieu: '',
    dureeMinutes: 120
  };
  enseignantId = '';
  message = '';

  // Pour ajouter un résultat
  competitionIdResultat = '';
  membreIdResultat = '';
  noteResultat = 0;
  messageResultat = '';

  constructor(
    private competitionService: CompetitionService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.loadAll();
    this.userService.getAllUsers().subscribe(users => {
      this.membres = users;
      this.enseignants = users.filter(u => u.roles?.includes('TEACHER'));
      users.forEach(u => {
        if (u.id) this.enseignantsMap[u.id] = `${u.prenom} ${u.nom}`;
      });
    });
  }

  loadAll() {
    this.competitionService.getAllCompetitions().subscribe(data => {
      this.competitions = data;
    });
  }

  getNomEnseignant(id?: string): string {
    if (!id) return '';
    return this.enseignantsMap[id] || id;
  }

  createCompetition() {
    this.competitionService.createCompetition(this.newComp, this.enseignantId).subscribe({
      next: (response) => {
        this.message = `Compétition "${response.titre}" créée avec succès !`;
        this.loadAll();
        this.newComp = { titre: '', niveauCible: 1, dateHeure: '', lieu: '', dureeMinutes: 120 };
        this.enseignantId = '';
      },
      error: (err) => {
        this.message = 'Erreur : ' + (err.error?.message || 'Création impossible');
      }
    });
  }

  ajouterResultat() {
    this.competitionService.addResultat(this.competitionIdResultat, this.membreIdResultat, this.noteResultat).subscribe({
      next: () => {
        this.messageResultat = 'Résultat ajouté avec succès !';
        this.loadAll();
      },
      error: (err) => {
        this.messageResultat = 'Erreur : ' + (err.error?.message || 'Impossible');
      }
    });
  }

  getNoteForMembre(comp: Competition, membreId: string): number | undefined {
    return comp.resultats?.[membreId];
  }

  getResultatsArray(comp: Competition): { membreId: string, note: number }[] {
    if (!comp.resultats) return [];
    return Object.entries(comp.resultats).map(([membreId, note]) => ({ membreId, note: note as number }));
  }
}
