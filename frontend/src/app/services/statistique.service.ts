import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatistiqueService {

  private apiUrl = 'http://localhost:8080/api/statistiques';

  constructor(private http: HttpClient) {}

  getResumesCours(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/cours/resume`);
  }

  getElevesParCours(coursId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/cours/${coursId}/eleves`);
  }

  getNombreCompetitionsParNiveau(niveau: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/competitions/niveau/${niveau}`);
  }
}
