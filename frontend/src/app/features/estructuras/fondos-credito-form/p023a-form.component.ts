import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface FondoCredito {
  modalidadCredito: string;
  codigoCategoria: string;
  tasaInteresMinima: string;
  tasaInteresMaxima: string;
  cantCreditos: string;
  valMontoCreditos: string;
  plazoCredito: string;
  porcentajeSubsidio: string;
}

@Component({
  selector: 'app-p023a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-023A: Fondos de Crédito</h2>
        <p class="estructura-form__description">
          Diligencie la información de los fondos de crédito disponibles. Cada fila representa
          una modalidad de crédito con sus condiciones.
        </p>
      </header>

      <div class="fondos-tabla">
        <div class="fondos-header">
          <span>Modalidad *</span>
          <span>Categoría *</span>
          <span>Tasa Mínima *</span>
          <span>Tasa Máxima *</span>
          <span>Cant. Créditos</span>
          <span>Valor Total</span>
          <span>Plazo (meses)</span>
          <span>% Subsidio</span>
          <span></span>
        </div>

        @for (fondo of fondos(); track $index; let i = $index) {
          <div class="fondos-row">
            <select
              class="fondos-select"
              [ngModel]="fondo.modalidadCredito"
              (ngModelChange)="updateFondo(i, 'modalidadCredito', $event)">
              <option value="">Seleccione...</option>
              <option value="01">01 - Crédito de consumo</option>
              <option value="02">02 - Microcrédito</option>
              <option value="03">03 - Crédito constructor</option>
              <option value="04">04 - Crédito hipotecario</option>
              <option value="05">05 - Crédito educativo</option>
              <option value="06">06 - Crédito comercial</option>
            </select>

            <select
              class="fondos-select"
              [ngModel]="fondo.codigoCategoria"
              (ngModelChange)="updateFondo(i, 'codigoCategoria', $event)">
              <option value="">Seleccione...</option>
              <option value="0101">0101 - Primera infancia</option>
              <option value="0102">0102 - Infancia</option>
              <option value="0103">0103 - Adolescencia</option>
              <option value="0104">0104 - Juventud</option>
              <option value="0105">0105 - Adultez</option>
              <option value="0106">0106 - Vejez</option>
              <option value="0201">0201 - Personas con discapacidad</option>
              <option value="0202">0202 - Migrantes</option>
              <option value="0203">0203 - Victimas conflicto</option>
              <option value="0204">0204 - Población rural</option>
            </select>

            <input
              type="text"
              class="fondos-input"
              placeholder="Tasa mínima"
              [ngModel]="fondo.tasaInteresMinima"
              (ngModelChange)="updateFondo(i, 'tasaInteresMinima', $event)"
            />

            <input
              type="text"
              class="fondos-input"
              placeholder="Tasa máxima"
              [ngModel]="fondo.tasaInteresMaxima"
              (ngModelChange)="updateFondo(i, 'tasaInteresMaxima', $event)"
            />

            <input
              type="text"
              class="fondos-input"
              placeholder="Cantidad"
              [ngModel]="fondo.cantCreditos"
              (ngModelChange)="updateFondo(i, 'cantCreditos', $event)"
            />

            <input
              type="text"
              class="fondos-input"
              placeholder="Valor total"
              [ngModel]="fondo.valMontoCreditos"
              (ngModelChange)="updateFondo(i, 'valMontoCreditos', $event)"
            />

            <input
              type="text"
              class="fondos-input"
              placeholder="Meses"
              [ngModel]="fondo.plazoCredito"
              (ngModelChange)="updateFondo(i, 'plazoCredito', $event)"
            />

            <input
              type="text"
              class="fondos-input"
              placeholder="%"
              [ngModel]="fondo.porcentajeSubsidio"
              (ngModelChange)="updateFondo(i, 'porcentajeSubsidio', $event)"
            />

            <button type="button" class="fondos-remove" (click)="removeFondo(i)">×</button>
          </div>
        }

        <button type="button" class="fondos-add" (click)="addFondo()">
          + Agregar Fondo
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Fondos:</span>
          <span class="resumen-value">{{ fondos().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Total Créditos:</span>
          <span class="resumen-value">{{ totalCreditos() | number }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Monto Total:</span>
          <span class="resumen-value">{{ montoTotal() | number }}</span>
        </div>
      </div>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-023A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1400px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .fondos-tabla { margin-bottom: var(--spacing-lg); }
    .fondos-header {
      display: grid;
      grid-template-columns: 140px 130px 100px 100px 90px 110px 90px 90px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.65rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .fondos-row {
      display: grid;
      grid-template-columns: 140px 130px 100px 100px 90px 110px 90px 90px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .fondos-input, .fondos-select {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.75rem;
      width: 100%;
    }
    .fondos-input:focus, .fondos-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .fondos-remove {
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
    .fondos-remove:hover { background: var(--color-error); color: white; }
    .fondos-add {
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
    .fondos-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
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
export class P023AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  fondos = signal<FondoCredito[]>([
    { modalidadCredito: '', codigoCategoria: '', tasaInteresMinima: '0', tasaInteresMaxima: '0', cantCreditos: '0', valMontoCreditos: '0', plazoCredito: '0', porcentajeSubsidio: '0' }
  ]);

  guardando = signal(false);

  totalCreditos(): number {
    return this.fondos().reduce((sum, f) => sum + (parseInt(f.cantCreditos) || 0), 0);
  }

  montoTotal(): number {
    return this.fondos().reduce((sum, f) => sum + (parseFloat(f.valMontoCreditos) || 0), 0);
  }

  addFondo(): void {
    this.fondos.update(fondos => [
      ...fondos,
      { modalidadCredito: '', codigoCategoria: '', tasaInteresMinima: '0', tasaInteresMaxima: '0', cantCreditos: '0', valMontoCreditos: '0', plazoCredito: '0', porcentajeSubsidio: '0' }
    ]);
  }

  removeFondo(index: number): void {
    if (this.fondos().length > 1) {
      this.fondos.update(fondos => fondos.filter((_, i) => i !== index));
    }
  }

  updateFondo(index: number, field: keyof FondoCredito, value: string): void {
    this.fondos.update(fondos => {
      const updated = [...fondos];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  guardar(): void {
    const datos = {
      codigoProyecto: this.codigoProyecto(),
      fondos: this.fondos().map(f => ({
        modalidadCredito: f.modalidadCredito,
        codigoCategoria: f.codigoCategoria,
        tasaInteresMinima: f.tasaInteresMinima,
        tasaInteresMaxima: f.tasaInteresMaxima,
        cantCreditos: f.cantCreditos,
        valMontoCreditos: f.valMontoCreditos,
        plazoCredito: f.plazoCredito,
        porcentajeSubsidio: f.porcentajeSubsidio
      }))
    };

    this.guardando.set(true);
    this.estructuraService.guardarP023A(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-023A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.fondos.set([
      { modalidadCredito: '', codigoCategoria: '', tasaInteresMinima: '0', tasaInteresMaxima: '0', cantCreditos: '0', valMontoCreditos: '0', plazoCredito: '0', porcentajeSubsidio: '0' }
    ]);
  }
}