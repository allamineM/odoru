import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserService, User } from '../../services/user.service';
import { RefreshService } from '../../services/refresh.service';

@Component({
  selector: 'app-membres-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './membres-list.component.html',
  styleUrl: './membres-list.component.css'
})
export class MembresListComponent implements OnInit, OnDestroy {

  membres: User[] = [];
  search = '';
  selectedMembre: User | null = null;
  editLevel = 1;
  selectedRole: 'TEACHER' | 'SECRETARY' | 'PRESIDENT' = 'TEACHER';
  message = '';
  private sub?: Subscription;

  constructor(
    private userService: UserService,
    private refresh: RefreshService
  ) {}

  ngOnInit(): void {
    this.loadMembres();
    this.sub = this.refresh.membresChanged.subscribe(() => this.loadMembres());
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }

  loadMembres() {
    this.userService.getAllUsers().subscribe(data => {
      this.membres = data;
    });
  }

  get filteredMembres(): User[] {
    if (!this.search.trim()) return this.membres;
    const s = this.search.toLowerCase();
    return this.membres.filter(m =>
      m.nom?.toLowerCase().includes(s) ||
      m.prenom?.toLowerCase().includes(s) ||
      m.email?.toLowerCase().includes(s) ||
      m.adresse?.ville?.toLowerCase().includes(s)
    );
  }

  openManage(membre: User) {
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
    this.userService.updateExpertise(this.selectedMembre.id, this.editLevel).subscribe({
      next: () => {
        this.message = `Niveau mis à jour : ${this.editLevel}`;
        this.loadMembres();
      },
      error: (err) => this.message = 'Erreur : ' + (err.error?.message || 'Échec')
    });
  }

  addRole() {
    if (!this.selectedMembre?.id) return;
    this.userService.addRole(this.selectedMembre.id, this.selectedRole).subscribe({
      next: (updated) => {
        this.message = `Rôle ${this.selectedRole} ajouté`;
        this.selectedMembre = updated;
        this.loadMembres();
      },
      error: (err) => this.message = 'Erreur : ' + (err.error?.message || 'Échec')
    });
  }

  deleteMembre(membre: User) {
    if (!membre.id) return;
    if (!confirm(`Supprimer ${membre.prenom} ${membre.nom} ?`)) return;
    this.userService.deleteUser(membre.id).subscribe(() => this.loadMembres());
  }

  roleLabel(role: string): string {
    const labels: { [k: string]: string } = {
      MEMBER: 'Membre',
      TEACHER: 'Enseignant',
      SECRETARY: 'Secrétaire',
      PRESIDENT: 'Président'
    };
    return labels[role] || role;
  }
}
