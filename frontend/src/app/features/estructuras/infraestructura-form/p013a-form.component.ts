import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

@Component({
  selector: 'app-p013a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-013A: Infraestructura del Proyecto</h2>
        <p class="estructura-form__description">
          Diligencie la información de infraestructura asociada al proyecto. Los campos
          cambian según el tipo de licencia de construcción seleccionada.
        </p>
      </header>

      <form class="estructura-form__form">
        <div class="form-row">
          <app-form-field [label]="'Código del Proyecto'" [fieldId]="'codigoProyecto'">
            <input type="text" id="codigoProyecto" class="form-input" [(ngModel)]="datos.codigoProyecto" disabled />
          </app-form-field>

          <app-form-field [label]="'Tipo de Licencia de Construcción'" [fieldId]="'licenciaConstruccion'" [isRequired]="true">
            <select id="licenciaConstruccion" class="form-select" [(ngModel)]="datos.licenciaConstruccion"
              (ngModelChange)="onLicenciaChange($event)">
              <option value="">Seleccione...</option>
              <option [ngValue]="1">1 - N.A. (No aplica)</option>
              <option [ngValue]="2">2 - En trámite</option>
              <option [ngValue]="3">3 - Expedida</option>
            </select>
          </app-form-field>
        </div>

        @if (datos.licenciaConstruccion === 2 || datos.licenciaConstruccion === 3) {
          <div class="conditional-section">
            <h3 class="conditional-title">Información de la Licencia</h3>
            <div class="form-row">
              <app-form-field [label]="'Número de Licencia'" [fieldId]="'numeroLicencia'">
                <input type="text" id="numeroLicencia" class="form-input" [(ngModel)]="datos.numeroLicencia"
                  maxlength="50" />
              </app-form-field>

              <app-form-field [label]="'Fecha de Expedición'" [fieldId]="'fechaExpedicionLicencia'">
                <input type="date" id="fechaExpedicionLicencia" class="form-input"
                  [(ngModel)]="datos.fechaExpedicionLicencia" />
              </app-form-field>
            </div>

            <div class="form-row">
              <app-form-field [label]="'Vigencia Licencia'" [fieldId]="'vigenciaLicencia'">
                <input type="text" id="vigenciaLicencia" class="form-input" [(ngModel)]="datos.vigenciaLicencia"
                  maxlength="50" />
              </app-form-field>

              @if (datos.licenciaConstruccion === 3) {
                <app-form-field [label]="'Fecha Vencimiento'" [fieldId]="'fechaVencimientoLicencia'">
                  <input type="date" id="fechaVencimientoLicencia" class="form-input"
                    [(ngModel)]="datos.fechaVencimientoLicencia" />
                </app-form-field>
              }
            </div>
          </div>
        }

        <div class="form-row">
          <app-form-field [label]="'Servicios Públicos'" [fieldId]="'serviciosPublicos'" [isRequired]="true">
            <select id="serviciosPublicos" class="form-select" [(ngModel)]="datos.serviciosPublicos"
              (ngModelChange)="onServiciosChange($event)">
              <option value="">Seleccione...</option>
              <option value="1">1 - Todos disponibles</option>
              <option value="2">2 - Algunos disponibles</option>
              <option value="3">3 - Ninguno disponible</option>
              <option value="4">4 - En proyecto</option>
            </select>
          </app-form-field>

          @if (datos.serviciosPublicos === '2' || datos.serviciosPublicos === '4') {
            <app-form-field [label]="'Cuales Servicios'" [fieldId]="'cualesServicios'">
              <input type="text" id="cualesServicios" class="form-input" [(ngModel)]="datos.cualesServicios"
                maxlength="200" placeholder="Cuáles servicios?" />
            </app-form-field>
          }
        </div>

        @if (datos.serviciosPublicos === '4') {
          <div class="conditional-section">
            <h3 class="conditional-title">Plan de Servicios</h3>
            <div class="form-row">
              <app-form-field [label]="'Fecha Estimada Disponibilidad'" [fieldId]="'fechaEstimadaServicios'">
                <input type="date" id="fechaEstimadaServicios" class="form-input"
                  [(ngModel)]="datos.fechaEstimadaServicios" />
              </app-form-field>

              <app-form-field [label]="'Inversión Requerida'" [fieldId]="'inversionServicios'">
                <input type="text" id="inversionServicios" class="form-input" [(ngModel)]="datos.inversionServicios"
                  placeholder="Valor en pesos" />
              </app-form-field>
            </div>
          </div>
        }

        <div class="form-row">
          <app-form-field [label]="'Área del Terreno (m²)'" [fieldId]="'areaTerreno'">
            <input type="text" id="areaTerreno" class="form-input" [(ngModel)]="datos.areaTerreno"
              placeholder="Área en metros cuadrados" />
          </app-form-field>

          <app-form-field [label]="'Área Construida (m²)'" [fieldId]="'areaConstruida'">
            <input type="text" id="areaConstruida" class="form-input" [(ngModel)]="datos.areaConstruida"
              placeholder="Área en metros cuadrados" />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Número de Pisos'" [fieldId]="'numeroPisos'">
            <input type="number" id="numeroPisos" class="form-input" [(ngModel)]="datos.numeroPisos" />
          </app-form-field>

          <app-form-field [label]="'Destinación'" [fieldId]="'destinacionInmueble'">
            <select id="destinacionInmueble" class="form-select" [(ngModel)]="datos.destinacionInmueble">
              <option value="">Seleccione...</option>
              <option value="1">1 - Educativo</option>
              <option value="2">2 - Salud</option>
              <option value="3">3 - Recreativo</option>
              <option value="4">4 - Comercial</option>
              <option value="5">5 - Institucional</option>
              <option value="6">6 - Otro</option>
            </select>
          </app-form-field>
        </div>

        <app-form-field [label]="'Observaciones'" [fieldId]="'observaciones'">
          <textarea id="observaciones" class="form-textarea" [(ngModel)]="datos.observaciones"
            rows="3" maxlength="1000"></textarea>
        </app-form-field>
      </form>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-013A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 900px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .estructura-form__form { display: flex; flex-direction: column; gap: var(--spacing-md); margin-bottom: var(--spacing-lg); }
    .form-row { display: grid; grid-template-columns: repeat(2, 1fr); gap: var(--spacing-md); }
    .form-input, .form-select, .form-textarea {
      width: 100%;
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      font-size: 1rem;
      background: var(--color-background);
    }
    .form-input:focus, .form-select:focus, .form-textarea:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .form-input:disabled, .form-select:disabled { background: var(--color-background-secondary); cursor: not-allowed; }
    .form-textarea { resize: vertical; }
    .conditional-section {
      padding: var(--spacing-md);
      background: var(--color-background-secondary);
      border-radius: var(--radius-md);
      margin-bottom: var(--spacing-md);
    }
    .conditional-title {
      font-size: 0.875rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin: 0 0 var(--spacing-sm) 0;
    }
    .estructura-form__actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding-top: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
    }
  `]
})
export class P013AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  guardando = signal(false);

  datos: any = {
    codigoProyecto: '',
    licenciaConstruccion: null,
    numeroLicencia: '',
    fechaExpedicionLicencia: '',
    vigenciaLicencia: '',
    fechaVencimientoLicencia: '',
    serviciosPublicos: '',
    cualesServicios: '',
    fechaEstimadaServicios: '',
    inversionServicios: '',
    areaTerreno: '',
    areaConstruida: '',
    numeroPisos: 0,
    destinacionInmueble: '',
    observaciones: ''
  };

  ngOnInit() {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  onLicenciaChange(value: number): void {
    if (value !== 2 && value !== 3) {
      this.datos.numeroLicencia = '';
      this.datos.fechaExpedicionLicencia = '';
      this.datos.vigenciaLicencia = '';
      this.datos.fechaVencimientoLicencia = '';
    }
  }

  onServiciosChange(value: string): void {
    if (value !== '2' && value !== '4') {
      this.datos.cualesServicios = '';
      this.datos.fechaEstimadaServicios = '';
      this.datos.inversionServicios = '';
    }
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP013A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-013A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      licenciaConstruccion: null,
      numeroLicencia: '',
      fechaExpedicionLicencia: '',
      vigenciaLicencia: '',
      fechaVencimientoLicencia: '',
      serviciosPublicos: '',
      cualesServicios: '',
      fechaEstimadaServicios: '',
      inversionServicios: '',
      areaTerreno: '',
      areaConstruida: '',
      numeroPisos: 0,
      destinacionInmueble: '',
      observaciones: ''
    };
  }
}