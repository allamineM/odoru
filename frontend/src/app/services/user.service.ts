import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Adresse {
  ville: string;
  pays: string;
}

export interface Utilisateur {
  id?: string;
  nom: string;
  prenom: string;
  email: string;
  nomUtilisateur: string;
  motDePasse: string;
  niveauExpertise: number;
  adresse: Adresse;
  roles?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private apiUrl = 'http://localhost:8080/api/utilisateurs';

  constructor(private http: HttpClient) {}

  inscription(utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(`${this.apiUrl}/inscription`, utilisateur);
  }

  login(nomUtilisateur: string, motDePasse: string): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(
      `${this.apiUrl}/login?nomUtilisateur=${nomUtilisateur}&motDePasse=${motDePasse}`,
      {}
    );
  }

  getAllUtilisateurs(): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(this.apiUrl);
  }

  getUtilisateurById(id: string): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.apiUrl}/${id}`);
  }

  updateExpertise(id: string, niveau: number): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.apiUrl}/${id}/expertise?niveau=${niveau}`, {});
  }

  addRole(id: string, role: string): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.apiUrl}/${id}/role?role=${role}`, {});
  }

  deleteUtilisateur(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
