import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService, PeriodoSupersubsidio } from '../../../core/services/api.service';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { BadgeComponent } from '../../../shared/atoms/badge/badge.component';
import { ToastService } from '../../../shared/molecules/toast/toast.component';
import { ProyectoResponse } from '../../../core/models/proyecto.model';

interface EstructuraXml {
  codigo: string;
  nombreXml: string;
  nombreArchivo: string;
  seleccionado: boolean;
}

interface ErrorInfo {
  proyectoId: string;
  proyectoCodigo: string;
  mensaje: string;
}

interface ArchivoGenerado {
  proyectoId: string;
  proyectoCodigo: string;
  estructuraCodigo: string;
  nombreArchivo: string;
  contenido: string;
  tamano: string;
}

/** Periodo por defecto si CIEF no responde o hasta que el usuario pulse «Cargar Datos». */
const PERIODO_SUPERSUBSIDIO_FALLBACK: PeriodoSupersubsidio = {
  codigo: 1,
  codigoPeriodo: '202605',
  fechaInicial: 20260501,
  fechaFinal: 20260531,
  descripcion: 'Mayo 2026',
  periodicidad: 'M',
  periodicidad2: 'MENSUAL',
  motivoCodigo: '01',
  fechaLimiteReporte: 0,
  fechaLimiteRevision: 0,
  anio: 2026,
  mes: 5,
  codigoAnterior: null,
  solicitanteCodigo: null,
  codigoPeriodoTipo: null,
  codigoInfraestructura: null,
  motivoCodigoXml: null,
  codigoPeriodoLq: '202605',
  nombreMes: 'MAYO',
  periodoAaaaMm: '2026-05',
  periodoActivo: 'S'
};

const ESTRUCTURAS_XML_INIT: EstructuraXml[] = [
  { codigo: 'P-001A', nombreXml: 'PROYECTOS_NUEVOS', nombreArchivo: 'CCF044_001A_PERC.xml', seleccionado: true },
  { codigo: 'P-002A', nombreXml: 'CRONOGRAMA_INICIAL_PROYECTO', nombreArchivo: 'CCF044_002A_PERC.xml', seleccionado: true },
  { codigo: 'P-003A', nombreXml: 'LOCALIZACION_PROYECTO', nombreArchivo: 'CCF044_003A_PERC.xml', seleccionado: true },
  { codigo: 'P-004C', nombreXml: 'ESTRUCTURA_FUENTE_RECURSOS', nombreArchivo: 'CCF044_004C_PERC.xml', seleccionado: true },
  { codigo: 'P-005A', nombreXml: 'FICHA_TECNICA_PROYECTOS', nombreArchivo: 'CCF044_005A_PERC.xml', seleccionado: true },
  { codigo: 'P-011A', nombreXml: 'COBERTURA_PROYECTADA', nombreArchivo: 'CCF044_011A_PERC.xml', seleccionado: true },
  { codigo: 'P-011B', nombreXml: 'COBERTURA_EJECUTADA', nombreArchivo: 'CCF044_011B_PERC.xml', seleccionado: true },
  { codigo: 'P-012A', nombreXml: 'SEGUIMIENTO_PROYECTO', nombreArchivo: 'CCF044_012A_PERC.xml', seleccionado: true },
  { codigo: 'P-013A', nombreXml: 'ASPECTOS_INFRAESTRUCTURA', nombreArchivo: 'CCF044_013A_PERC.xml', seleccionado: true },
  { codigo: 'P-023A', nombreXml: 'FONDOS_CREDITO', nombreArchivo: 'CCF044_023A_PERC.xml', seleccionado: true },
  { codigo: 'P-024A', nombreXml: 'CARTERA_POR_EDADES', nombreArchivo: 'CCF044_024A_PERC.xml', seleccionado: true },
  { codigo: 'P-026A', nombreXml: 'ARRENDAMIENTO_INMUEBLES', nombreArchivo: 'CCF044_026A_PERC.xml', seleccionado: true },
  { codigo: 'P-031A', nombreXml: 'COMODATO_INMUEBLES', nombreArchivo: 'CCF044_031A_PERC.xml', seleccionado: true },
  { codigo: 'P-034A', nombreXml: 'COMPRA_INMUEBLES', nombreArchivo: 'CCF044_034A_PERC.xml', seleccionado: true },
  { codigo: 'P-040A', nombreXml: 'PERMUTA_INMUEBLES', nombreArchivo: 'CCF044_040A_PERC.xml', seleccionado: true },
  { codigo: 'P-050A', nombreXml: 'NEGOCIACION_ACCIONES', nombreArchivo: 'CCF044_050A_PERC.xml', seleccionado: true },
  { codigo: 'P-055A', nombreXml: 'CAPITALIZACIONES', nombreArchivo: 'CCF044_055A_PERC.xml', seleccionado: true }
];

