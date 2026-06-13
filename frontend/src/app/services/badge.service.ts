import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Badge {
  id?: string;
  numeroBadge: string;
  membreId: string;
}

export interface Attendance {
  id?: string;
  membreId: string;
  coursId: string;
  scanneLe?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BadgeService {

  private apiUrl = 'http://localhost:8080/api/badges';

  constructor(private http: HttpClient) {}

  assignerBadge(numeroBadge: string, membreId: string): Observable<Badge> {
    return this.http.post<Badge>(
      `${this.apiUrl}/assigner?numeroBadge=${numeroBadge}&membreId=${membreId}`,
      {}
    );
  }

  scanner(numeroBadge: string, coursId: string): Observable<Attendance> {
    return this.http.post<Attendance>(
      `${this.apiUrl}/scanner?numeroBadge=${numeroBadge}&coursId=${coursId}`,
      {}
    );
  }

  retirerBadge(numeroBadge: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${numeroBadge}`);
  }

  getPresencesParCours(coursId: string): Observable<Attendance[]> {
    return this.http.get<Attendance[]>(`${this.apiUrl}/cours/${coursId}/presences`);
  }
}
