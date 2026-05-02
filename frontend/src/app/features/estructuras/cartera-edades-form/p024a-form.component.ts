import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface CarteraEdad {
  rangoEdad: string;
  edadCartera: string;
  modalidadCredito: string;
  codigoCategoria: string;
  cantCreditos: string;
  valorTotalMontoCartera: string;
}

@Component({
  selector: 'app-p024a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-024A: Cartera por Edades</h2>
        <p class="estructura-form__description">
          Diligencie la información de cartera segmentada por edades. Cada fila representa
          un rango de edad con sus respective indicadores de cartera.
        </p>
      </header>

      <div class="cartera-tabla">
        <div class="cartera-header">
          <span>Rango Edad *</span>
          <span>Edad Especifica</span>
          <span>Modalidad *</span>
          <span>Categoría</span>
          <span>Cant. Créditos</span>
          <span>Valor Total Cartera</span>
          <span></span>
        </div>

        @for (cartera of carteras(); track $index; let i = $index) {
          <div class="cartera-row">
            <select
              class="cartera-select"
              [ngModel]="cartera.rangoEdad"
              (ngModelChange)="updateCartera(i, 'rangoEdad', $event)">
              <option value="">Seleccione...</option>
              <option value="01">01 - 0 a 10 años</option>
              <option value="02">02 - 11 a 20 años</option>
              <option value="03">03 - 21 a 30 años</option>
              <option value="04">04 - 31 a 40 años</option>
              <option value="05">05 - 41 a 50 años</option>
              <option value="06">06 - 51 a 60 años</option>
              <option value="07">07 - 61 a 70 años</option>
              <option value="08">08 - 71 a 80 años</option>
              <option value="09">09 - Mayor a 80 años</option>
            </select>

            <input
              type="text"
              class="cartera-input"
              placeholder="Edad específica"
              [ngModel]="cartera.edadCartera"
              (ngModelChange)="updateCartera(i, 'edadCartera', $event)"
            />

            <select
              class="cartera-select"
              [ngModel]="cartera.modalidadCredito"
              (ngModelChange)="updateCartera(i, 'modalidadCredito', $event)">
              <option value="">Seleccione...</option>
              <option value="01">01 - Crédito de consumo</option>
              <option value="02">02 - Microcrédito</option>
              <option value="03">03 - Crédito constructor</option>
              <option value="04">04 - Crédito hipotecario</option>
              <option value="05">05 - Crédito educativo</option>
              <option value="06">06 - Crédito comercial</option>
            </select>

            <select
              class="cartera-select"
              [ngModel]="cartera.codigoCategoria"
              (ngModelChange)="updateCartera(i, 'codigoCategoria', $event)">
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
              class="cartera-input"
              placeholder="Cantidad"
              [ngModel]="cartera.cantCreditos"
              (ngModelChange)="updateCartera(i, 'cantCreditos', $event)"
            />

            <input
              type="text"
              class="cartera-input"
              placeholder="Valor total"
              [ngModel]="cartera.valorTotalMontoCartera"
              (ngModelChange)="updateCartera(i, 'valorTotalMontoCartera', $event)"
            />

            <button type="button" class="cartera-remove" (click)="removeCartera(i)">×</button>
          </div>
        }

        <button type="button" class="cartera-add" (click)="addCartera()">
          + Agregar Cartera
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Registros:</span>
          <span class="resumen-value">{{ carteras().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Total Créditos:</span>
          <span class="resumen-value">{{ totalCreditos() | number }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Valor Total Cartera:</span>
          <span class="resumen-value">{{ valorTotalCartera() | number }}</span>
        </div>
      </div>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-024A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1200px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .cartera-tabla { margin-bottom: var(--spacing-lg); }
    .cartera-header {
      display: grid;
      grid-template-columns: 120px 120px 150px 130px 110px 140px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.7rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .cartera-row {
      display: grid;
      grid-template-columns: 120px 120px 150px 130px 110px 140px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .cartera-input, .cartera-select {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.875rem;
      width: 100%;
    }
    .cartera-input:focus, .cartera-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .cartera-remove {
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
    .cartera-remove:hover { background: var(--color-error); color: white; }
    .cartera-add {
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
    .cartera-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
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
export class P024AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  carteras = signal<CarteraEdad[]>([
    { rangoEdad: '', edadCartera: '', modalidadCredito: '', codigoCategoria: '', cantCreditos: '0', valorTotalMontoCartera: '0' }
  ]);

  guardando = signal(false);

  totalCreditos(): number {
    return this.carteras().reduce((sum, c) => sum + (parseInt(c.cantCreditos) || 0), 0);
  }

  valorTotalCartera(): number {
    return this.carteras().reduce((sum, c) => sum + (parseFloat(c.valorTotalMontoCartera) || 0), 0);
  }

  addCartera(): void {
    this.carteras.update(carteras => [
      ...carteras,
      { rangoEdad: '', edadCartera: '', modalidadCredito: '', codigoCategoria: '', cantCreditos: '0', valorTotalMontoCartera: '0' }
    ]);
  }

  removeCartera(index: number): void {
    if (this.carteras().length > 1) {
      this.carteras.update(carteras => carteras.filter((_, i) => i !== index));
    }
  }

  updateCartera(index: number, field: keyof CarteraEdad, value: string): void {
    this.carteras.update(carteras => {
      const updated = [...carteras];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  guardar(): void {
    const datos = {
      codigoProyecto: this.codigoProyecto(),
      carteras: this.carteras().map(c => ({
        rangoEdad: c.rangoEdad,
        edadCartera: c.edadCartera,
        modalidadCredito: c.modalidadCredito,
        codigoCategoria: c.codigoCategoria,
        cantCreditos: c.cantCreditos,
        valorTotalMontoCartera: c.valorTotalMontoCartera
      }))
    };

    this.guardando.set(true);
    this.estructuraService.guardarP024A(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-024A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.carteras.set([
      { rangoEdad: '', edadCartera: '', modalidadCredito: '', codigoCategoria: '', cantCreditos: '0', valorTotalMontoCartera: '0' }
    ]);
  }
}