import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CoursService } from '../../services/cours.service';
import { CompetitionService } from '../../services/competition.service';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {

  stats = {
    membres: 0,
    enseignants: 0,
    cours: 0,
    competitions: 0
  };

  cards = [
    { route: '/membres', icon: '👥', title: 'Membres', desc: 'Inscrire et gérer les membres du club', color: '#6366F1' },
    { route: '/cours', icon: '📚', title: 'Cours', desc: 'Planifier les créneaux de cours', color: '#3B82F6' },
    { route: '/competitions', icon: '🏆', title: 'Compétitions', desc: 'Organiser les compétitions et résultats', color: '#F59E0B' },
    { route: '/badges', icon: '🎫', title: 'Badges', desc: 'Gérer les badges et la présence', color: '#8B5CF6' },
    { route: '/statistiques', icon: '📊', title: 'Statistiques', desc: 'Consulter les statistiques (Président)', color: '#EC4899' }
  ];

  constructor(
    private userService: UserService,
    private coursService: CoursService,
    private competitionService: CompetitionService
  ) {}

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => {
      this.stats.membres = users.length;
      this.stats.enseignants = users.filter(u => u.roles?.includes('TEACHER')).length;
    });
    this.coursService.getAllCours().subscribe(c => this.stats.cours = c.length);
    this.competitionService.getAllCompetitions().subscribe(c => this.stats.competitions = c.length);
  }
}
