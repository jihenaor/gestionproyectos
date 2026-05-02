import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface Seguimiento {
  tipoActividad: string;
  descripcion: string;
  porcentajeEjecutado: string;
  valorPlaneado: string;
  valorEjecutado: string;
  costoActual: string;
  fechaInicio: string;
  fechaTerminacion: string;
}

@Component({
  selector: 'app-p012a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-012A: Seguimiento del Proyecto</h2>
        <p class="estructura-form__description">
          Diligencie el reporte de seguimiento del proyecto. El período debe indicarse en formato AAAAMMDD.
        </p>
      </header>

      <form class="estructura-form__form">
        <div class="form-row">
          <app-form-field [label]="'Código del Proyecto'" [fieldId]="'codigoProyecto'">
            <input type="text" id="codigoProyecto" class="form-input" [(ngModel)]="datos.codigoProyecto" disabled />
          </app-form-field>

          <app-form-field [label]="'Período del Reporte'" [fieldId]="'periodoReporte'" [isRequired]="true"
            [description]="'Formato: AAAAMMDD'" [formatExample]="'20260330'">
            <input type="text" id="periodoReporte" class="form-input" [(ngModel)]="datos.periodoReporte"
              placeholder="AAAAMMDD" maxlength="8" />
          </app-form-field>
        </div>
      </form>

      <div class="seguimiento-tabla">
        <div class="seguimiento-header">
          <span>Tipo Actividad *</span>
          <span>Descripción</span>
          <span>% Ejecutado *</span>
          <span>Valor Planeado</span>
          <span>Valor Ejecutado</span>
          <span>Costo Actual</span>
          <span>Fecha Inicio</span>
          <span>Fecha Term.</span>
          <span></span>
        </div>

        @for (seg of seguimientos(); track $index; let i = $index) {
          <div class="seguimiento-row">
            <select
              class="seguimiento-select"
              [ngModel]="seg.tipoActividad"
              (ngModelChange)="updateSeguimiento(i, 'tipoActividad', $event)">
              <option value="">Seleccione...</option>
              <option value="0501">0501 - Prediseño</option>
              <option value="0502">0502 - Diseño</option>
              <option value="0503">0503 - Ejecución</option>
              <option value="0504">0504 - Interventoría</option>
              <option value="0505">0505 - Adquisición de bienes</option>
              <option value="0506">0506 - Adecuación de bienes</option>
              <option value="0507">0507 - Mantenimiento de bienes</option>
              <option value="0508">0508 - Dotación</option>
              <option value="0509">0509 - Otros</option>
              <option value="0601">0601 - Capacitación</option>
              <option value="0602">0602 - Evaluar</option>
              <option value="0603">0603 - Certificar</option>
              <option value="0401">0401 - Desembolso crédito social</option>
              <option value="0402">0402 - Desembolso microcrédito</option>
              <option value="0403">0403 - Desembolso crédito constructor</option>
              <option value="0404">0404 - Pago honorarios</option>
              <option value="0405">0405 - Pago nómina</option>
              <option value="0406">0406 - Pago proveedores</option>
              <option value="0407">0407 - Pago arriendos</option>
              <option value="0408">0408 - Pago servicios</option>
              <option value="0409">0409 - Pago obras</option>
            </select>

            <input
              type="text"
              class="seguimiento-input"
              placeholder="Descripción"
              [ngModel]="seg.descripcion"
              (ngModelChange)="updateSeguimiento(i, 'descripcion', $event)"
              maxlength="500"
            />

            <input
              type="text"
              class="seguimiento-input porcentaje"
              placeholder="%"
              [ngModel]="seg.porcentajeEjecutado"
              (ngModelChange)="updateSeguimiento(i, 'porcentajeEjecutado', $event)"
            />

            <input
              type="text"
              class="seguimiento-input"
              placeholder="Valor"
              [ngModel]="seg.valorPlaneado"
              (ngModelChange)="updateSeguimiento(i, 'valorPlaneado', $event)"
            />

            <input
              type="text"
              class="seguimiento-input"
              placeholder="Valor"
              [ngModel]="seg.valorEjecutado"
              (ngModelChange)="updateSeguimiento(i, 'valorEjecutado', $event)"
            />

            <input
              type="text"
              class="seguimiento-input"
              placeholder="Costo"
              [ngModel]="seg.costoActual"
              (ngModelChange)="updateSeguimiento(i, 'costoActual', $event)"
            />

            <input
              type="date"
              class="seguimiento-input"
              [ngModel]="seg.fechaInicio"
              (ngModelChange)="updateSeguimiento(i, 'fechaInicio', $event)"
            />

            <input
              type="date"
              class="seguimiento-input"
              [ngModel]="seg.fechaTerminacion"
              (ngModelChange)="updateSeguimiento(i, 'fechaTerminacion', $event)"
            />

            <button type="button" class="seguimiento-remove" (click)="removeSeguimiento(i)">×</button>
          </div>
        }

        <button type="button" class="seguimiento-add" (click)="addSeguimiento()">
          + Agregar Seguimiento
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Registros:</span>
          <span class="resumen-value">{{ seguimientos().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">% Promedio Ejecutado:</span>
          <span class="resumen-value">{{ promedioEjecutado() | number:'1.2-2' }}%</span>
        </div>
      </div>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-012A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1400px; }
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
    .form-input:disabled { background: var(--color-background-secondary); cursor: not-allowed; }
    .seguimiento-tabla { margin-bottom: var(--spacing-lg); }
    .seguimiento-header {
      display: grid;
      grid-template-columns: 120px 1fr 80px 100px 100px 100px 110px 110px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.65rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .seguimiento-row {
      display: grid;
      grid-template-columns: 120px 1fr 80px 100px 100px 100px 110px 110px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .seguimiento-input, .seguimiento-select {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.75rem;
      width: 100%;
    }
    .seguimiento-input:focus, .seguimiento-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .seguimiento-input.porcentaje { text-align: right; }
    .seguimiento-remove {
      width: 2rem;
      height: 2rem;
      border: none;
      background: var(--color-error-bg);
      color: var(--color-error);
      border-radius: var(--radius-sm);
      cursor: pointer;
      font-size: 1.25rem;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .seguimiento-remove:hover { background: var(--color-error); color: white; }
    .seguimiento-add {
      margin-top: var(--spacing-sm);
      padding: var(--spacing-sm) var(--spacing-md);
      border: 2px dashed var(--color-border);
      background: transparent;
      border-radius: var(--radius-md);
      color: var(--color-text-secondary);
      cursor: pointer;
      width: 100%;
      font-weight: 500;
    }
    .seguimiento-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
    .estructura-form__resumen {
      display: flex;
      gap: var(--spacing-xl);
      padding: var(--spacing-md);
      background: var(--color-background-secondary);
      border-radius: var(--radius-md);
      margin-bottom: var(--spacing-md);
    }
    .resumen-item { display: flex; gap: var(--spacing-sm); }
    .resumen-label { color: var(--color-text-secondary); }
    .resumen-value { font-weight: 600; }
    .estructura-form__actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding-top: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
    }
  `]
})
export class P012AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  seguimientos = signal<Seguimiento[]>([
    { tipoActividad: '', descripcion: '', porcentajeEjecutado: '0.00', valorPlaneado: '0', valorEjecutado: '0', costoActual: '0', fechaInicio: '', fechaTerminacion: '' }
  ]);

  guardando = signal(false);

  datos: any = {
    codigoProyecto: '',
    periodoReporte: ''
  };

  ngOnInit() {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  promedioEjecutado(): number {
    if (this.seguimientos().length === 0) return 0;
    const sum = this.seguimientos().reduce((s, seg) => s + (parseFloat(seg.porcentajeEjecutado) || 0), 0);
    return sum / this.seguimientos().length;
  }

  addSeguimiento(): void {
    this.seguimientos.update(seguimientos => [
      ...seguimientos,
      { tipoActividad: '', descripcion: '', porcentajeEjecutado: '0.00', valorPlaneado: '0', valorEjecutado: '0', costoActual: '0', fechaInicio: '', fechaTerminacion: '' }
    ]);
  }

  removeSeguimiento(index: number): void {
    if (this.seguimientos().length > 1) {
      this.seguimientos.update(seguimientos => seguimientos.filter((_, i) => i !== index));
    }
  }

  updateSeguimiento(index: number, field: keyof Seguimiento, value: string): void {
    this.seguimientos.update(seguimientos => {
      const updated = [...seguimientos];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  guardar(): void {
    const datos = {
      codigoProyecto: this.datos.codigoProyecto,
      periodoReporte: this.datos.periodoReporte,
      seguimientos: this.seguimientos().map(s => ({
        tipoActividad: s.tipoActividad,
        descripcion: s.descripcion,
        porcentajeEjecutado: s.porcentajeEjecutado,
        valorPlaneado: s.valorPlaneado,
        valorEjecutado: s.valorEjecutado,
        costoActual: s.costoActual,
        fechaInicio: this.formatDateToAAAAMMDD(s.fechaInicio),
        fechaTerminacion: this.formatDateToAAAAMMDD(s.fechaTerminacion)
      }))
    };

    this.guardando.set(true);
    this.estructuraService.guardarP012A(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-012A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.seguimientos.set([
      { tipoActividad: '', descripcion: '', porcentajeEjecutado: '0.00', valorPlaneado: '0', valorEjecutado: '0', costoActual: '0', fechaInicio: '', fechaTerminacion: '' }
    ]);
  }

  private formatDateToAAAAMMDD(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}${month}${day}`;
  }
}