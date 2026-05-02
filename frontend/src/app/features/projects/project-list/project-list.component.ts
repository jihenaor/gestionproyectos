import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BadgeComponent } from '../../../shared/atoms/badge/badge.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { ProjectStateService } from '../../../core/services/project-state.service';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [RouterLink, FormsModule, BadgeComponent, InputComponent],
  template: `
    <div class="project-list">
      <header class="project-list__header">
        <div>
          <h1 class="project-list__title">Proyectos</h1>
          <p class="project-list__subtitle">Gestión de proyectos de inversión</p>
        </div>
        <a routerLink="/projects/new" class="project-list__new-btn">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          Nuevo Proyecto
        </a>
      </header>

      <div class="project-list__filters">
        <app-input
          [inputId]="'search'"
          [placeholder]="'Buscar por código o nombre...'"
          [prefixIcon]="'<svg viewBox=\\'0 0 24 24\\' fill=\\'none\\' stroke=\\'currentColor\\' stroke-width=\\'2\\'><circle cx=\\'11\\' cy=\\'11\\' r=\\'8\\'/><path d=\\'M21 21l-4.35-4.35\\'/></svg>'"
          (input)="onSearch($event)"
        />
        <select class="project-list__filter-select" [(ngModel)]="filterEstado">
          <option value="">Todos los estados</option>
          <option value="Borrador">Borrador</option>
          <option value="En Ejecución">En Ejecución</option>
          <option value="Completado">Completado</option>
          <option value="Cancelado">Cancelado</option>
        </select>
      </div>

      <div class="project-list__table-container">
        <table class="project-list__table">
          <thead>
            <tr>
              <th>Código</th>
              <th>Nombre</th>
              <th>Modalidad</th>
              <th>Valor Total</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            @for (proyecto of projectState.filteredProjects(); track proyecto.id) {
              <tr>
                <td class="project-list__code">{{ proyecto.codigo }}</td>
                <td>{{ proyecto.nombre }}</td>
                <td>{{ proyecto.modalidad }}</td>
                <td class="project-list__valor">{{ formatCurrency(proyecto.valorTotal) }}</td>
                <td>
                  <app-badge [variant]="getBadgeVariant(proyecto.estado)">
                    {{ proyecto.estado }}
                  </app-badge>
                </td>
                <td class="project-list__actions">
                  <a [routerLink]="['/projects', proyecto.id]" class="project-list__action-btn" title="Ver detalle">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="16" height="16">
                      <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/>
                    </svg>
                  </a>
                  <a [routerLink]="['/projects', proyecto.id, 'edit']" class="project-list__action-btn" title="Editar">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="16" height="16">
                      <path d="M11 4H4a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 013 3L12 15l-4 1 1-4 9.5-9.5z"/>
                    </svg>
                  </a>
                </td>
              </tr>
            } @empty {
              <tr>
                <td colspan="6" class="project-list__empty">
                  No hay proyectos que mostrar
                </td>
              </tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: [`
    .project-list {
      max-width: 1200px;
      margin: 0 auto;
    }
    .project-list__header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: var(--spacing-xl);
    }
    .project-list__title {
      font-size: 1.875rem;
      font-weight: 700;
      margin: 0 0 var(--spacing-xs) 0;
    }
    .project-list__subtitle {
      color: var(--color-text-secondary);
      margin: 0;
    }
    .project-list__new-btn {
      display: inline-flex;
      align-items: center;
      gap: var(--spacing-sm);
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-primary);
      color: white;
      border-radius: var(--radius-md);
      text-decoration: none;
      font-weight: 500;
      transition: background 0.15s ease;
    }
    .project-list__new-btn:hover {
      background: var(--color-primary-hover);
    }
    .project-list__filters {
      display: flex;
      gap: var(--spacing-md);
      margin-bottom: var(--spacing-lg);
    }
    .project-list__filters app-input {
      flex: 1;
      max-width: 400px;
    }
    .project-list__filter-select {
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      background: var(--color-background);
      font-size: 1rem;
      min-width: 200px;
    }
    .project-list__table-container {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      overflow: hidden;
    }
    .project-list__table {
      width: 100%;
      border-collapse: collapse;
    }
    .project-list__table th,
    .project-list__table td {
      padding: var(--spacing-md);
      text-align: left;
      border-bottom: 1px solid var(--color-border);
    }
    .project-list__table th {
      background: var(--color-background-secondary);
      font-weight: 600;
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .project-list__table tr:last-child td {
      border-bottom: none;
    }
    .project-list__table tr:hover {
      background: var(--color-background-secondary);
    }
    .project-list__code {
      font-weight: 600;
      font-family: monospace;
    }
    .project-list__valor {
      font-weight: 500;
    }
    .project-list__actions {
      display: flex;
      gap: var(--spacing-sm);
    }
    .project-list__action-btn {
      width: 2rem;
      height: 2rem;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: var(--radius-sm);
      color: var(--color-text-secondary);
      transition: all 0.15s ease;
    }
    .project-list__action-btn:hover {
      background: var(--color-background-tertiary);
      color: var(--color-text-primary);
    }
    .project-list__empty {
      text-align: center;
      color: var(--color-text-secondary);
      padding: var(--spacing-2xl) !important;
    }
  `]
})
export class ProjectListComponent implements OnInit {
  readonly projectState = inject(ProjectStateService);
  searchText = signal('');
  filterEstado = '';

  ngOnInit(): void {
    this.projectState.reloadFromServer();
  }

  onSearch(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.searchText.set(value);
    this.projectState.setFilter(value);
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