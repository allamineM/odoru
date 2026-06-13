import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RefreshService {
  private refreshMembres$ = new Subject<void>();
  private refreshCours$ = new Subject<void>();

  membresChanged = this.refreshMembres$.asObservable();
  coursChanged = this.refreshCours$.asObservable();

  notifyMembresChanged() {
    this.refreshMembres$.next();
  }

  notifyCoursChanged() {
    this.refreshCours$.next();
  }
}
