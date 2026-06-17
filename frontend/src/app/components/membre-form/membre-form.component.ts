import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UtilisateurService, Utilisateur } from '../../services/user.service';
import { ActualisationService } from '../../services/refresh.service';

@Component({
  selector: 'app-membre-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './membre-form.component.html',
  styleUrl: './membre-form.component.css'
})
export class MembreFormComponent {

  membre: Utilisateur = {
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
    private utilisateurService: UtilisateurService,
    private actualisationService: ActualisationService
  ) {}

  onSubmit() {
    this.loading = true;
    this.utilisateurService.inscription(this.membre).subscribe({
      next: (response) => {
        this.message = `Membre ${response.prenom} ${response.nom} créé avec succès !`;
        this.resetForm();
        this.actualisationService.notifierMembres();
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
