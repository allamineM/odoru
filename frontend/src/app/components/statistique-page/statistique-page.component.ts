import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { StatistiqueService } from '../../services/statistique.service';

@Component({
  selector: 'app-statistique-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './statistique-page.component.html',
  styleUrl: './statistique-page.component.css'
})
export class StatistiquePageComponent implements OnInit {

  resumeCours: any = null;
  niveau = 1;
  nbCompetitions: any = null;

  constructor(private statService: StatistiqueService) {}

  ngOnInit() {
    this.statService.getResumesCours().subscribe(r => this.resumeCours = r);
  }

  rechercherCompetitions() {
    this.statService.getNombreCompetitionsParNiveau(this.niveau).subscribe(r => {
      this.nbCompetitions = r;
    });
  }
}
