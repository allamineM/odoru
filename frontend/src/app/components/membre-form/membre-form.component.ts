import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService, User } from '../../services/user.service';
import { RefreshService } from '../../services/refresh.service';

@Component({
  selector: 'app-membre-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './membre-form.component.html',
  styleUrl: './membre-form.component.css'
})
export class MembreFormComponent {

  membre: User = {
    nom: '',
    prenom: '',
    email: '',
    nomUtilisateur: '',
    motDePasse: '',
    niveauExpertise: 1,
    adresse: { ville: '', pays: '' }
  };

  message = '';
  loading = false;

  constructor(
    private userService: UserService,
    private refresh: RefreshService
  ) {}

  onSubmit() {
    this.loading = true;
    this.userService.inscription(this.membre).subscribe({
      next: (response) => {
        this.message = `Membre ${response.prenom} ${response.nom} créé avec succès !`;
        this.resetForm();
        this.refresh.notifyMembresChanged();
        this.loading = false;
      },
      error: (err) => {
        this.message = 'Erreur : ' + (err.error?.message || 'Inscription impossible');
        this.loading = false;
      }
    });
  }

  resetForm() {
    this.membre = {
      nom: '',
      prenom: '',
      email: '',
      nomUtilisateur: '',
      motDePasse: '',
      niveauExpertise: 1,
      adresse: { ville: '', pays: '' }
    };
  }
}
