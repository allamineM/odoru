import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { BadgeService } from '../../services/badge.service';
import { UserService, User } from '../../services/user.service';
import { CoursService, Cours } from '../../services/cours.service';

@Component({
  selector: 'app-badge-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './badge-page.component.html',
  styleUrl: './badge-page.component.css'
})
export class BadgePageComponent implements OnInit {

  membres: User[] = [];
  cours: Cours[] = [];

  // Assigner badge
  numeroBadge = '';
  membreId = '';
  messageAssign = '';

  // Scanner
  numeroBadgeScan = '';
  coursIdScan = '';
  messageScan = '';

  constructor(
    private badgeService: BadgeService,
    private userService: UserService,
    private coursService: CoursService
  ) {}

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => this.membres = users);
    this.coursService.getAllCours().subscribe(c => this.cours = c);
  }

  assigner() {
    this.badgeService.assignerBadge(this.numeroBadge, this.membreId).subscribe({
      next: () => {
        this.messageAssign = `Badge ${this.numeroBadge} assigné avec succès !`;
        this.numeroBadge = '';
        this.membreId = '';
      },
      error: (err) => {
        this.messageAssign = 'Erreur : ' + (err.error?.message || 'Impossible');
      }
    });
  }

  scanner() {
    this.badgeService.scanner(this.numeroBadgeScan, this.coursIdScan).subscribe({
      next: () => {
        this.messageScan = 'Présence enregistrée !';
        this.numeroBadgeScan = '';
      },
      error: (err) => {
        this.messageScan = 'Erreur : ' + (err.error?.message || 'Impossible');
      }
    });
  }
}
