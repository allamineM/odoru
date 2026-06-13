import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { CoursService, Cours } from '../../services/cours.service';
import { UserService, User } from '../../services/user.service';
import { RefreshService } from '../../services/refresh.service';

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
    private userService: UserService,
    private refresh: RefreshService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.sub = this.refresh.coursChanged.subscribe(() => this.loadCours());
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }

  loadAll() {
    this.userService.getAllUsers().subscribe(users => {
      users.forEach(u => {
        if (u.id) {
          this.enseignantsMap[u.id] = `${u.prenom} ${u.nom}`;
        }
      });
      this.loadCours();
    });
  }

  loadCours() {
    this.coursService.getAllCours().subscribe(data => {
      this.cours = data;
    });
  }

  getNomEnseignant(id?: string): string {
    if (!id) return '';
    return this.enseignantsMap[id] || id;
  }
}