@Component({
  selector: 'app-xml-generator',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, BadgeComponent],
  template: `
    <div class="xml-generator">
      <header class="xml-generator__header">
        <div>
          <h1 class="xml-generator__title">Circular Superintendencia</h1>
          <p class="xml-generator__subtitle">Generación de archivos XML para reporte de información</p>
        </div>
        <div class="xml-generator__periodo-section">
          <label class="xml-generator__periodo-label" for="periodo-select">Periodo de Reporte:</label>
          @if (periodosDisponibles().length > 0) {
            <select
              id="periodo-select"
              class="xml-generator__periodo-select"
              [(ngModel)]="periodoSeleccionado"
              (ngModelChange)="onPeriodoChange($event)">
              @for (p of periodosDisponibles(); track p.codigo) {
                <option [value]="p.codigoPeriodo">
                  {{ p.descripcion }} ({{ p.anio }}/{{ p.mes }})
                </option>
              }
            </select>
          } @else {
            <span class="xml-generator__periodo-loading">Cargando periodos...</span>
          }
        </div>
      </header>

      <section class="xml-generator__estructuras-info">
        <div class="xml-generator__estructuras-header">
          <div>
            <h2 class="xml-generator__section-title">Formatos a Generar</h2>
            <p class="xml-generator__info-text">
              Seleccione los formatos que desea incluir en la generación de XML.
              Total seleccionados: <strong>{{ formatosSeleccionados() }}</strong> de {{ estructurasXml().length }}
            </p>
          </div>
          <div class="xml-generator__format-actions">
            <button class="xml-generator__link-btn" (click)="seleccionarTodos()">Seleccionar todos</button>
            <span class="xml-generator__separator">|</span>
            <button class="xml-generator__link-btn" (click)="deseleccionarTodos()">Deseleccionar todos</button>
          </div>
        </div>
        <div class="xml-generator__table-wrapper">
          <table class="xml-generator__table">
            <thead>
              <tr>
                <th class="xml-generator__th-check"></th>
                <th>Código</th>
                <th>Nombre Estructura</th>
                <th>Nombre Archivo XML</th>
              </tr>
            </thead>
            <tbody>
              @for (estructura of estructurasXml(); track estructura.codigo) {
                <tr [class.xml-generator__row-selected]="estructura.seleccionado">
                  <td class="xml-generator__td-check">
                    <input
                      type="checkbox"
                      [checked]="estructura.seleccionado"
                      (change)="toggleFormato(estructura.codigo)"
                      [id]="'chk-' + estructura.codigo"
                      class="xml-generator__checkbox" />
                  </td>
                  <td class="xml-generator__codigo">{{ estructura.codigo }}</td>
                  <td>
                    <label [for]="'chk-' + estructura.codigo" class="xml-generator__nombre-label">
                      {{ estructura.nombreXml }}
                    </label>
                  </td>
                  <td class="xml-generator__filename">{{ estructura.nombreArchivo }}</td>
                </tr>
              }
            </tbody>
          </table>
        </div>
      </section>

      <section class="xml-generator__proyectos">
        <div class="xml-generator__proyectos-header">
          <h2 class="xml-generator__section-title">Proyectos Registrados</h2>
          <div class="xml-generator__header-actions">
            <app-button
              variant="secondary"
              [disabled]="cargandoProyectos()"
              (click)="cargarTodo()">
              {{ cargandoProyectos() ? 'Cargando...' : 'Cargar Datos' }}
            </app-button>
            <app-button
              variant="primary"
              [disabled]="proyectos().length === 0 || generando() || formatosSeleccionados() === 0"
              (click)="generarTodosLosXmls()">
              {{ generando() ? 'Generando...' : 'Generar XMLs' }}
            </app-button>
          </div>
        </div>

        <div class="xml-generator__summary">
          <span class="xml-generator__summary-item">
            <strong>{{ proyectos().length }}</strong> proyectos registrados
          </span>
          <span class="xml-generator__summary-item">
            <strong>{{ formatosSeleccionados() }}</strong> formatos seleccionados
          </span>
          <span class="xml-generator__summary-item">
            <strong>{{ totalXmlsAGenerar() }}</strong> XMLs a generar
          </span>
        </div>

        @if (cargandoProyectos()) {
          <div class="xml-generator__loading">Cargando datos...</div>
        } @else if (proyectos().length === 0 && estructurasXml().length === 0) {
          <div class="xml-generator__empty">
            <p>Haga clic en "Cargar Datos" para consultar los proyectos y periodos.</p>
          </div>
        } @else if (proyectos().length === 0) {
          <div class="xml-generator__empty">
            <p>No se encontraron proyectos. Verifique la conexión al servidor.</p>
          </div>
        } @else {
          <div class="xml-generator__proyectos-list">
            @for (proyecto of proyectos(); track proyecto.id) {
              <div class="xml-generator__proyecto-item">
                <div class="xml-generator__proyecto-info">
                  <span class="xml-generator__proyecto-codigo">{{ proyecto.codigo }}</span>
                  <span class="xml-generator__proyecto-nombre">{{ proyecto.nombre }}</span>
                  <span class="xml-generator__proyecto-estado">
                    @if (proyecto.estado) {
                      <app-badge variant="info">{{ proyecto.estado }}</app-badge>
                    }
                  </span>
                </div>
                <div class="xml-generator__proyecto-xmls">
                  @for (estructura of getFormatosSeleccionados(); track estructura.codigo) {
                    <span class="xml-generator__xml-tag" [title]="estructura.nombreXml">
                      {{ estructura.codigo }}
                    </span>
                  }
                </div>
              </div>
            }
          </div>
        }
      </section>

      @if (errores().length > 0) {
        <section class="xml-generator__errores">
          <h2 class="xml-generator__section-title xml-generator__section-title--error">
            Errores Encontrados ({{ errores().length }})
          </h2>
          <div class="xml-generator__errores-list">
            @for (error of errores(); track error.proyectoId + error.mensaje) {
              <div class="xml-generator__error-item">
                <div class="xml-generator__error-header">
                  @if (error.proyectoCodigo) {
                    <span class="xml-generator__error-proyecto">{{ error.proyectoCodigo }}</span>
                  }
                  <span class="xml-generator__error-badge">Error</span>
                </div>
                <p class="xml-generator__error-mensaje">{{ error.mensaje }}</p>
              </div>
            }
          </div>
        </section>
      }

      @if (archivosGenerados().length > 0) {
        <section class="xml-generator__downloads">
          <h2 class="xml-generator__section-title">Archivos Generados ({{ archivosGenerados().length }})</h2>
          <div class="xml-generator__downloads-actions">
            <app-button variant="primary" (click)="descargarTodoZip()">
              Descargar Todos los XMLs (ZIP)
            </app-button>
            <app-button variant="secondary" (click)="limpiarArchivos()">
              Limpiar
            </app-button>
          </div>

          <div class="xml-generator__files-summary">
            <div class="xml-generator__files-by-project">
              @for (proyectoAgrupado of archivosAgrupadosPorProyecto(); track proyectoAgrupado.proyectoId) {
                <div class="xml-generator__project-group">
                  <div class="xml-generator__project-group-header">
                    <span class="xml-generator__project-group-name">{{ proyectoAgrupado.proyectoCodigo }}</span>
                    <span class="xml-generator__project-group-count">{{ proyectoAgrupado.archivos.length }} XMLs</span>
                    <app-button variant="outline" size="sm" (click)="descargarZipProyecto(proyectoAgrupado.proyectoId, proyectoAgrupado.proyectoCodigo)">
                      Descargar ZIP
                    </app-button>
                  </div>
                  <div class="xml-generator__files-list">
                    @for (archivo of proyectoAgrupado.archivos; track archivo.nombreArchivo) {
                      <div class="xml-generator__file-item">
                        <span class="xml-generator__file-name">{{ archivo.nombreArchivo }}</span>
                        <span class="xml-generator__file-size">{{ archivo.tamano }}</span>
                      </div>
                    }
                  </div>
                </div>
              }
            </div>
          </div>
        </section>
      }
    </div>
  `,
  styles: [`
    .xml-generator {
      max-width: 1200px;
      margin: 0 auto;
      padding: var(--spacing-xl);
    }
    .xml-generator__header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: var(--spacing-xl);
    }
    .xml-generator__title {
      font-size: 1.875rem;
      font-weight: 700;
      margin: 0 0 var(--spacing-xs) 0;
    }
    .xml-generator__subtitle {
      color: var(--color-text-secondary);
      margin: 0;
    }
    .xml-generator__periodo-section {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xs);
      align-items: flex-end;
    }
    .xml-generator__periodo-label {
      font-size: 0.75rem;
      font-weight: 500;
      color: var(--color-text-secondary);
      text-transform: uppercase;
    }
    .xml-generator__periodo-select {
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      background: var(--color-background);
      font-size: 0.875rem;
      font-weight: 600;
      min-width: 200px;
      cursor: pointer;
    }
    .xml-generator__periodo-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .xml-generator__periodo-loading {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
      font-style: italic;
    }
    .xml-generator__section-title {
      font-size: 1.125rem;
      font-weight: 600;
      margin: 0 0 var(--spacing-xs) 0;
    }
    .xml-generator__info-text {
      color: var(--color-text-secondary);
      margin: 0;
      font-size: 0.875rem;
    }
    .xml-generator__estructuras-info {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      margin-bottom: var(--spacing-xl);
    }
    .xml-generator__estructuras-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: var(--spacing-md);
    }
    .xml-generator__format-actions {
      display: flex;
      align-items: center;
      gap: var(--spacing-xs);
    }
    .xml-generator__link-btn {
      background: none;
      border: none;
      color: var(--color-primary);
      font-size: 0.75rem;
      cursor: pointer;
      padding: 0;
    }
    .xml-generator__link-btn:hover {
      text-decoration: underline;
    }
    .xml-generator__separator {
      color: var(--color-text-secondary);
      font-size: 0.75rem;
    }
    .xml-generator__table-wrapper {
      overflow-x: auto;
    }
    .xml-generator__table {
      width: 100%;
      border-collapse: collapse;
    }
    .xml-generator__table th,
    .xml-generator__table td {
      padding: var(--spacing-sm) var(--spacing-md);
      text-align: left;
      border-bottom: 1px solid var(--color-border);
    }
    .xml-generator__table th {
      font-weight: 600;
      color: var(--color-text-secondary);
      font-size: 0.875rem;
      background: var(--color-background-secondary);
    }
    .xml-generator__th-check {
      width: 40px;
    }
    .xml-generator__td-check {
      width: 40px;
      text-align: center;
    }
    .xml-generator__checkbox {
      cursor: pointer;
      width: 16px;
      height: 16px;
    }
    .xml-generator__row-selected {
      background: var(--color-primary-light, #e0f2fe22);
    }
    .xml-generator__nombre-label {
      cursor: pointer;
    }
    .xml-generator__codigo {
      font-family: monospace;
      font-weight: 600;
      color: var(--color-primary);
    }
    .xml-generator__filename {
      font-family: monospace;
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .xml-generator__proyectos {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      margin-bottom: var(--spacing-xl);
    }
    .xml-generator__proyectos-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-md);
    }
    .xml-generator__header-actions {
      display: flex;
      gap: var(--spacing-sm);
    }
    .xml-generator__summary {
      display: flex;
      gap: var(--spacing-xl);
      padding: var(--spacing-md);
      background: var(--color-background-secondary);
      border-radius: var(--radius-md);
      margin-bottom: var(--spacing-md);
    }
    .xml-generator__summary-item {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .xml-generator__loading {
      display: flex;
      justify-content: center;
      padding: var(--spacing-xl);
      color: var(--color-text-secondary);
    }
    .xml-generator__empty {
      text-align: center;
      padding: var(--spacing-xl);
      color: var(--color-text-secondary);
    }
    .xml-generator__proyectos-list {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-sm);
      max-height: 400px;
      overflow-y: auto;
    }
    .xml-generator__proyecto-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-background-secondary);
      border-radius: var(--radius-md);
    }
    .xml-generator__proyecto-info {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
    }
    .xml-generator__proyecto-codigo {
      font-family: monospace;
      font-weight: 600;
    }
    .xml-generator__proyecto-nombre {
      color: var(--color-text-secondary);
    }
    .xml-generator__proyecto-xmls {
      display: flex;
      gap: var(--spacing-xs);
      flex-wrap: wrap;
    }
    .xml-generator__xml-tag {
      padding: 2px 6px;
      background: var(--color-primary);
      color: white;
      font-size: 0.625rem;
      font-weight: 600;
      border-radius: 2px;
    }
    .xml-generator__downloads {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
    }
    .xml-generator__downloads-actions {
      display: flex;
      gap: var(--spacing-md);
      margin-bottom: var(--spacing-lg);
    }
    .xml-generator__files-summary {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-md);
    }
    .xml-generator__files-by-project {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-md);
    }
    .xml-generator__project-group {
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      overflow: hidden;
    }
    .xml-generator__project-group-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: var(--spacing-sm) var(--spacing-md);
      background: var(--color-background-secondary);
    }
    .xml-generator__project-group-name {
      font-family: monospace;
      font-weight: 600;
    }
    .xml-generator__project-group-count {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .xml-generator__files-list {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: var(--spacing-sm);
      padding: var(--spacing-sm);
    }
    .xml-generator__file-item {
      display: flex;
      justify-content: space-between;
      padding: var(--spacing-xs) var(--spacing-sm);
      background: var(--color-background);
      border-radius: var(--radius-sm);
      font-size: 0.875rem;
    }
    .xml-generator__file-name {
      font-family: monospace;
    }
    .xml-generator__file-size {
      color: var(--color-text-secondary);
      font-size: 0.75rem;
    }
    .xml-generator__errores {
      background: var(--color-error-light, #fef2f2);
      border: 1px solid var(--color-error, #DC2626);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
      margin-bottom: var(--spacing-xl);
    }
    .xml-generator__section-title--error {
      color: var(--color-error, #DC2626);
    }
    .xml-generator__errores-list {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-sm);
    }
    .xml-generator__error-item {
      padding: var(--spacing-sm) var(--spacing-md);
      background: white;
      border-radius: var(--radius-md);
      border-left: 4px solid var(--color-error, #DC2626);
    }
    .xml-generator__error-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-xs);
    }
    .xml-generator__error-proyecto {
      font-family: monospace;
      font-weight: 600;
      color: var(--color-text-primary);
    }
    .xml-generator__error-badge {
      padding: 2px 8px;
      background: var(--color-error, #DC2626);
      color: white;
      font-size: 0.75rem;
      font-weight: 600;
      border-radius: 4px;
    }
    .xml-generator__error-mensaje {
      margin: 0;
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
  `]
})
export class XmlGeneratorComponent implements OnInit {
  private readonly apiService = inject(ApiService);
  private readonly toastService = inject(ToastService);

