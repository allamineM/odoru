import { Routes } from '@angular/router';
import { HomePageComponent } from './components/home-page/home-page.component';
import { MembrePageComponent } from './components/membre-page/membre-page.component';
import { CoursPageComponent } from './components/cours-page/cours-page.component';
import { CompetitionPageComponent } from './components/competition-page/competition-page.component';
import { BadgePageComponent } from './components/badge-page/badge-page.component';
import { StatistiquePageComponent } from './components/statistique-page/statistique-page.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'membres', component: MembrePageComponent },
  { path: 'cours', component: CoursPageComponent },
  { path: 'competitions', component: CompetitionPageComponent },
  { path: 'badges', component: BadgePageComponent },
  { path: 'statistiques', component: StatistiquePageComponent }
];
