import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthService } from './auth.service';

export interface LoginRequest {
  username: string;
  password: string;
  codigoSistema?: string;
}

export interface TokenResponse {
  tokenType: string;
  accessToken: string;
  expiresIn: number;
}

@Injectable({ providedIn: 'root' })
export class LoginService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = environment.authLoginUrl;

  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);

  readonly isLoading = this._loading.asReadonly();
  readonly error = this._error.asReadonly();

  login(credentials: LoginRequest): Observable<TokenResponse> {
    this._loading.set(true);
    this._error.set(null);

    const codigoSistema =
      (credentials.codigoSistema && String(credentials.codigoSistema).trim()) ||
      environment.codigoSistemaSec ||
      '196';

    const payload = {
      username: credentials.username,
      password: credentials.password,
      codigoSistema
    };

    return this.http.post<TokenResponse>(this.apiUrl, payload).pipe(
      tap({
        next: (response) => {
          this.authService.setToken(response.accessToken);
          this._loading.set(false);
        },
        error: (err: { status?: number; error?: { error?: string; message?: string }; message?: string }) => {
          this._loading.set(false);
          this._error.set(this.getErrorMessage(err));
        }
      })
    );
  }

  private getErrorMessage(err: {
    status?: number;
    error?: { error?: string; message?: string };
    message?: string;
  }): string {
    if (err?.status === 0 || err?.message?.includes('Http failure') || err?.message?.includes('Unknown Error')) {
      return 'No se puede conectar al servidor. Verifica que el backend esté en ejecución.';
    }
    if (err?.status === 401) {
      return 'Usuario o contraseña incorrectos.';
    }
    return err?.error?.message ?? err?.error?.error ?? 'Error de autenticación';
  }
}
