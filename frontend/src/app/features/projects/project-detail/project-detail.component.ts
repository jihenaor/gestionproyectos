import { Component, input, signal, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BadgeComponent } from '../../../shared/atoms/badge/badge.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { ApiService } from '../../../core/services/api.service';
import { ProyectoResponse } from '../../../core/models/proyecto.model';
import { EstructuraGuardadaEvent } from '../../../core/models/estructura.model';
import { DatosGeneralesFormComponent } from '../../estructuras/datos-generales-form/datos-generales-form.component';
import { CronogramaFormComponent } from '../../estructuras/cronograma-form/cronograma-form.component';
import { LocalizacionFormComponent } from '../../estructuras/localizacion-form/localizacion-form.component';
import { FuentesRecursosFormComponent } from '../../estructuras/fuentes-recursos-form/fuentes-recursos-form.component';
import { CoberturaProyectadaFormComponent } from '../../estructuras/cobertura-proyectada-form/cobertura-proyectada-form.component';
import { SeguimientoFormComponent } from '../../estructuras/seguimiento-form/seguimiento-form.component';
import { InfraestructuraFormComponent } from '../../estructuras/infraestructura-form/infraestructura-form.component';
import { FondosCreditoFormComponent } from '../../estructuras/fondos-credito-form/fondos-credito-form.component';
import { CarteraEdadesFormComponent } from '../../estructuras/cartera-edades-form/cartera-edades-form.component';
import { ArrendamientoFormComponent } from '../../estructuras/arrendamiento-form/arrendamiento-form.component';
import { ComodatoFormComponent } from '../../estructuras/comodato-form/comodato-form.component';
import { CompraBienesFormComponent } from '../../estructuras/compra-bienes-form/compra-bienes-form.component';
import { PermutaFormComponent } from '../../estructuras/permuta-form/permuta-form.component';
import { DocumentosManagerComponent } from '../../documentos/documentos-manager/documentos-manager.component';

