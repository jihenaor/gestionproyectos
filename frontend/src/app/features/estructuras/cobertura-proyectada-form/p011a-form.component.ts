import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

interface Cobertura {
  codigoCategoria: string;
  cantidadBeneficiarios: string;
  valorPerCapita: string;
  anio1: string;
  anio2: string;
  anio3: string;
  anio4: string;
  anio5: string;
}

@Component({
  selector: 'app-p011a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-011A: Cobertura Proyectada</h2>
        <p class="estructura-form__description">
          Diligencie las categorías de cobertura con la proyección a 5 años. Cada fila representa
          una categoría de beneficiarios.
        </p>
      </header>

      <div class="cobertura-tabla">
        <div class="cobertura-header">
          <span>Código Categoría *</span>
          <span>Cant. Beneficiarios *</span>
          <span>Valor Per Cápita *</span>
          <span>Año 1</span>
          <span>Año 2</span>
          <span>Año 3</span>
          <span>Año 4</span>
          <span>Año 5</span>
          <span></span>
        </div>

        @for (cobertura of coberturas(); track $index; let i = $index) {
          <div class="cobertura-row">
            <select
              class="cobertura-select"
              [ngModel]="cobertura.codigoCategoria"
              (ngModelChange)="updateCobertura(i, 'codigoCategoria', $event)">
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
              <option value="0205">0205 - Población indigena</option>
            </select>
            <input
              type="text"
              class="cobertura-input"
              placeholder="Cantidad"
              [ngModel]="cobertura.cantidadBeneficiarios"
              (ngModelChange)="updateCobertura(i, 'cantidadBeneficiarios', $event)"
            />
            <input
              type="text"
              class="cobertura-input"
              placeholder="Valor"
              [ngModel]="cobertura.valorPerCapita"
              (ngModelChange)="updateCobertura(i, 'valorPerCapita', $event)"
            />
            <input
              type="text"
              class="cobertura-input anio"
              placeholder="Año 1"
              [ngModel]="cobertura.anio1"
              (ngModelChange)="updateCobertura(i, 'anio1', $event)"
            />
            <input
              type="text"
              class="cobertura-input anio"
              placeholder="Año 2"
              [ngModel]="cobertura.anio2"
              (ngModelChange)="updateCobertura(i, 'anio2', $event)"
            />
            <input
              type="text"
              class="cobertura-input anio"
              placeholder="Año 3"
              [ngModel]="cobertura.anio3"
              (ngModelChange)="updateCobertura(i, 'anio3', $event)"
            />
            <input
              type="text"
              class="cobertura-input anio"
              placeholder="Año 4"
              [ngModel]="cobertura.anio4"
              (ngModelChange)="updateCobertura(i, 'anio4', $event)"
            />
            <input
              type="text"
              class="cobertura-input anio"
              placeholder="Año 5"
              [ngModel]="cobertura.anio5"
              (ngModelChange)="updateCobertura(i, 'anio5', $event)"
            />
            <button type="button" class="cobertura-remove" (click)="removeCobertura(i)">×</button>
          </div>
        }

        <button type="button" class="cobertura-add" (click)="addCobertura()">
          + Agregar Categoría
        </button>
      </div>

      <div class="estructura-form__resumen">
        <div class="resumen-item">
          <span class="resumen-label">Total Categorías:</span>
          <span class="resumen-value">{{ coberturas().length }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Total Beneficiarios:</span>
          <span class="resumen-value">{{ totalBeneficiarios() | number }}</span>
        </div>
        <div class="resumen-item">
          <span class="resumen-label">Valor Total Proyectado:</span>
          <span class="resumen-value">{{ valorTotalProyectado() | number }}</span>
        </div>
      </div>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-011A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 1400px; }
    .estructura-form__header { margin-bottom: var(--spacing-lg); }
    .estructura-form__title { font-size: 1.25rem; font-weight: 600; margin: 0 0 var(--spacing-xs) 0; }
    .estructura-form__description { color: var(--color-text-secondary); margin: 0; font-size: 0.875rem; }
    .cobertura-tabla { margin-bottom: var(--spacing-lg); }
    .cobertura-header {
      display: grid;
      grid-template-columns: 150px 100px 120px 90px 90px 90px 90px 90px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.7rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-xs);
    }
    .cobertura-row {
      display: grid;
      grid-template-columns: 150px 100px 120px 90px 90px 90px 90px 90px 40px;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .cobertura-input, .cobertura-select {
      padding: var(--spacing-sm);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      font-size: 0.875rem;
      width: 100%;
    }
    .cobertura-input:focus, .cobertura-select:focus {
      outline: none;
      border-color: var(--color-primary);
    }
    .cobertura-input.anio { text-align: right; }
    .cobertura-remove {
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
    .cobertura-remove:hover { background: var(--color-error); color: white; }
    .cobertura-add {
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
    .cobertura-add:hover { border-color: var(--color-primary); color: var(--color-primary); }
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
export class P011AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  coberturas = signal<Cobertura[]>([
    { codigoCategoria: '', cantidadBeneficiarios: '0', valorPerCapita: '0', anio1: '0', anio2: '0', anio3: '0', anio4: '0', anio5: '0' }
  ]);

  guardando = signal(false);

  totalBeneficiarios(): number {
    return this.coberturas().reduce((sum, c) => {
      return sum + (parseInt(c.cantidadBeneficiarios) || 0);
    }, 0);
  }

  valorTotalProyectado(): number {
    return this.coberturas().reduce((sum, c) => {
      const cantidad = parseInt(c.cantidadBeneficiarios) || 0;
      const perCapita = parseFloat(c.valorPerCapita) || 0;
      const anios = ['anio1', 'anio2', 'anio3', 'anio4', 'anio5'].reduce((s, key) => {
        return s + (parseFloat((c as any)[key]) || 0);
      }, 0);
      return sum + (cantidad * perCapita * anios);
    }, 0);
  }

  addCobertura(): void {
    this.coberturas.update(coberturas => [
      ...coberturas,
      { codigoCategoria: '', cantidadBeneficiarios: '0', valorPerCapita: '0', anio1: '0', anio2: '0', anio3: '0', anio4: '0', anio5: '0' }
    ]);
  }

  removeCobertura(index: number): void {
    if (this.coberturas().length > 1) {
      this.coberturas.update(coberturas => coberturas.filter((_, i) => i !== index));
    }
  }

  updateCobertura(index: number, field: keyof Cobertura, value: string): void {
    this.coberturas.update(coberturas => {
      const updated = [...coberturas];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  guardar(): void {
    const datos = {
      codigoProyecto: this.codigoProyecto(),
      coberturas: this.coberturas().map(c => ({
        codigoCategoria: c.codigoCategoria,
        cantidadBeneficiarios: c.cantidadBeneficiarios,
        valorPerCapita: c.valorPerCapita,
        anio1: c.anio1,
        anio2: c.anio2,
        anio3: c.anio3,
        anio4: c.anio4,
        anio5: c.anio5
      }))
    };

    this.guardando.set(true);
    this.estructuraService.guardarP011A(datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-011A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
        alert('Error al guardar');
      }
    });
  }

  cancelar(): void {
    this.coberturas.set([
      { codigoCategoria: '', cantidadBeneficiarios: '0', valorPerCapita: '0', anio1: '0', anio2: '0', anio3: '0', anio4: '0', anio5: '0' }
    ]);
  }
}