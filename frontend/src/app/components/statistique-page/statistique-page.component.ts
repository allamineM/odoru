import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { StatistiqueService } from '../../services/statistique.service';
import { UtilisateurService, Utilisateur } from '../../services/user.service';
import { CoursService, Cours } from '../../services/cours.service';

@Component({
  selector: 'app-statistique-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './statistique-page.component.html',
  styleUrl: './statistique-page.component.css'
})
export class StatistiquePageComponent implements OnInit {

  // Stats globales
  resumeCours: any = null;
  niveau = 1;
  nbCompetitions: any = null;

  // Listes déroulantes
  membres: Utilisateur[] = [];
  coursList: Cours[] = [];

  // Liste des présences par cours
  selectedCoursId = '';
  elevesDuCours: any = null;

  // Historiques par membre
  selectedMembreId = '';
  dateDebut = '';
  dateFin = '';
  historiqueCours: any[] = [];
  historiqueComps: any[] = [];

  constructor(
    private statService: StatistiqueService,
    private utilisateurService: UtilisateurService,
    private coursService: CoursService
  ) {}

  ngOnInit() {
    this.statService.getResumesCours().subscribe(r => this.resumeCours = r);

    this.utilisateurService.getAllUtilisateurs().subscribe(u => {
      this.membres = u.filter(m => m.roles?.includes('MEMBER'));
    });

    this.coursService.getAllCours().subscribe(c => this.coursList = c);
  }

  rechercherCompetitions() {
    this.statService.getNombreCompetitionsParNiveau(this.niveau).subscribe(r => {
      this.nbCompetitions = r;
    });
  }

  rechercherElevesCours() {
    if (!this.selectedCoursId) return;
    this.statService.getElevesParCours(this.selectedCoursId).subscribe(r => {
      this.elevesDuCours = r;
    });
  }

  rechercherHistorique() {
    if (!this.selectedMembreId) return;

    const d = this.dateDebut ? this.dateDebut + 'T00:00:00' : undefined;
    const f = this.dateFin ? this.dateFin + 'T23:59:59' : undefined;

    this.statService.getCoursMembreAvecPresences(this.selectedMembreId, d, f).subscribe(r => this.historiqueCours = r);
    this.statService.getCompetitionsMembreAvecResultats(this.selectedMembreId, d, f).subscribe(r => this.historiqueComps = r);
  }
}
