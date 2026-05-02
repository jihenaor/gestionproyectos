import { Component, input, output, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';

const DEPARTAMENTOS_COLOMBIA = [
  { codigo: '05', nombre: 'Antioquia' },
  { codigo: '08', nombre: 'Atlántico' },
  { codigo: '11', nombre: 'Bogotá D.C.' },
  { codigo: '13', nombre: 'Bolívar' },
  { codigo: '15', nombre: 'Boyacá' },
  { codigo: '17', nombre: 'Caldas' },
  { codigo: '18', nombre: 'Caquetá' },
  { codigo: '19', nombre: 'Cauca' },
  { codigo: '20', nombre: 'Cesar' },
  { codigo: '23', nombre: 'Córdoba' },
  { codigo: '25', nombre: 'Cundinamarca' },
  { codigo: '27', nombre: 'Chocó' },
  { codigo: '41', nombre: 'Huila' },
  { codigo: '44', nombre: 'La Guajira' },
  { codigo: '47', nombre: 'Magdalena' },
  { codigo: '50', nombre: 'Meta' },
  { codigo: '52', nombre: 'Nariño' },
  { codigo: '54', nombre: 'Norte de Santander' },
  { codigo: '63', nombre: 'Quindío' },
  { codigo: '66', nombre: 'Risaralda' },
  { codigo: '68', nombre: 'Santander' },
  { codigo: '70', nombre: 'Sucre' },
  { codigo: '73', nombre: 'Tolima' },
  { codigo: '76', nombre: 'Valle del Cauca' },
  { codigo: '81', nombre: 'Arauca' },
  { codigo: '85', nombre: 'Casanare' },
  { codigo: '86', nombre: 'Putumayo' },
  { codigo: '88', nombre: 'San Andrés y Providencia' },
  { codigo: '91', nombre: 'Amazonas' },
  { codigo: '94', nombre: 'Guainía' },
  { codigo: '95', nombre: 'Guaviare' },
  { codigo: '97', nombre: 'Vaupés' },
  { codigo: '99', nombre: 'Vichada' }
];

const MUNICIPIOS: { [key: string]: { codigo: string; nombre: string }[] } = {
  '05': [
    { codigo: '05001', nombre: 'Medellín' },
    { codigo: '05004', nombre: 'Bello' },
    { codigo: '05002', nombre: 'Envigado' },
    { codigo: '05003', nombre: 'Itagüí' },
    { codigo: '05031', nombre: 'Rionegro' }
  ],
  '08': [
    { codigo: '08001', nombre: 'Barranquilla' },
    { codigo: '08002', nombre: 'Soledad' },
    { codigo: '08003', nombre: 'Cartagena' }
  ],
  '11': [
    { codigo: '11001', nombre: 'Bogotá D.C.' }
  ],
  '13': [
    { codigo: '13001', nombre: 'Cartagena' },
    { codigo: '13006', nombre: 'Barrancabermeja' }
  ],
  '15': [
    { codigo: '15001', nombre: 'Tunja' },
    { codigo: '15022', nombre: 'Sogamoso' }
  ],
  '17': [
    { codigo: '17001', nombre: 'Manizales' }
  ],
  '41': [
    { codigo: '41001', nombre: 'Neiva' }
  ],
  '68': [
    { codigo: '68001', nombre: 'Bucaramanga' },
    { codigo: '68020', nombre: 'Floridablanca' }
  ],
  '76': [
    { codigo: '76001', nombre: 'Cali' },
    { codigo: '76109', nombre: 'Palmira' }
  ]
};

@Component({
  selector: 'app-p003a-form',
  standalone: true,
  imports: [FormsModule, ButtonComponent, InputComponent, SelectComponent, FormFieldComponent],
  template: `
    <div class="estructura-form">
      <header class="estructura-form__header">
        <h2 class="estructura-form__title">P-003A: Localización del Proyecto</h2>
        <p class="estructura-form__description">
          Diligencie la información de localización del proyecto. Los campos departamento y municipio
          son obligatorios para proyectos de infraestructura.
        </p>
      </header>

      <form class="estructura-form__form">
        <div class="form-row">
          <app-form-field [label]="'Código del Proyecto'" [fieldId]="'codigoProyecto'">
            <input type="text" id="codigoProyecto" class="form-input" [(ngModel)]="datos.codigoProyecto" disabled />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Departamento'" [fieldId]="'departamento'" [isRequired]="true">
            <select id="departamento" class="form-select" [(ngModel)]="datos.departamento" (ngModelChange)="onDepartamentoChange($event)">
              <option value="">Seleccione...</option>
              @for (dept of departamentos; track dept.codigo) {
                <option [value]="dept.codigo">{{ dept.codigo }} - {{ dept.nombre }}</option>
              }
            </select>
          </app-form-field>

          <app-form-field [label]="'Municipio'" [fieldId]="'municipio'" [isRequired]="true">
            <select id="municipio" class="form-select" [(ngModel)]="datos.municipio" [disabled]="!datos.departamento">
              <option value="">Seleccione...</option>
              @if (datos.departamento && municipiosDisponibles().length > 0) {
                @for (mun of municipiosDisponibles(); track mun.codigo) {
                  <option [value]="mun.codigo">{{ mun.codigo }} - {{ mun.nombre }}</option>
                }
              }
            </select>
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Dirección'" [fieldId]="'direccion'">
            <input type="text" id="direccion" class="form-input" [(ngModel)]="datos.direccion" maxlength="200" />
          </app-form-field>

          <app-form-field [label]="'Barrio'" [fieldId]="'barrio'">
            <input type="text" id="barrio" class="form-input" [(ngModel)]="datos.barrio" maxlength="100" />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Teléfono de Contacto'" [fieldId]="'telefono'">
            <input type="text" id="telefono" class="form-input" [(ngModel)]="datos.telefono" maxlength="20" placeholder="(57) XXX XXXX" />
          </app-form-field>

          <app-form-field [label]="'Persona de Contacto'" [fieldId]="'contacto'">
            <input type="text" id="contacto" class="form-input" [(ngModel)]="datos.contacto" maxlength="150" />
          </app-form-field>
        </div>

        <div class="form-row">
          <app-form-field [label]="'Fecha Inicio de Operación'" [fieldId]="'fechaInicioOperacion'">
            <input type="date" id="fechaInicioOperacion" class="form-input" [(ngModel)]="datos.fechaInicioOperacion" />
          </app-form-field>
        </div>
      </form>

      <div class="estructura-form__actions">
        <app-button variant="secondary" type="button" (click)="cancelar()">Cancelar</app-button>
        <app-button variant="primary" type="button" (click)="guardar()" [loading]="guardando()">
          Guardar P-003A
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
    .form-input:disabled { background: var(--color-background-secondary); cursor: not-allowed; }
    .estructura-form__actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding-top: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
    }
  `]
})
export class P003AFormComponent {
  codigoProyecto = input<string>('');
  guardado = output<any>();

  private estructuraService = inject(EstructuraService);

  departamentos = DEPARTAMENTOS_COLOMBIA;
  municipiosDisponibles = signal<{ codigo: string; nombre: string }[]>([]);

  guardando = signal(false);

  datos: any = {
    codigoProyecto: '',
    departamento: '',
    municipio: '',
    direccion: '',
    barrio: '',
    telefono: '',
    contacto: '',
    fechaInicioOperacion: ''
  };

  ngOnInit() {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  onDepartamentoChange(codigo: string): void {
    this.datos.municipio = '';
    if (codigo && MUNICIPIOS[codigo]) {
      this.municipiosDisponibles.set(MUNICIPIOS[codigo]);
    } else {
      this.municipiosDisponibles.set([]);
    }
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP003A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-003A', datos: result });
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
      departamento: '',
      municipio: '',
      direccion: '',
      barrio: '',
      telefono: '',
      contacto: '',
      fechaInicioOperacion: ''
    };
  }
}