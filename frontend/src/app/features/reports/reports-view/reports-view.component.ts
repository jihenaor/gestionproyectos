import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-reports-view',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent],
  template: `
    <div class="reports">
      <header class="reports__header">
        <h1 class="reports__title">Reportes</h1>
        <p class="reports__subtitle">Generación de reportes y consolidado de estructuras</p>
        @let periodo = periodoActual();
        @if (periodo) {
          <div class="reports__periodo">
            <span class="reports__periodo-label">Periodo activo:</span>
            <span class="reports__periodo-value">{{ periodo.codigoPeriodo }} - {{ periodo.descripcion }}</span>
            <span class="reports__periodo-anio">{{ periodo.anio }}/{{ periodo.mes }}</span>
          </div>
        }
      </header>

      <div class="reports__filters">
        <app-input
          [inputId]="'fechaInicio'"
          [type]="'date'"
          [label]="'Fecha Inicio'"
          [value]="fechaInicio()"
          (input)="onFechaInicioInput($event)"
        />
        <app-input
          [inputId]="'fechaFin'"
          [type]="'date'"
          [label]="'Fecha Fin'"
          [value]="fechaFin()"
          (input)="onFechaFinInput($event)"
        />
        <app-select
          [selectId]="'tipoReporte'"
          [label]="'Tipo de Reporte'"
          [options]="reporteOptions"
          [value]="tipoReporte()"
          (change)="onTipoReporteChange($event)"
        />
        <app-button variant="primary" (click)="generarReporte()">Generar</app-button>
      </div>

      <div class="reports__content">
        @if (isLoading()) {
          <div class="reports__loading">Generando reporte...</div>
        } @else if (reporteGenerado()) {
          <div class="reports__result">
            <h2>Reporte Generado</h2>
            <p>El reporte está listo para descargar.</p>
            <app-button variant="primary">Descargar PDF</app-button>
          </div>
        } @else {
          <div class="reports__placeholder">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="64" height="64">
              <line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/>
            </svg>
            <p>Seleccione los filtros y genere un reporte</p>
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .reports { max-width: 1000px; margin: 0 auto; }
    .reports__header { margin-bottom: var(--spacing-xl); }
    .reports__title { font-size: 1.875rem; font-weight: 700; margin: 0 0 var(--spacing-xs) 0; }
    .reports__subtitle { color: var(--color-text-secondary); margin: 0; }
    .reports__periodo { display: flex; align-items: center; gap: var(--spacing-sm); margin-top: var(--spacing-md); padding: var(--spacing-sm) var(--spacing-md); background: var(--color-primary-light, #e0f2fe); border-radius: var(--radius-md); font-size: 0.875rem; }
    .reports__periodo-label { font-weight: 500; color: var(--color-text-secondary); }
    .reports__periodo-value { font-weight: 600; color: var(--color-primary, #0284c7); }
    .reports__periodo-anio { color: var(--color-text-secondary); }
    .reports__filters { display: flex; gap: var(--spacing-md); align-items: flex-end; flex-wrap: wrap; margin-bottom: var(--spacing-xl); padding: var(--spacing-lg); background: var(--color-background); border: 1px solid var(--color-border); border-radius: var(--radius-lg); }
    .reports__content { background: var(--color-background); border: 1px solid var(--color-border); border-radius: var(--radius-lg); min-height: 400px; padding: var(--spacing-xl); }
    .reports__loading, .reports__placeholder { display: flex; flex-direction: column; align-items: center; justify-content: center; height: 300px; color: var(--color-text-secondary); }
    .reports__placeholder svg { margin-bottom: var(--spacing-md); opacity: 0.5; }
    .reports__result { text-align: center; }
    .reports__result h2 { margin: 0 0 var(--spacing-sm) 0; }
    .reports__result p { color: var(--color-text-secondary); margin: 0 0 var(--spacing-lg) 0; }
  `]
})
export class ReportsViewComponent {
  private readonly apiService = inject(ApiService);

  fechaInicio = signal('');
  fechaFin = signal('');
  tipoReporte = signal('');
  isLoading = signal(false);
  reporteGenerado = signal(false);
  periodoActual = signal<{codigoPeriodo: string; descripcion: string; anio: number; mes: number} | null>(null);

  reporteOptions = [
    { value: 'consolidado', label: 'Consolidado de Estructuras' },
    { value: 'proyectos', label: 'Estado de Proyectos' },
    { value: 'fovis', label: 'Reporte FOVIS' }
  ];

  constructor() {
    this.cargarPeriodoActivo();
  }

  cargarPeriodoActivo(): void {
    this.apiService.getPeriodoActivo().subscribe({
      next: (periodo) => {
        this.periodoActual.set(periodo);
      },
      error: () => {
        console.error('Error cargando periodo activo');
      }
    });
  }

  generarReporte(): void {
    if (!this.fechaInicio() || !this.fechaFin() || !this.tipoReporte()) {
      return;
    }

    this.isLoading.set(true);
    this.apiService.getReportes(this.fechaInicio(), this.fechaFin()).subscribe({
      next: () => {
        this.isLoading.set(false);
        this.reporteGenerado.set(true);
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  onFechaInicioInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.fechaInicio.set(value);
  }

  onFechaFinInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.fechaFin.set(value);
  }

  onTipoReporteChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.tipoReporte.set(value);
  }
}