  periodoSeleccionado = '';
  periodosDisponibles = signal<PeriodoSupersubsidio[]>([]);
  periodoActual = signal<string>('');
  estructurasXml = signal<EstructuraXml[]>([]);
  proyectos = signal<ProyectoResponse[]>([]);
  archivosGenerados = signal<ArchivoGenerado[]>([]);
  errores = signal<ErrorInfo[]>([]);
  cargandoProyectos = signal(false);
  generando = signal(false);

  formatosSeleccionados = computed(() =>
    this.estructurasXml().filter(e => e.seleccionado).length
  );

  totalXmlsAGenerar = computed(() =>
    this.proyectos().length * this.formatosSeleccionados()
  );

  archivosAgrupadosPorProyecto = computed(() => {
    const archivos = this.archivosGenerados();
    const agrupado = new Map<string, { proyectoId: string; proyectoCodigo: string; archivos: ArchivoGenerado[] }>();

    for (const archivo of archivos) {
      if (!agrupado.has(archivo.proyectoId)) {
        agrupado.set(archivo.proyectoId, {
          proyectoId: archivo.proyectoId,
          proyectoCodigo: archivo.proyectoCodigo,
          archivos: []
        });
      }
      agrupado.get(archivo.proyectoId)!.archivos.push(archivo);
    }

    return Array.from(agrupado.values());
  });

