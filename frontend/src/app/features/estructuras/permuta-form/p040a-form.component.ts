import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

@Component({
  selector: 'app-p040a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-040A: Permuta</h2>
        <p class="estructura-form__description">
          Diligencie la información de la permuta de inmuebles asociada al proyecto.
          Una permuta consiste en el intercambio de propiedades entre partes.
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

        <div class="permuta-seccion">
          <h3 class="permuta-title">Información del Inmueble que Recibe</h3>
          <div class="form-row">
            <app-form-field [label]="'Fecha Avalúo Recibe'" [fieldId]="'fechaAvaluoRecibe'">
              <input type="date" id="fechaAvaluoRecibe" class="form-input"
                [(ngModel)]="datos.fechaAvaluoRecibe" />
            </app-form-field>

            <app-form-field [label]="'Avalúador Recibe'" [fieldId]="'avaluadorRecibe'">
              <input type="text" id="avaluadorRecibe" class="form-input"
                [(ngModel)]="datos.avaluadorRecibe" maxlength="150" placeholder="Nombre del perito" />
            </app-form-field>
          </div>

          <div class="form-row">
            <app-form-field [label]="'Valor Avalúo Recibe'" [fieldId]="'valorAvaluoRecibe'">
              <input type="text" id="valorAvaluoRecibe" class="form-input"
                [(ngModel)]="datos.valorAvaluoRecibe" placeholder="Valor en pesos" />
            </app-form-field>
          </div>
        </div>

        <div class="permuta-seccion">
          <h3 class="permuta-title">Información del Inmueble que Entrega</h3>
          <div class="form-row">
            <app-form-field [label]="'Fecha Avalúo Entrega'" [fieldId]="'fechaAvaluoEntrega'">
              <input type="date" id="fechaAvaluoEntrega" class="form-input"
                [(ngModel)]="datos.fechaAvaluoEntrega" />
            </app-form-field>

            <app-form-field [label]="'Avalúador Entrega'" [fieldId]="'avaluadorEntrega'">
              <input type="text" id="avaluadorEntrega" class="form-input"
                [(ngModel)]="datos.avaluadorEntrega" maxlength="150" placeholder="Nombre del perito" />
            </app-form-field>
          </div>

          <div class="form-row">
            <app-form-field [label]="'Valor Avalúo Entrega'" [fieldId]="'valorAvaluoEntrega'">
              <input type="text" id="valorAvaluoEntrega" class="form-input"
                [(ngModel)]="datos.valorAvaluoEntrega" placeholder="Valor en pesos" />
            </app-form-field>
          </div>
        </div>

        <div class="form-row">
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

          <app-form-field [label]="'Uso Autorizado'" [fieldId]="'usoAutorizado'" [isRequired]="true">
            <textarea id="usoAutorizado" class="form-textarea" [(ngModel)]="datos.usoAutorizado"
              rows="2" maxlength="500"></textarea>
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Valor en Libros'" [fieldId]="'valorEnLibros'">
            <input type="text" id="valorEnLibros" class="form-input" [(ngModel)]="datos.valorEnLibros"
              placeholder="Valor contable del inmueble" />
          </app-form-field>

          <app-form-field [label]="'Utilidad o Pérdida'" [fieldId]="'utilidadPerdida'">
            <select id="utilidadPerdida" class="form-select" [(ngModel)]="datos.utilidadPerdida">
              <option value="">Seleccione...</option>
              <option value="U">U - Utilidad</option>
              <option value="P">P - Pérdida</option>
              <option value="N">N - Neutro (valores iguales)</option>
            </select>
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Origen de los Recursos'" [fieldId]="'origenRecursos'">
            <input type="text" id="origenRecursos" class="form-input" [(ngModel)]="datos.origenRecursos"
              maxlength="200" placeholder="Descripción del origen de recursos" />
          </app-form-field>

          <app-form-field [label]="'Destinación'" [fieldId]="'destinacion'">
            <input type="text" id="destinacion" class="form-input" [(ngModel)]="datos.destinacion"
              maxlength="200" placeholder="Descripción de la destinación" />
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
          Guardar P-040A
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
    .permuta-seccion {
      padding: var(--spacing-md);
      background: var(--color-background-secondary);
      border-radius: var(--radius-md);
      margin-bottom: var(--spacing-md);
    }
    .permuta-title {
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
export class P040AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  guardando = signal(false);

  datos: any = {
    codigoProyecto: '',
    fechaCertTradicionLibertad: '',
    fechaAvaluoRecibe: '',
    fechaAvaluoEntrega: '',
    avaluadorRecibe: '',
    avaluadorEntrega: '',
    valorAvaluoRecibe: '',
    valorAvaluoEntrega: '',
    destinacionInmueble: '',
    usoAutorizado: '',
    valorEnLibros: '',
    utilidadPerdida: '',
    origenRecursos: '',
    destinacion: '',
    observaciones: ''
  };

  ngOnInit() {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP040A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-040A', datos: result });
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
      fechaAvaluoRecibe: '',
      fechaAvaluoEntrega: '',
      avaluadorRecibe: '',
      avaluadorEntrega: '',
      valorAvaluoRecibe: '',
      valorAvaluoEntrega: '',
      destinacionInmueble: '',
      usoAutorizado: '',
      valorEnLibros: '',
      utilidadPerdida: '',
      origenRecursos: '',
      destinacion: '',
      observaciones: ''
    };
  }
}