import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Cours {
  id?: string;
  titre: string;
  niveauCible: number;
  dateHeure: string;
  enseignantId?: string;
  lieu: string;
  dureeMinutes: number;
  creeLe?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CoursService {

  private apiUrl = 'http://localhost:8080/api/cours';

  constructor(private http: HttpClient) {}

  createCours(cours: Cours, enseignantId: string): Observable<Cours> {
    return this.http.post<Cours>(`${this.apiUrl}?enseignantId=${enseignantId}`, cours);
  }

  getAllCours(): Observable<Cours[]> {
    return this.http.get<Cours[]>(this.apiUrl);
  }

  getCoursByNiveau(niveau: number): Observable<Cours[]> {
    return this.http.get<Cours[]>(`${this.apiUrl}/niveau/${niveau}`);
  }

  getCoursByEnseignant(enseignantId: string): Observable<Cours[]> {
    return this.http.get<Cours[]>(`${this.apiUrl}/enseignant/${enseignantId}`);
  }
}
