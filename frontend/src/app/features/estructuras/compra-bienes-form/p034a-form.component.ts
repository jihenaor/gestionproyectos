import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

@Component({
  selector: 'app-p034a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-034A: Compra de Inmueble</h2>
        <p class="estructura-form__description">
          Diligencie la información de la compra del inmueble asociado al proyecto.
        </p>
      </header>

      <form class="estructura-form__form">
        <div class="form-row">
          <app-form-field [label]="'Código del Proyecto'" [fieldId]="'codigoProyecto'">
            <input type="text" id="codigoProyecto" class="form-input" [(ngModel)]="datos.codigoProyecto" disabled />
          </app-form-field>

          <app-form-field [label]="'Fecha Cert. Tradición y Libertad'" [fieldId]="'fechaCertTradicionLibertad'">
            <input type="date" id="fechaCertTradicionLibertad" class="form-input"
              [(ngModel)]="datos.fechaCertTradicionLibertad" />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Fecha del Avalúo'" [fieldId]="'fechaAvaluo'">
            <input type="date" id="fechaAvaluo" class="form-input" [(ngModel)]="datos.fechaAvaluo" />
          </app-form-field>

          <app-form-field [label]="'Perito Avalúador'" [fieldId]="'perito'">
            <input type="text" id="perito" class="form-input" [(ngModel)]="datos.perito"
              maxlength="150" placeholder="Nombre del perito" />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Valor del Avalúo'" [fieldId]="'valorAvaluo'">
            <input type="text" id="valorAvaluo" class="form-input" [(ngModel)]="datos.valorAvaluo"
              placeholder="Valor en pesos" />
          </app-form-field>

          <app-form-field [label]="'Valor de Compra'" [fieldId]="'valorCompra'">
            <input type="text" id="valorCompra" class="form-input" [(ngModel)]="datos.valorCompra"
              placeholder="Valor final de la transacción" />
          </app-form-field>
        </div>

        <app-form-field [label]="'Destinación del Inmueble'" [fieldId]="'destinacionInmueble'" [isRequired]="true">
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

        <app-form-field [label]="'Uso Autorizado'" [fieldId]="'usoAutorizado'" [isRequired]="true"
          [description]="'Describe el uso específico autorizado para el inmueble'">
          <textarea id="usoAutorizado" class="form-textarea" [(ngModel)]="datos.usoAutorizado"
            rows="3" maxlength="500"></textarea>
        </app-form-field>

        <div class="form-row">
          <app-form-field [label]="'Fecha de Escritura'" [fieldId]="'fechaEscritura'">
            <input type="date" id="fechaEscritura" class="form-input" [(ngModel)]="datos.fechaEscritura" />
          </app-form-field>

          <app-form-field [label]="'Notaría'" [fieldId]="'notaria'">
            <input type="text" id="notaria" class="form-input" [(ngModel)]="datos.notaria"
              placeholder="Número y ciudad de la notaría" maxlength="100" />
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
          Guardar P-034A
        </app-button>
      </div>
    </div>
  `,
  styles: [`
    .estructura-form { max-width: 800px; }
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
    .estructura-form__actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding-top: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
    }
  `]
})
export class P034AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  guardando = signal(false);

  datos: any = {
    codigoProyecto: '',
    fechaCertTradicionLibertad: '',
    fechaAvaluo: '',
    perito: '',
    valorAvaluo: '',
    valorCompra: '',
    destinacionInmueble: '',
    usoAutorizado: '',
    fechaEscritura: '',
    notaria: '',
    observaciones: ''
  };

  ngOnInit() {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP034A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-034A', datos: result });
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
      fechaCertTradicionLibertad: '',
      fechaAvaluo: '',
      perito: '',
      valorAvaluo: '',
      valorCompra: '',
      destinacionInmueble: '',
      usoAutorizado: '',
      fechaEscritura: '',
      notaria: '',
      observaciones: ''
    };
  }
}