  ngOnInit(): void {
    this.estructurasXml.set(ESTRUCTURAS_XML_INIT);
    // No llamar a CIEF aquí: evita periodos-reporte al montar la ruta (p. ej. tras login con returnUrl).
    this.setFallbackPeriodos();
  }

  private setFallbackPeriodos(): void {
    this.periodosDisponibles.set([PERIODO_SUPERSUBSIDIO_FALLBACK]);
    this.periodoSeleccionado = PERIODO_SUPERSUBSIDIO_FALLBACK.codigoPeriodo;
    this.periodoActual.set(PERIODO_SUPERSUBSIDIO_FALLBACK.codigoPeriodo);
  }

  /**
   * Obtiene periodos desde CIEF y ejecuta `then` al terminar (éxito o error con fallback).
   * La petición se dispara desde «Cargar Datos», no al abrir la pantalla.
   */
  private cargarPeriodosDesdeCief(then: () => void): void {
    this.apiService.getPeriodosSupersubsidio('01').subscribe({
      next: (periodos) => {
        if (periodos && periodos.length > 0) {
          this.periodosDisponibles.set(periodos);
          this.periodoSeleccionado = periodos[0].codigoPeriodo;
          this.periodoActual.set(periodos[0].codigoPeriodo);
        } else {
          this.setFallbackPeriodos();
        }
      },
      error: () => this.setFallbackPeriodos(),
      complete: () => then()
    });
  }

