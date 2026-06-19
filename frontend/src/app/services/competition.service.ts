import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Competition {
  id?: string;
  titre: string;
  niveauCible: number;
  dateHeure: string;
  enseignantId?: string;
  lieu: string;
  dureeMinutes: number;
  resultats?: { [membreId: string]: number };
}

@Injectable({
  providedIn: 'root'
})
export class CompetitionService {

  private apiUrl = 'https://odoru.onrender.com/api/competitions';

  constructor(private http: HttpClient) {}

  createCompetition(competition: Competition, enseignantId: string): Observable<Competition> {
    return this.http.post<Competition>(`${this.apiUrl}?enseignantId=${enseignantId}`, competition);
  }

  addResultat(competitionId: string, membreId: string, note: number): Observable<Competition> {
    return this.http.put<Competition>(
      `${this.apiUrl}/${competitionId}/resultat?membreId=${membreId}&note=${note}`,
      {}
    );
  }

  getAllCompetitions(): Observable<Competition[]> {
    return this.http.get<Competition[]>(this.apiUrl);
  }

  getByNiveau(niveau: number): Observable<Competition[]> {
    return this.http.get<Competition[]>(`${this.apiUrl}/niveau/${niveau}`);
  }

  getByMembre(membreId: string): Observable<Competition[]> {
    return this.http.get<Competition[]>(`${this.apiUrl}/membre/${membreId}`);
  }

  getByEnseignant(enseignantId: string): Observable<Competition[]> {
    return this.http.get<Competition[]>(`${this.apiUrl}/enseignant/${enseignantId}`);
  }
}
