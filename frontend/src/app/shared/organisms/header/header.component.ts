import { Component, signal, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { ModalComponent } from '../../molecules/modal/modal.component';
import { UserInfo } from '../../../core/models/token.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule, ModalComponent],
  template: `
    <header class="header">
      <div class="header__brand">
        <a routerLink="/dashboard" class="header__logo">
          <svg viewBox="0 0 32 32" width="32" height="32" fill="none">
            <rect width="32" height="32" rx="8" fill="var(--color-primary)"/>
            <path d="M8 16L14 22L24 10" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="header__title">Gestión de Proyectos</span>
        </a>
      </div>
      <div class="header__user">
        <button class="header__notification-btn" aria-label="Notificaciones">
          <svg viewBox="0 0 24 24" width="20" height="20" fill="none">
            <path d="M18 8A6 6 0 006 8c0 7-3 9-3 9h18s-3-2-3-9M13.73 21a2 2 0 01-3.46 0" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="header__notification-badge">3</span>
        </button>
        <div class="header__user-info">
          <span class="header__user-name">{{ auth.userName() }}</span>
          <span class="header__user-role">{{ auth.userRoles().join(', ') }}</span>
        </div>
        <button type="button" class="header__logout" (click)="onLogout()" title="Cerrar sesión">
          Salir
        </button>
        <button class="header__avatar" aria-label="Perfil de usuario" (click)="openUserModal()">
          {{ auth.userName().charAt(0).toUpperCase() }}
        </button>
      </div>
    </header>

    <app-modal
      [title]="'Información del Usuario'"
      [isOpen]="showUserModal()"
      [size]="'sm'"
      [showFooter]="false"
      (close)="closeUserModal()">
      @if (auth.userInfo(); as user) {
        <div class="user-info">
          <div class="user-info__avatar">
            {{ user.nombreCompleto.charAt(0).toUpperCase() }}
          </div>
          <div class="user-info__details">
            <div class="user-info__row">
              <span class="user-info__label">Login:</span>
              <span class="user-info__value">{{ user.login }}</span>
            </div>
            <div class="user-info__row">
              <span class="user-info__label">Nombre:</span>
              <span class="user-info__value">{{ user.nombreCompleto }}</span>
            </div>
            @if (user.cedula) {
              <div class="user-info__row">
                <span class="user-info__label">Cédula:</span>
                <span class="user-info__value">{{ user.cedula }}</span>
              </div>
            }
            @if (user.roles.length > 0) {
              <div class="user-info__row">
                <span class="user-info__label">Roles:</span>
                <span class="user-info__value">{{ user.roles.join(', ') }}</span>
              </div>
            }
            @if (user.tokenExpiration) {
              <div class="user-info__row">
                <span class="user-info__label">Expira:</span>
                <span class="user-info__value">{{ user.tokenExpiration }}</span>
              </div>
            }
          </div>
        </div>
      }
    </app-modal>
  `,
  styles: [`
    .header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: var(--spacing-md) var(--spacing-xl);
      background: var(--color-background);
      border-bottom: 1px solid var(--color-border);
      height: 4rem;
    }
    .header__brand {
      display: flex;
      align-items: center;
    }
    .header__logo {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);
      text-decoration: none;
    }
    .header__title {
      font-size: 1.125rem;
      font-weight: 600;
      color: var(--color-text-primary);
    }
    .header__user {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
    }
    .header__notification-btn {
      position: relative;
      background: transparent;
      border: none;
      color: var(--color-text-secondary);
      cursor: pointer;
      padding: var(--spacing-xs);
      border-radius: var(--radius-md);
    }
    .header__notification-btn:hover {
      background: var(--color-background-secondary);
    }
    .header__notification-badge {
      position: absolute;
      top: 0;
      right: 0;
      background: var(--color-error);
      color: white;
      font-size: 0.625rem;
      font-weight: 600;
      width: 1rem;
      height: 1rem;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .header__user-info {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
    }
    .header__user-name {
      font-size: 0.875rem;
      font-weight: 500;
      color: var(--color-text-primary);
    }
    .header__user-role {
      font-size: 0.75rem;
      color: var(--color-text-secondary);
    }
    .header__logout {
      padding: var(--spacing-xs) var(--spacing-sm);
      font-size: 0.875rem;
      font-weight: 500;
      color: var(--color-text-secondary);
      background: transparent;
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      cursor: pointer;
    }
    .header__logout:hover {
      background: var(--color-background-secondary);
      color: var(--color-text-primary);
    }
    .header__avatar {
      width: 2.5rem;
      height: 2.5rem;
      border-radius: 50%;
      background: var(--color-primary);
      color: white;
      font-weight: 600;
      border: none;
      cursor: pointer;
    }
    .header__avatar:hover {
      opacity: 0.9;
    }
    .user-info {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: var(--spacing-lg);
      padding: var(--spacing-md);
    }
    .user-info__avatar {
      width: 4rem;
      height: 4rem;
      border-radius: 50%;
      background: var(--color-primary);
      color: white;
      font-size: 1.5rem;
      font-weight: 600;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .user-info__details {
      width: 100%;
      display: flex;
      flex-direction: column;
      gap: var(--spacing-sm);
    }
    .user-info__row {
      display: flex;
      justify-content: space-between;
      padding: var(--spacing-xs) 0;
      border-bottom: 1px solid var(--color-border);
    }
    .user-info__row:last-child {
      border-bottom: none;
    }
    .user-info__label {
      font-weight: 500;
      color: var(--color-text-secondary);
    }
    .user-info__value {
      color: var(--color-text-primary);
    }
  `]
})
export class HeaderComponent {
  readonly auth = inject(AuthService);
  readonly showUserModal = signal(false);

  openUserModal(): void {
    this.showUserModal.set(true);
  }

  closeUserModal(): void {
    this.showUserModal.set(false);
  }

  onLogout(): void {
    this.auth.logout();
  }
}