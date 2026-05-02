import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BadgeComponent } from '../../../shared/atoms/badge/badge.component';
import { ProjectStateService } from '../../../core/services/project-state.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, BadgeComponent],
  template: `
    <div class="dashboard">
      <header class="dashboard__header">
        <h1 class="dashboard__title">Dashboard</h1>
        <p class="dashboard__subtitle">Resumen de proyectos de inversión</p>
      </header>

      <div class="dashboard__stats">
        <div class="stat-card">
          <span class="stat-card__value">{{ projectState.projectCount() }}</span>
          <span class="stat-card__label">Total Proyectos</span>
        </div>
        <div class="stat-card stat-card--success">
          <span class="stat-card__value">{{ projectState.completedCount() }}</span>
          <span class="stat-card__label">Completados</span>
        </div>
        <div class="stat-card stat-card--warning">
          <span class="stat-card__value">{{ projectState.pendingCount() }}</span>
          <span class="stat-card__label">En Progreso</span>
        </div>
        <div class="stat-card stat-card--info">
          <span class="stat-card__value">{{ estructurasPendientes() }}</span>
          <span class="stat-card__label">Estructuras Pendientes</span>
        </div>
      </div>

      <section class="dashboard__section">
        <h2 class="dashboard__section-title">Acciones Rápidas</h2>
        <div class="dashboard__actions">
          <a routerLink="/projects/new" class="action-card">
            <span class="action-card__icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
              </svg>
            </span>
            <span class="action-card__label">Nuevo Proyecto</span>
          </a>
          <a routerLink="/fovis" class="action-card">
            <span class="action-card__icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"/>
              </svg>
            </span>
            <span class="action-card__label">Proyectos FOVIS</span>
          </a>
          <a routerLink="/reports" class="action-card">
            <span class="action-card__icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/>
              </svg>
            </span>
            <span class="action-card__label">Reportes</span>
          </a>
        </div>
      </section>

      <section class="dashboard__section">
        <h2 class="dashboard__section-title">Proyectos Recientes</h2>
        <div class="dashboard__projects">
          @for (proyecto of projectState.projects().slice(0, 5); track proyecto.id) {
            <a [routerLink]="['/projects', proyecto.id]" class="project-card">
              <div class="project-card__header">
                <span class="project-card__code">{{ proyecto.codigo }}</span>
                <app-badge [variant]="getBadgeVariant(proyecto.estado)">{{ proyecto.estado }}</app-badge>
              </div>
              <h3 class="project-card__name">{{ proyecto.nombre }}</h3>
              <div class="project-card__footer">
                <span class="project-card__modalidad">{{ proyecto.modalidad }}</span>
                <span class="project-card__valor">{{ formatCurrency(proyecto.valorTotal) }}</span>
              </div>
            </a>
          } @empty {
            <div class="empty-state">
              <p>No hay proyectos creados aún.</p>
              <a routerLink="/projects/new" class="dashboard__link">Crear el primer proyecto</a>
            </div>
          }
        </div>
      </section>
    </div>
  `,
  styles: [`
    .dashboard {
      max-width: 1200px;
      margin: 0 auto;
    }
    .dashboard__header {
      margin-bottom: var(--spacing-xl);
    }
    .dashboard__title {
      font-size: 1.875rem;
      font-weight: 700;
      margin: 0 0 var(--spacing-xs) 0;
    }
    .dashboard__subtitle {
      color: var(--color-text-secondary);
      margin: 0;
    }
    .dashboard__stats {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: var(--spacing-md);
      margin-bottom: var(--spacing-2xl);
    }
    .stat-card {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      display: flex;
      flex-direction: column;
    }
    .stat-card__value {
      font-size: 2rem;
      font-weight: 700;
      color: var(--color-text-primary);
    }
    .stat-card__label {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .stat-card--success .stat-card__value { color: var(--color-success); }
    .stat-card--warning .stat-card__value { color: var(--color-warning); }
    .stat-card--info .stat-card__value { color: var(--color-info); }
    .dashboard__section {
      margin-bottom: var(--spacing-2xl);
    }
    .dashboard__section-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin: 0 0 var(--spacing-md) 0;
    }
    .dashboard__actions {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: var(--spacing-md);
    }
    .action-card {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: var(--spacing-sm);
      text-decoration: none;
      transition: all 0.15s ease;
    }
    .action-card:hover {
      border-color: var(--color-primary);
      box-shadow: var(--shadow-md);
    }
    .action-card__icon {
      width: 3rem;
      height: 3rem;
      background: var(--color-info-bg);
      color: var(--color-info);
      border-radius: var(--radius-md);
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .action-card__icon svg {
      width: 1.5rem;
      height: 1.5rem;
    }
    .action-card__label {
      font-weight: 500;
      color: var(--color-text-primary);
    }
    .dashboard__projects {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: var(--spacing-md);
    }
    .project-card {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      text-decoration: none;
      transition: all 0.15s ease;
    }
    .project-card:hover {
      box-shadow: var(--shadow-md);
    }
    .project-card__header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-sm);
    }
    .project-card__code {
      font-size: 0.875rem;
      font-weight: 600;
      color: var(--color-text-secondary);
    }
    .project-card__name {
      font-size: 1rem;
      font-weight: 500;
      color: var(--color-text-primary);
      margin: 0 0 var(--spacing-md) 0;
    }
    .project-card__footer {
      display: flex;
      justify-content: space-between;
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .project-card__valor {
      font-weight: 500;
    }
    .empty-state {
      text-align: center;
      padding: var(--spacing-2xl);
      color: var(--color-text-secondary);
    }
    .dashboard__link {
      color: var(--color-primary);
      font-weight: 500;
    }
  `]
})
export class DashboardComponent implements OnInit {
  readonly projectState = inject(ProjectStateService);

  estructurasPendientes = signal(42);

  ngOnInit(): void {
    this.projectState.reloadFromServer();
  }

  getBadgeVariant(estado: string): 'success' | 'warning' | 'error' | 'info' | 'neutral' {
    switch (estado) {
      case 'Completado': return 'success';
      case 'En Ejecución': return 'warning';
      case 'Cancelado': return 'error';
      default: return 'neutral';
    }
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(value);
  }
}