  onPeriodoChange(codigoPeriodo: string): void {
    this.periodoActual.set(codigoPeriodo);
  }

  toggleFormato(codigo: string): void {
    this.estructurasXml.update(lista =>
      lista.map(e => e.codigo === codigo ? { ...e, seleccionado: !e.seleccionado } : e)
    );
  }

  seleccionarTodos(): void {
    this.estructurasXml.update(lista => lista.map(e => ({ ...e, seleccionado: true })));
  }

  deseleccionarTodos(): void {
    this.estructurasXml.update(lista => lista.map(e => ({ ...e, seleccionado: false })));
  }

  getFormatosSeleccionados(): EstructuraXml[] {
    return this.estructurasXml().filter(e => e.seleccionado);
  }

  cargarTodo(): void {
    this.cargandoProyectos.set(true);
    this.cargarPeriodosDesdeCief(() => this.cargarProyectos());
  }

  cargarProyectos(): void {
    this.apiService.getProyectos(0, 1000).subscribe({
      next: (response) => {
        this.proyectos.set(response.content);
        this.cargandoProyectos.set(false);
      },
      error: (err) => {
        this.cargandoProyectos.set(false);
        this.errores.set([{
          proyectoId: '',
          proyectoCodigo: '',
          mensaje: 'Error cargando proyectos: ' + (err.statusText || err.message || 'Error del servidor')
        }]);
      }
    });
  }

