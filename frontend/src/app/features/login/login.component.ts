import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService, LoginRequest } from '../../core/services/login.service';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login">
      <div class="login__card">
        <div class="login__header">
          <h1 class="login__title">Gestión de proyectos</h1>
          <p class="login__subtitle">Inicie sesión para continuar</p>
        </div>
        <form class="login__form" (ngSubmit)="onSubmit()">
          <div class="login__group">
            <label class="login__label" for="usuario">Usuario</label>
            <input
              id="usuario"
              type="text"
              class="login__input"
              [class.login__input--error]="usuarioError()"
              [value]="usuario()"
              (input)="onUsuarioChange($any($event.target).value)"
              placeholder="Usuario"
              [disabled]="loginService.isLoading()"
              autocomplete="username"
            />
            @if (usuarioError()) {
              <div class="login__err">{{ usuarioError() }}</div>
            }
          </div>
          <div class="login__group">
            <label class="login__label" for="clave">Contraseña</label>
            <input
              id="clave"
              type="password"
              class="login__input"
              [class.login__input--error]="claveError()"
              [value]="clave()"
              (input)="onClaveChange($any($event.target).value)"
              placeholder="Contraseña"
              [disabled]="loginService.isLoading()"
              autocomplete="current-password"
            />
            @if (claveError()) {
              <div class="login__err">{{ claveError() }}</div>
            }
          </div>
          <button type="submit" class="login__btn" [disabled]="loginService.isLoading()">
            @if (loginService.isLoading()) {
              Iniciando sesión…
            } @else {
              Iniciar sesión
            }
          </button>
        </form>
        <p class="login__hint">Código sistema SEC: {{ codigoSistema() }} (configurable)</p>
      </div>
    </div>
  `,
  styles: [`
    .login {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: var(--spacing-xl);
      background: var(--color-background-secondary);
    }
    .login__card {
      width: 100%;
      max-width: 420px;
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-xl);
      box-shadow: 0 4px 24px rgba(0,0,0,.06);
    }
    .login__header { margin-bottom: var(--spacing-lg); text-align: center; }
    .login__title { margin: 0 0 var(--spacing-xs); font-size: 1.5rem; font-weight: 700; }
    .login__subtitle { margin: 0; color: var(--color-text-secondary); font-size: 0.9375rem; }
    .login__form { display: flex; flex-direction: column; gap: var(--spacing-md); }
    .login__group { display: flex; flex-direction: column; gap: var(--spacing-xs); }
    .login__label { font-size: 0.875rem; font-weight: 500; color: var(--color-text-primary); }
    .login__input {
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      font-size: 1rem;
      background: var(--color-background);
    }
    .login__input--error { border-color: var(--color-error); }
    .login__input:focus { outline: 2px solid var(--color-primary); outline-offset: 1px; }
    .login__err { font-size: 0.8125rem; color: var(--color-error); }
    .login__btn {
      margin-top: var(--spacing-sm);
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-primary);
      color: #fff;
      border: none;
      border-radius: var(--radius-md);
      font-weight: 600;
      cursor: pointer;
    }
    .login__btn:disabled { opacity: 0.7; cursor: not-allowed; }
    .login__btn:hover:not(:disabled) { background: var(--color-primary-hover); }
    .login__hint { margin: var(--spacing-lg) 0 0; font-size: 0.75rem; color: var(--color-text-secondary); text-align: center; }
  `]
})
export class LoginComponent implements OnInit {
  /** Público: la plantilla usa `loginService.isLoading()`. */
  readonly loginService = inject(LoginService);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  readonly usuario = signal('');
  readonly clave = signal('');
  readonly codigoSistema = signal(environment.codigoSistemaSec ?? '196');
  readonly usuarioError = signal('');
  readonly claveError = signal('');

  ngOnInit(): void {
    if (this.authService.isUserAuthenticated()) {
      const target = this.safeReturnUrl(this.route.snapshot.queryParamMap.get('returnUrl'));
      void this.router.navigateByUrl(target, { replaceUrl: true });
    }
  }

  onUsuarioChange(value: string): void {
    this.usuario.set(value);
    if (this.usuarioError()) this.usuarioError.set('');
  }

  onClaveChange(value: string): void {
    this.clave.set(value);
    if (this.claveError()) this.claveError.set('');
  }

  onSubmit(): void {
    if (!this.validateForm()) return;

    const request: LoginRequest = {
      username: this.usuario(),
      password: this.clave(),
      codigoSistema: this.codigoSistema()
    };

    this.loginService.login(request).subscribe({
      next: () => {
        const target = this.safeReturnUrl(this.route.snapshot.queryParamMap.get('returnUrl'));
        void this.router.navigateByUrl(target, { replaceUrl: true });
      },
      error: () => {
        this.claveError.set(this.loginService.error() ?? 'Error de autenticación');
      }
    });
  }

  private validateForm(): boolean {
    let ok = true;
    if (!this.usuario().trim()) {
      this.usuarioError.set('El usuario es requerido');
      ok = false;
    } else {
      this.usuarioError.set('');
    }
    if (!this.clave().trim()) {
      this.claveError.set('La contraseña es requerida');
      ok = false;
    } else {
      this.claveError.set('');
    }
    return ok;
  }

  private safeReturnUrl(raw: string | null | undefined): string {
    const def = '/dashboard';
    if (!raw) return def;
    if (!raw.startsWith('/')) return def;
    if (raw === '/login' || raw.startsWith('/login?')) return def;
    const path = raw.split('?')[0].split('#')[0];
    // Tras login siempre inicio: no reabrir generador XML (evita carga CIEF y no es la ruta por defecto).
    if (path === '/xml-generator' || path.startsWith('/xml-generator/')) {
      return def;
    }
    return raw;
  }
}
