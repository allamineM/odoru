import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Address {
  ville: string;
  pays: string;
}

export interface User {
  id?: string;
  nom: string;
  prenom: string;
  email: string;
  nomUtilisateur: string;
  motDePasse: string;
  niveauExpertise: number;
  adresse: Address;
  roles?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  inscription(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/inscription`, user);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  updateExpertise(id: string, niveau: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}/expertise?niveau=${niveau}`, {});
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  addRole(id: string, role: string): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}/role?role=${role}`, {});
  }
}
