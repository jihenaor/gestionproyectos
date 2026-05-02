import { Injectable, inject, signal, computed } from '@angular/core';
import { Router } from '@angular/router';
import { TokenPayload, UserInfo } from '../models/token.model';

/** Misma idea que circulares: token en storage + señal; el interceptor usa getToken(). */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly router = inject(Router);
  private readonly tokenKey = 'gestionproyectos_auth_token';

  private readonly tokenSignal = signal<string | null>(null);
  readonly isAuthenticated = computed(() => !!this.tokenSignal());

  readonly userName = computed(() => {
    const payload = this.getTokenPayload();
    return payload?.nombreCompleto || payload?.name || payload?.sub || 'Usuario';
  });

  readonly userLogin = computed(() => this.getTokenPayload()?.sub ?? '');
  readonly userRoles = computed(() => {
    const payload = this.getTokenPayload();
    if (payload?.roles && payload.roles.length > 0) return payload.roles;
    if (payload?.role) return [payload.role];
    return [];
  });
  readonly userCedula = computed(() => this.getTokenPayload()?.cedula ?? '');
  readonly userInfo = computed<UserInfo | null>(() => {
    const payload = this.getTokenPayload();
    if (!payload) return null;
    return {
      login: payload.sub || '',
      name: payload.name || payload.sub || 'Usuario',
      cedula: payload.cedula ?? '',
      ascodarea: payload.ascodarea ?? '',
      cacodcandi: payload.cacodcandi ?? '',
      nombreCompleto: payload.nombreCompleto ?? '',
      prcodproce: Array.isArray(payload.prcodproce) ? payload.prcodproce : [],
      roles: payload.roles ?? (payload.role ? [payload.role] : []),
      tokenExpiration: payload.exp ? this.formatDate(payload.exp) : ''
    };
  });

  constructor() {
    this.hydrateFromStorage();
  }

  private getTokenPayload(): TokenPayload | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      const parts = token.split('.');
      if (parts.length < 2) return null;
      return JSON.parse(atob(parts[1])) as TokenPayload;
    } catch {
      return null;
    }
  }

  private formatDate(timestamp: number): string {
    const date = new Date(timestamp * 1000);
    return date.toLocaleString('es-CO', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  private hydrateFromStorage(): void {
    let stored = localStorage.getItem(this.tokenKey);
    if (!stored) {
      const legacy = localStorage.getItem('token');
      if (legacy) {
        localStorage.removeItem('token');
        localStorage.setItem(this.tokenKey, legacy);
        stored = legacy;
      }
    }
    this.tokenSignal.set(stored);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.tokenSignal.set(token);
  }

  getToken(): string | null {
    const stored = localStorage.getItem(this.tokenKey);
    return stored && stored.length > 0 ? stored : null;
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
    this.tokenSignal.set(null);
  }

  /** Valida existencia y expiración del JWT (payload `exp`). */
  checkToken(): boolean {
    const token = this.tokenSignal();
    if (!token) {
      this.clearToken();
      return false;
    }
    try {
      const parts = token.split('.');
      if (parts.length < 2) {
        this.clearToken();
        return false;
      }
      const payload = JSON.parse(atob(parts[1])) as { exp?: number };
      if (payload.exp != null) {
        const expirationDate = new Date(payload.exp * 1000);
        if (Date.now() > expirationDate.getTime()) {
          this.clearToken();
          return false;
        }
      }
      return true;
    } catch {
      this.clearToken();
      return false;
    }
  }

  isUserAuthenticated(): boolean {
    return this.checkToken();
  }

  /** Alias usado por el guard legado */
  estaAutenticado(): boolean {
    return this.isUserAuthenticated();
  }

  logout(): void {
    this.clearToken();
    void this.router.navigate(['/login'], { replaceUrl: true });
  }

  getUsername(): string | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1])) as { sub?: string; username?: string };
      return payload.sub ?? payload.username ?? null;
    } catch {
      return null;
    }
  }
}
