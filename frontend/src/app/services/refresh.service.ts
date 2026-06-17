import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ActualisationService {
  private actualisationMembres$ = new Subject<void>();
  private actualisationCours$ = new Subject<void>();

  membresChangees = this.actualisationMembres$.asObservable();
  coursChangees = this.actualisationCours$.asObservable();

  notifierMembres() {
    this.actualisationMembres$.next();
  }

  notifierCours() {
    this.actualisationCours$.next();
  }
}
