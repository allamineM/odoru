import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CoursService, Cours } from '../../services/cours.service';
import { UserService, User } from '../../services/user.service';
import { RefreshService } from '../../services/refresh.service';

@Component({
  selector: 'app-cours-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cours-form.component.html',
  styleUrl: './cours-form.component.css'
})
export class CoursFormComponent implements OnInit {

  cours: Cours = {
    titre: '',
    niveauCible: 1,
    dateHeure: '',
    lieu: '',
    dureeMinutes: 60
  };

  enseignantId = '';
  enseignants: User[] = [];
  message = '';

  constructor(
    private coursService: CoursService,
    private userService: UserService,
    private refresh: RefreshService
  ) {}

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => {
      this.enseignants = users.filter(u => u.roles?.includes('TEACHER'));
    });
  }

  onSubmit() {
    this.coursService.createCours(this.cours, this.enseignantId).subscribe({
      next: (response) => {
        this.message = `Cours "${response.titre}" créé avec succès !`;
        this.resetForm();
        this.refresh.notifyCoursChanged();
      },
      error: (err) => {
        this.message = 'Erreur : ' + (err.error?.message || 'Création impossible');
      }
    });
  }

  resetForm() {
    this.cours = {
      titre: '',
      niveauCible: 1,
      dateHeure: '',
      lieu: '',
      dureeMinutes: 60
    };
    this.enseignantId = '';
  }
}