  generarTodosLosXmls(): void {
    const periodo = this.periodoActual();
    if (!periodo) {
      this.toastService.error('No hay periodo activo definido');
      return;
    }

    const formatosActivos = this.getFormatosSeleccionados();
    if (formatosActivos.length === 0) {
      this.toastService.error('Debe seleccionar al menos un formato');
      return;
    }

    this.generando.set(true);
    const proyectos = this.proyectos();

    this.archivosGenerados.set([]);
    this.errores.set([]);

    let completados = 0;
    let exitosos = 0;
    let fallidos = 0;

    for (const proyecto of proyectos) {
      this.apiService.generarZipXml(proyecto.id, periodo).subscribe({
        next: (blob) => {
          this.archivosGenerados.update(files => [...files, {
            proyectoId: proyecto.id,
            proyectoCodigo: proyecto.codigo,
            estructuraCodigo: 'ZIP',
            nombreArchivo: `${proyecto.codigo}_xmls_${periodo}.zip`,
            contenido: '',
            tamano: this.formatearTamano(blob.size)
          }]);
          exitosos++;
          completados++;
          this.actualizarEstadoGeneracion(completados, proyectos.length, exitosos, fallidos);
        },
        error: (err) => {
          fallidos++;
          this.errores.update(errores => [...errores, {
            proyectoId: proyecto.id,
            proyectoCodigo: proyecto.codigo,
            mensaje: 'Error generando XML para proyecto ' + proyecto.codigo + ': ' + (err.message || 'Error del servidor')
          }]);
          completados++;
          this.actualizarEstadoGeneracion(completados, proyectos.length, exitosos, fallidos);
        }
      });
    }
  }

