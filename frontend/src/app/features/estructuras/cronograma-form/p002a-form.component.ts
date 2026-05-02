import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface Actividad {
  tipoActividad: string;
  descripcion: string;
  porcentaje: string;
  fechaInicio: string;
  fechaTerminacion: string;
}

@Component({
  selector: 'app-p002a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-002A: Cronograma Inicial del Proyecto</h2>
        <p class="estructura-form__description">
          Diligencie una fila por cada tipo de actividad planned. El cronograma define las actividades,
          sus porcentajes y fechas de ejecución.
        </p>
      </header>

      <div class="cronograma-tabla">
        <div class="cronograma-header">
          <span>Tipo Actividad *</span>
          <span>Descripción</span>
          <span>% Planeado *</span>
          <span>Fecha Inicio *</span>
          <span>Fecha Terminación *</span>
          <span></span>
        </div>

        @for (actividad of actividades(); track $index; let i = $index) {
          <div class="cronograma-row">
            <select
              class="cronograma-select"
              [ngModel]="actividad.tipoActividad"
              (ngModelChange)="updateActividad(i, 'tipoActividad', $event)">
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
              class="cronograma-input"
              placeholder="Descripción de la actividad"
              [ngModel]="actividad.descripcion"
              (ngModelChange)="updateActividad(i, 'descripcion', $event)"
              maxlength="500"
            />

            <input
              type="text"
              class="cronograma-input porcentaje"
              placeholder="25.00"
              [ngModel]="actividad.porcentaje"
              (ngModelChange)="updateActividad(i, 'porcentaje', $event)"
            />

            <input
              type="date"
              class="cronograma-input"
              [ngModel]="actividad.fechaInicio"
              (ngModelChange)="updateActividad(i, 'fechaInicio', $event)"
            />

            <input
              type="date"
              class="cronograma-input"
              [ngModel]="actividad.fechaTerminacion"
              (ngModelChange)="updateActividad(i, 'fechaTerminacion', $event)"
            />

            <button type="button" class="cronograma-remove" (click)="removeActividad(i)">×</button>
          </div>
        }

        <button type="button" class="cronograma-add" (click)="addActividad()">
          + Agregar Actividad
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Actividades:</span>
          <span class="resumen-value">{{ actividades().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Porcentaje Total:</span>
          <span class="resumen-value" [class.error]="sumaPorcentajes() !== 100">
            {{ sumaPorcentajes() | number:'1.2-2' }}%
          </span>
        </div>
      </div>

      @if (sumaPorcentajes() !== 100) {
        <div class="estructura-form__warning">
          ⚠️ La suma de porcentajes debe ser 100%
        </div>
      }

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-002A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1000px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .cronograma-tabla { margin-bottom: var(--spacing-lg); }
    .cronograma-header {
      display: grid;
      grid-template-columns: 150px 1fr 100px 130px 130px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.75rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .cronograma-row {
      display: grid;
      grid-template-columns: 150px 1fr 100px 130px 130px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .cronograma-select, .cronograma-input {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.875rem;
      width: 100%;
    }
    .cronograma-select:focus, .cronograma-input:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .cronograma-input.porcentaje { text-align: right; }
    .cronograma-remove {
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
    .cronograma-remove:hover { background: var(--color-error); color: white; }
    .cronograma-add {
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
    .cronograma-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
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
    .resumen-value.error { color: var(--color-error); }
    .estructura-form__warning {
      padding: var(--spacing-md);
      background: var(--color-warning-bg);
      border: 1px solid var(--color-warning);
      border-radius: var(--radius-md);
      color: var(--color-warning);
      margin-bottom: var(--spacing-md);
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
export class P002AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  actividades = signal<Actividad[]>([
    { tipoActividad: '', descripcion: '', porcentaje: '0.00', fechaInicio: '', fechaTerminacion: '' }
  ]);

  guardando = signal(false);

  sumaPorcentajes(): number {
    return this.actividades().reduce((sum, act) => {
      const pct = parseFloat(act.porcentaje) || 0;
      return sum + pct;
    }, 0);
  }

  addActividad(): void {
    this.actividades.update(acts => [
      ...acts,
      { tipoActividad: '', descripcion: '', porcentaje: '0.00', fechaInicio: '', fechaTerminacion: '' }
    ]);
  }

  removeActividad(index: number): void {
    if (this.actividades().length > 1) {
      this.actividades.update(acts => acts.filter((_, i) => i !== index));
    }
  }

  updateActividad(index: number, field: keyof Actividad, value: string): void {
    this.actividades.update(acts => {
      const updated = [...acts];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  guardar(): void {
    if (this.sumaPorcentajes() !== 100) {
      alert('La suma de porcentajes debe ser 100%');
      return;
    }

    const datos = {
      codigoProyecto: this.codigoProyecto(),
      actividades: this.actividades().map(a => ({
        tipoActividad: a.tipoActividad,
        descripcionActividad: a.descripcion,
        porcentajeProyectado: a.porcentaje,
        fechaInicio: this.formatDateToAAAAMMDD(a.fechaInicio),
        fechaTerminacion: this.formatDateToAAAAMMDD(a.fechaTerminacion)
      })),
      porcentajeTotal: this.sumaPorcentajes().toFixed(2)
    };

    this.guardando.set(true);
    this.estructuraService.guardarP002A(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-002A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.actividades.set([
      { tipoActividad: '', descripcion: '', porcentaje: '0.00', fechaInicio: '', fechaTerminacion: '' }
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