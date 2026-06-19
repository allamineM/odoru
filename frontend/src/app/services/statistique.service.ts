import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatistiqueService {

  private apiUrl = 'https://odoru.onrender.com/api/statistiques';

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

  getCoursMembreAvecPresences(membreId: string, debut?: string, fin?: string): Observable<any[]> {
    let url = `${this.apiUrl}/membres/${membreId}/cours?`;
    if (debut) url += `debut=${debut}&`;
    if (fin) url += `fin=${fin}`;
    return this.http.get<any[]>(url);
  }

  getCompetitionsMembreAvecResultats(membreId: string, debut?: string, fin?: string): Observable<any[]> {
    let url = `${this.apiUrl}/membres/${membreId}/competitions?`;
    if (debut) url += `debut=${debut}&`;
    if (fin) url += `fin=${fin}`;
    return this.http.get<any[]>(url);
  }
}