  private actualizarEstadoGeneracion(completados: number, total: number, exitosos: number, fallidos: number): void {
    if (completados >= total) {
      this.generando.set(false);
      if (fallidos > 0) {
        this.toastService.warning(`Generación completada: ${exitosos} exitosos, ${fallidos} fallidos de ${total} proyectos`);
      } else {
        this.toastService.success(`Se generaron ${exitosos} archivos XML exitosamente`);
      }
    }
  }

  descargarZipProyecto(proyectoId: string, proyectoCodigo: string): void {
    const periodo = this.periodoActual();
    this.apiService.generarZipXml(proyectoId, periodo).subscribe({
      next: (blob) => {
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `xmls_${proyectoCodigo}.zip`;
        a.click();
        URL.revokeObjectURL(url);
        this.toastService.success(`Descarga iniciada: ${proyectoCodigo}`);
      },
      error: (err) => {
        this.toastService.error(`Error descargando ZIP de ${proyectoCodigo}: ` + (err.message || 'Error del servidor'));
      }
    });
  }

  descargarTodoZip(): void {
    const proyectos = this.proyectos();
    const periodo = this.periodoActual();

    for (const proyecto of proyectos) {
      setTimeout(() => {
        this.descargarZipProyecto(proyecto.id, proyecto.codigo);
      }, 500);
    }
  }

  limpiarArchivos(): void {
    this.archivosGenerados.set([]);
  }

  private formatearTamano(bytes: number): string {
    return bytes < 1024 ? `${bytes} B` : `${(bytes / 1024).toFixed(1)} KB`;
  }
}