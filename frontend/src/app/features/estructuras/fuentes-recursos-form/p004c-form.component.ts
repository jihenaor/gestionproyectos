import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface Fuente {
  codigoFuente: string;
  nombreFuente: string;
  valor: string;
  porcentaje: string;
  tipoRecurso: string;
}

@Component({
  selector: 'app-p004c-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-004C: Fuentes de Recursos</h2>
        <p class="estructura-form__description">
          Diligencie las fuentes de recursos que financian el proyecto. El porcentaje total debe sumar 100%.
        </p>
      </header>

      <div class="fuentes-tabla">
        <div class="fuentes-header">
          <span>Código Fuente *</span>
          <span>Nombre Fuente *</span>
          <span>Valor *</span>
          <span>Porcentaje *</span>
          <span>Tipo Recurso *</span>
          <span></span>
        </div>

        @for (fuente of fuentes(); track $index; let i = $index) {
          <div class="fuentes-row">
            <input
              type="text"
              class="fuentes-input"
              placeholder="Código"
              [ngModel]="fuente.codigoFuente"
              (ngModelChange)="updateFuente(i, 'codigoFuente', $event)"
              maxlength="20"
            />
            <input
              type="text"
              class="fuentes-input"
              placeholder="Nombre de la fuente"
              [ngModel]="fuente.nombreFuente"
              (ngModelChange)="updateFuente(i, 'nombreFuente', $event)"
              maxlength="200"
            />
            <input
              type="text"
              class="fuentes-input"
              placeholder="Valor"
              [ngModel]="fuente.valor"
              (ngModelChange)="updateFuente(i, 'valor', $event)"
            />
            <input
              type="text"
              class="fuentes-input porcentaje"
              placeholder="%"
              [ngModel]="fuente.porcentaje"
              (ngModelChange)="updateFuente(i, 'porcentaje', $event)"
            />
            <select
              class="fuentes-select"
              [ngModel]="fuente.tipoRecurso"
              (ngModelChange)="updateFuente(i, 'tipoRecurso', $event)">
              <option value="">Seleccione...</option>
              <option value="001">001 - Recursos propios</option>
              <option value="002">002 - Recursos externos</option>
              <option value="003">003 - Aportes territoriales</option>
              <option value="004">004 - Creditos</option>
              <option value="005">005 - Donaciones</option>
              <option value="006">006 - Subsidios</option>
            </select>
            <button type="button" class="fuentes-remove" (click)="removeFuente(i)">×</button>
          </div>
        }

        <button type="button" class="fuentes-add" (click)="addFuente()">
          + Agregar Fuente
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Fuentes:</span>
          <span class="resumen-value">{{ fuentes().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Porcentaje Total:</span>
          <span class="resumen-value" [class.error]="sumaPorcentajes() !== 100">
            {{ sumaPorcentajes() | number:'1.2-2' }}%
          </span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Valor Total:</span>
          <span class="resumen-value">{{ valorTotal() | number }}</span>
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
          Guardar P-004C
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1200px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .fuentes-tabla { margin-bottom: var(--spacing-lg); }
    .fuentes-header {
      display: grid;
      grid-template-columns: 100px 1fr 150px 100px 150px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.75rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .fuentes-row {
      display: grid;
      grid-template-columns: 100px 1fr 150px 100px 150px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .fuentes-input, .fuentes-select {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.875rem;
      width: 100%;
    }
    .fuentes-input:focus, .fuentes-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .fuentes-input.porcentaje { text-align: right; }
    .fuentes-remove {
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
    .fuentes-remove:hover { background: var(--color-error); color: white; }
    .fuentes-add {
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
    .fuentes-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
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
export class P004CFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  fuentes = signal<Fuente[]>([
    { codigoFuente: '', nombreFuente: '', valor: '0', porcentaje: '0.00', tipoRecurso: '' }
  ]);

  guardando = signal(false);

  sumaPorcentajes(): number {
    return this.fuentes().reduce((sum, f) => {
      return sum + (parseFloat(f.porcentaje) || 0);
    }, 0);
  }

  valorTotal(): number {
    return this.fuentes().reduce((sum, f) => {
      return sum + (parseFloat(f.valor) || 0);
    }, 0);
  }

  addFuente(): void {
    this.fuentes.update(fuentes => [
      ...fuentes,
      { codigoFuente: '', nombreFuente: '', valor: '0', porcentaje: '0.00', tipoRecurso: '' }
    ]);
  }

  removeFuente(index: number): void {
    if (this.fuentes().length > 1) {
      this.fuentes.update(fuentes => fuentes.filter((_, i) => i !== index));
    }
  }

  updateFuente(index: number, field: keyof Fuente, value: string): void {
    this.fuentes.update(fuentes => {
      const updated = [...fuentes];
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
      fuentes: this.fuentes().map(f => ({
        codigoFuente: f.codigoFuente,
        nombreFuente: f.nombreFuente,
        valor: f.valor,
        porcentaje: f.porcentaje,
        tipoRecurso: f.tipoRecurso
      }))
    };

    this.guardando.set(true);
    this.estructuraService.guardarP004C(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-004C', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.fuentes.set([
      { codigoFuente: '', nombreFuente: '', valor: '0', porcentaje: '0.00', tipoRecurso: '' }
    ]);
  }
}