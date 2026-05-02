import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-fovis-list',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="fovis-list">
      <header class="fovis-list__header">
        <div>
          <h1 class="fovis-list__title">Proyectos FOVIS</h1>
          <p class="fovis-list__subtitle">Fondo de Vivienda de Interés Social</p>
        </div>
        <a routerLink="/projects/new" class="fovis-list__new-btn">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          Nuevo Proyecto FOVIS
        </a>
      </header>

      <div class="fovis-list__info">
        <div class="info-card">
          <h3>Modalidades FOVIS</h3>
          <ul>
            <li><strong>1 - Obras Asociadas:</strong> Construcción relacionada con vivienda</li>
            <li><strong>2 - Créditos Hipotecarios:</strong> Financiación de vivienda</li>
            <li><strong>3 - Incremento de Capital:</strong> Aportes al fondo</li>
            <li><strong>4 - Adquisición de Lotes:</strong> Compra de terrenos</li>
            <li><strong>5 - Proyectos Integrales:</strong> Desarrollo completo</li>
            <li><strong>6 - Microcrédito:</strong> Pequeños créditos de vivienda</li>
          </ul>
        </div>
      </div>

      <div class="fovis-list__table-container">
        <table class="fovis-list__table">
          <thead>
            <tr>
              <th>Código</th>
              <th>Nombre</th>
              <th>Modalidad</th>
              <th>Valor</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="6" class="fovis-list__empty">
                No hay proyectos FOVIS creados
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: [`
    .fovis-list { max-width: 1200px; margin: 0 auto; }
    .fovis-list__header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: var(--spacing-xl); }
    .fovis-list__title { font-size: 1.875rem; font-weight: 700; margin: 0 0 var(--spacing-xs) 0; }
    .fovis-list__subtitle { color: var(--color-text-secondary); margin: 0; }
    .fovis-list__new-btn { display: inline-flex; align-items: center; gap: var(--spacing-sm); padding: var(--spacing-sm) var(--spacing-md); background: var(--color-primary); color: white; border-radius: var(--radius-md); text-decoration: none; font-weight: 500; }
    .fovis-list__info { margin-bottom: var(--spacing-lg); }
    .info-card { background: var(--color-info-bg); border: 1px solid var(--color-info); border-radius: var(--radius-lg); padding: var(--spacing-lg); }
    .info-card h3 { margin: 0 0 var(--spacing-sm) 0; color: var(--color-info); }
    .info-card ul { margin: 0; padding-left: var(--spacing-lg); color: var(--color-text-secondary); }
    .info-card li { margin-bottom: var(--spacing-xs); }
    .fovis-list__table-container { background: var(--color-background); border: 1px solid var(--color-border); border-radius: var(--radius-lg); overflow: hidden; }
    .fovis-list__table { width: 100%; border-collapse: collapse; }
    .fovis-list__table th, .fovis-list__table td { padding: var(--spacing-md); text-align: left; border-bottom: 1px solid var(--color-border); }
    .fovis-list__table th { background: var(--color-background-secondary); font-weight: 600; font-size: 0.875rem; color: var(--color-text-secondary); }
    .fovis-list__empty { text-align: center; color: var(--color-text-secondary); padding: var(--spacing-2xl) !important; }
  `]
})
export class FovisListComponent {}