import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UtilisateurService, Utilisateur } from '../../services/user.service';
import { ActualisationService } from '../../services/refresh.service';

@Component({
  selector: 'app-membres-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './membres-list.component.html',
  styleUrl: './membres-list.component.css'
})
export class MembresListComponent implements OnInit, OnDestroy {

  membres: Utilisateur[] = [];
  search = '';
  selectedMembre: Utilisateur | null = null;
  editLevel = 1;
  selectedRole: 'TEACHER' | 'SECRETARY' | 'PRESIDENT' = 'TEACHER';
  message = '';
  private sub?: Subscription;

  constructor(
    private utilisateurService: UtilisateurService,
    private actualisationService: ActualisationService
  ) {}

  ngOnInit(): void {
    this.loadMembres();
    this.sub = this.actualisationService.membresChangees.subscribe(() => this.loadMembres());
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }

  loadMembres() {
    this.utilisateurService.getAllUtilisateurs().subscribe(data => {
      this.membres = data;
    });
  }

  // Fonction manquante : Filtre la liste selon la recherche
  get filteredMembres(): Utilisateur[] {
    if (!this.search.trim()) return this.membres;
    const term = this.search.toLowerCase();
    return this.membres.filter(m =>
      m.nom.toLowerCase().includes(term) ||
      m.prenom.toLowerCase().includes(term) ||
      m.email.toLowerCase().includes(term) ||
      (m.adresse?.ville || '').toLowerCase().includes(term)
    );
  }

  roleLabel(role: string): string {
    switch(role) {
      case 'TEACHER': return 'Enseignant';
      case 'SECRETARY': return 'Secrétaire';
      case 'PRESIDENT': return 'Président';
      case 'MEMBER': return 'Membre';
      default: return role;
    }
  }

  openManage(membre: Utilisateur) {
    this.selectedMembre = membre;
    this.editLevel = membre.niveauExpertise;
    this.message = '';
  }

  closeManage() {
    this.selectedMembre = null;
    this.message = '';
  }

  updateLevel() {
    if (!this.selectedMembre?.id) return;
    this.utilisateurService.updateExpertise(this.selectedMembre.id, this.editLevel).subscribe({
      next: () => {
        this.message = `Niveau mis à jour : ${this.editLevel}`;
        this.loadMembres();
      },
      error: (err) => this.message = 'Erreur : ' + (err.error?.message || 'Échec')
    });
  }

  addRole() {
    if (!this.selectedMembre?.id) return;
    this.utilisateurService.addRole(this.selectedMembre.id, this.selectedRole).subscribe({
      next: (updated) => {
        this.message = `Rôle ${this.roleLabel(this.selectedRole)} ajouté`;
        this.selectedMembre = updated;
        this.loadMembres();
      },
      error: (err) => this.message = 'Erreur : ' + (err.error?.message || 'Échec')
    });
  }

  deleteMembre(membre: Utilisateur) {
    if (!membre.id) return;
    if (confirm(`Supprimer ${membre.prenom} ${membre.nom} ?`)) {
      this.utilisateurService.deleteUtilisateur(membre.id).subscribe({
        next: () => {
          this.closeManage();
          this.loadMembres();
        },
        error: (err) => alert('Erreur : ' + (err.error?.message || 'Échec'))
      });
    }
  }
}