@Component({
  selector: 'app-project-detail',
  standalone: true,
  imports: [
    FormsModule,
    BadgeComponent,
    DatosGeneralesFormComponent,
    CronogramaFormComponent,
    LocalizacionFormComponent,
    FuentesRecursosFormComponent,
    CoberturaProyectadaFormComponent,
    SeguimientoFormComponent,
    InfraestructuraFormComponent,
    FondosCreditoFormComponent,
    CarteraEdadesFormComponent,
    ArrendamientoFormComponent,
    ComodatoFormComponent,
    CompraBienesFormComponent,
    PermutaFormComponent,
    DocumentosManagerComponent
  ],
  template: `
    <div class="project-detail">
      <header class="project-detail__header">
        <div class="project-detail__title-row">
          <button class="project-detail__back" (click)="goBack()">
            ← Volver
          </button>
          <div>
            <h1 class="project-detail__title">{{ proyecto()?.nombre || 'Cargando...' }}</h1>
            <span class="project-detail__code">{{ proyecto()?.codigo }}</span>
          </div>
          <app-badge [variant]="getEstadoVariant()">{{ proyecto()?.estado }}</app-badge>
        </div>
      </header>

      <nav class="project-detail__tabs">
        @for (tab of estructuraTabs; track tab.codigo) {
          <button
            class="project-detail__tab"
            [class.active]="tabActivo() === tab.codigo"
            [class.completado]="estructurasCompletadas().includes(tab.codigo)"
            (click)="seleccionarTab(tab.codigo)">
            <span class="project-detail__tab-name">{{ tab.nombre }}</span>
            <span class="project-detail__tab-code">{{ tab.codigo }}</span>
            @if (estructurasCompletadas().includes(tab.codigo)) {
              <span class="project-detail__tab-check">✓</span>
            }
          </button>
        }
      </nav>

      <div class="project-detail__content">
        @switch (tabActivo()) {
          @case ('P-001A') {
            <app-datos-generales-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-002A') {
            <app-cronograma-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-003A') {
            <app-localizacion-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-004C') {
            <app-fuentes-recursos-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-011A') {
            <app-cobertura-proyectada-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-012A') {
            <app-seguimiento-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-013A') {
            <app-infraestructura-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              [modalidadInversion]="proyecto()?.modalidadInversion || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-023A') {
            <app-fondos-credito-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-024A') {
            <app-cartera-edades-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-026A') {
            <app-arrendamiento-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-031A') {
            <app-comodato-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-034A') {
            <app-compra-bienes-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('P-040A') {
            <app-permuta-form
              [codigoProyecto]="proyecto()?.codigo || ''"
              (guardado)="onEstructuraGuardada($event)"
            />
          }
          @case ('DOCUMENTOS') {
            <app-documentos-manager
              [proyectoId]="proyecto()?.codigo || ''"
            />
          }
          @default {
            <div class="project-detail__placeholder">
              <p>Seleccione una estructura para ver su contenido</p>
            </div>
          }
        }
      </div>
    </div>
  `,
  styles: [`
    .project-detail {
      max-width: 1200px;
      margin: 0 auto;
    }
    .project-detail__header {
      margin-bottom: var(--spacing-lg);
    }
    .project-detail__title-row {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
    }
    .project-detail__back {
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      cursor: pointer;
    }
    .project-detail__back:hover {
      background: var(--color-background-secondary);
    }
    .project-detail__title {
      font-size: 1.5rem;
      font-weight: 700;
      margin: 0;
    }
    .project-detail__code {
      font-family: monospace;
      color: var(--color-text-secondary);
    }
    .project-detail__tabs {
      display: flex;
      flex-wrap: wrap;
      gap: var(--spacing-xs);
      padding: var(--spacing-md);
      background: var(--color-background);
      border-radius: var(--radius-lg);
      margin-bottom: var(--spacing-md);
    }
    .project-detail__tab {
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-background-secondary);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      cursor: pointer;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 2px;
      min-width: 100px;
      position: relative;
    }
    .project-detail__tab:hover {
      background: var(--color-background-tertiary);
    }
    .project-detail__tab.active {
      background: var(--color-primary);
      color: white;
      border-color: var(--color-primary);
    }
    .project-detail__tab.completado {
      background: var(--color-success-bg);
      border-color: var(--color-success);
    }
    .project-detail__tab.completado.active {
      background: var(--color-success);
    }
    .project-detail__tab-name {
      font-size: 0.75rem;
      font-weight: 700;
      color: var(--color-text-primary);
      text-align: center;
      white-space: normal;
      word-break: break-word;
    }
    .project-detail__tab.active .project-detail__tab-name {
      color: white;
    }
    .project-detail__tab-code {
      font-size: 0.625rem;
      font-weight: 400;
      color: var(--color-text-secondary);
      font-family: monospace;
    }
    .project-detail__tab.active .project-detail__tab-code {
      color: rgba(255,255,255,0.7);
    }
    .project-detail__tab-check {
      position: absolute;
      top: -4px;
      right: -4px;
      background: var(--color-success);
      color: white;
      border-radius: 50%;
      width: 16px;
      height: 16px;
      font-size: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .project-detail__content {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
    }
    .project-detail__placeholder {
      text-align: center;
      padding: var(--spacing-2xl);
      color: var(--color-text-secondary);
    }
  `]
})
export class ProjectDetailComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly estructuraService = inject(EstructuraService);

  id = input<string>('');

  proyecto = signal<ProyectoResponse | null>(null);
  tabActivo = signal<string>('P-001A');
  estructurasCompletadas = signal<string[]>([]);

  estructuraTabs = [
    { codigo: 'P-001A', nombre: 'Datos Generales' },
    { codigo: 'P-002A', nombre: 'Cronograma' },
    { codigo: 'P-003A', nombre: 'Localización' },
    { codigo: 'P-004C', nombre: 'Fuentes' },
    { codigo: 'P-011A', nombre: 'Cobertura' },
    { codigo: 'P-012A', nombre: 'Seguimiento' },
    { codigo: 'P-013A', nombre: 'Infraestructura' },
    { codigo: 'P-023A', nombre: 'Fondo Crédito' },
    { codigo: 'P-024A', nombre: 'Cartera' },
    { codigo: 'P-026A', nombre: 'Arrendamiento' },
    { codigo: 'P-031A', nombre: 'Comodato' },
    { codigo: 'P-034A', nombre: 'Compra' },
    { codigo: 'P-040A', nombre: 'Permuta' },
    { codigo: 'DOCUMENTOS', nombre: 'Documentos' }
  ];

  ngOnInit(): void {
    this.loadProject();
  }

  private loadProject(): void {
    this.apiService.getProyectoById(this.id()).subscribe({
      next: (proy) => {
        this.proyecto.set(proy);
      },
      error: () => {
        this.proyecto.set({
          id: this.id(),
          codigo: 'CCF001-01-00001',
          nombre: 'Proyecto Demo',
          modalidad: 'Infraestructura',
          modalidadInversion: 'INF',
          valorTotal: 500000000,
          valorAprobado: 500000000,
          justificacion: 'Proyecto de demostración',
          estado: 'En Ejecución',
          fechaCreacion: '2025-03-15',
          estructurasCompletadas: [],
          porcentajeCompletado: 0
        });
      }
    });

    this.estructuraService.obtenerEstructurasPorProyecto(this.id()).subscribe({
      next: (data) => {
        if (data.estructuras) {
          const completadas = Object.keys(data.estructuras);
          this.estructurasCompletadas.set(completadas);
        }
      },
      error: () => {}
    });
  }

  seleccionarTab(codigo: string): void {
    this.tabActivo.set(codigo);
  }

  onEstructuraGuardada(event: EstructuraGuardadaEvent): void {
    this.estructurasCompletadas.update(current => {
      if (!current.includes(event.estructura)) {
        return [...current, event.estructura];
      }
      return current;
    });
  }

  getEstadoVariant(): 'success' | 'warning' | 'error' | 'info' | 'neutral' {
    const estado = this.proyecto()?.estado;
    switch (estado) {
      case 'Completado': return 'success';
      case 'En Ejecución': return 'warning';
      case 'Cancelado': return 'error';
      default: return 'neutral';
    }
  }

  goBack(): void {
    this.router.navigate(['/projects']);
  }
}