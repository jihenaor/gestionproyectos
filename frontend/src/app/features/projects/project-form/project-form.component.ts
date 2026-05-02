import { Component, input, OnInit, signal, inject, computed } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { InputComponent } from '../../../shared/atoms/input/input.component';
import { SelectComponent } from '../../../shared/atoms/select/select.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { RichTextEditorComponent } from '../../../shared/atoms/rich-text-editor/rich-text-editor.component';
import { ValidationService } from '../../../core/services/validation.service';
import { ProjectStateService } from '../../../core/services/project-state.service';
import { ApiService } from '../../../core/services/api.service';

interface CronogramaActividad {
  tipoActividad: string;
  descripcion: string;
  porcentaje: string;
  fechaInicio: string;
  fechaTerminacion: string;
}

@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ButtonComponent,
    InputComponent,
    SelectComponent,
    FormFieldComponent,
    RichTextEditorComponent
  ],
  template: `
    <div class="project-form">
      <header class="project-form__header">
        <h1 class="project-form__title">{{ isEditing() ? 'Editar' : 'Nuevo' }} Proyecto</h1>
        <p class="project-form__subtitle">
          {{ isEditing() ? 'Actualice la información del proyecto' : 'Diligencie todos los campos obligatorios' }}
        </p>
      </header>

      <form [formGroup]="form" (ngSubmit)="onSubmit()" class="project-form__form">
        <section class="project-form__section">
          <h2 class="project-form__section-title">Datos Generales (P-001A)</h2>

          <div class="project-form__row">
            <app-form-field
              [label]="'Código del Proyecto'"
              [fieldId]="'codigo'"
              [fieldType]="'input'"
              [inputType]="'text'"
              [placeholder]="'CCF001-01-00001'"
              [description]="'Código único de identificación del proyecto'"
              [formatExample]="'CCFXXX-XX-XXXXX (15 caracteres)'"
              [isRequired]="true"
              [maxLength]="15"
              [showCharCount]="true"
              [tableReference]="'Tabla 32'"
              [validationRules]="['Formato: CCF + Código Caja + Modalidad + Consecutivo']"
            >
              <app-input
                [inputId]="'codigo'"
                formControlName="codigo"
                [placeholder]="'CCF001-01-00001'"
                [maxLength]="15"
                [hasError]="hasError('codigo')"
                [errorMessage]="getErrorMessage('codigo')"
              />
            </app-form-field>

            <app-form-field
              [label]="'Nombre del Proyecto'"
              [fieldId]="'nombre'"
              [fieldType]="'input'"
              [inputType]="'text'"
              [placeholder]="'Descripción clara del proyecto'"
              [description]="'Nombre o descripción que identifique el proyecto'"
              [isRequired]="true"
              [maxLength]="200"
              [showCharCount]="true"
              [validationRules]="['Mínimo 10 caracteres', 'Máximo 200 caracteres']"
            >
              <app-input
                [inputId]="'nombre'"
                formControlName="nombre"
                [placeholder]="'Construcción Centro Recreativo'"
                [maxLength]="200"
                [hasError]="hasError('nombre')"
                [errorMessage]="getErrorMessage('nombre')"
              />
            </app-form-field>
          </div>

          <div class="project-form__row">
            <app-form-field
              [label]="'Modalidad'"
              [fieldId]="'modalidad'"
              [fieldType]="'select'"
              [placeholder]="'Seleccione modalidad'"
              [tableReference]="'Tabla 32'"
              [isRequired]="true"
              [selectOptions]="modalidadOptions"
            >
              <app-select
                [selectId]="'modalidad'"
                formControlName="modalidad"
                [options]="modalidadOptions"
                [hasError]="hasError('modalidad')"
                [errorMessage]="getErrorMessage('modalidad')"
              />
            </app-form-field>

            <app-form-field
              [label]="'Modalidad de Inversión'"
              [fieldId]="'modalidadInversion'"
              [fieldType]="'select'"
              [placeholder]="'Seleccione tipo'"
              [tableReference]="'Tabla 33'"
              [isRequired]="true"
              [selectOptions]="modalidadInversionOptions"
              [description]="'Determina el tipo de proyecto y campos condicionales'"
            >
              <app-select
                [selectId]="'modalidadInversion'"
                formControlName="modalidadInversion"
                [options]="modalidadInversionOptions"
                [hasError]="hasError('modalidadInversion')"
                [errorMessage]="getErrorMessage('modalidadInversion')"
              />
            </app-form-field>
          </div>

          <div class="project-form__row">
            <app-form-field
              [label]="'Valor Total del Proyecto'"
              [fieldId]="'valorTotal'"
              [fieldType]="'input'"
              [inputType]="'number'"
              [placeholder]="'500000000'"
              [description]="'Sin puntos ni comas. Solo números'"
              [formatExample]="'500000000 (sin separadores)'"
              [isRequired]="true"
              [helperText]="'Valor en pesos colombianos sin decimales'"
            >
              <app-input
                [inputId]="'valorTotal'"
                formControlName="valorTotal"
                [type]="'number'"
                [placeholder]="'500000000'"
                [hasError]="hasError('valorTotal')"
                [errorMessage]="getErrorMessage('valorTotal')"
              />
            </app-form-field>

            <app-form-field
              [label]="'Valor Aprobado Vigencia'"
              [fieldId]="'valorAprobado'"
              [fieldType]="'input'"
              [inputType]="'number'"
              [placeholder]="'250000000'"
              [description]="'Monto aprobado para la vigencia actual'"
              [isRequired]="true"
            >
              <app-input
                [inputId]="'valorAprobado'"
                formControlName="valorAprobado"
                [type]="'number'"
                [placeholder]="'250000000'"
                [hasError]="hasError('valorAprobado')"
                [errorMessage]="getErrorMessage('valorAprobado')"
              />
            </app-form-field>
          </div>

          <div class="project-form__field-full">
            <app-form-field
              [label]="'Justificación'"
              [fieldId]="'justificacion'"
              [fieldType]="'custom'"
              [description]="'Describe la necesidad, beneficios y objetivos del proyecto. Puede usar negrita, listas y viñetas.'"
              [isRequired]="true"
              [maxLength]="4000"
              [showCharCount]="true"
              [validationRules]="['Mínimo 100 caracteres de texto', 'Máximo 4000 caracteres de texto']"
              [hasError]="hasError('justificacion')"
              [errorMessage]="getErrorMessage('justificacion')"
            >
              <app-rich-text-editor
                formControlName="justificacion"
                [maxLength]="4000"
                [showCharCount]="true"
                placeholder="Explicación detallada del proyecto..."
              />
            </app-form-field>
          </div>
        </section>

        <section class="project-form__section">
          <h2 class="project-form__section-title">P-002A: Cronograma del Proyecto</h2>
          <p class="project-form__help">
            Diligencie una fila por cada tipo de actividad planned. El cronograma define el plan de trabajo del proyecto.
          </p>

          <div class="cronograma-container">
            <div class="cronograma-header">
              <span>Tipo Actividad</span>
              <span>Descripción</span>
              <span>% Planeado</span>
              <span>Fecha Inicio</span>
              <span>Fecha Terminación</span>
              <span></span>
            </div>

            @for (actividad of cronogramaActividades(); track $index; let i = $index) {
              <div class="cronograma-row">
                <app-select
                  [selectId]="'tipoActividad-' + i"
                  [options]="tipoActividadOptions"
                  [value]="actividad.tipoActividad"
                  (valueChange)="onActividadTipoChange(i, $event)"
                />
                <app-input
                  [inputId]="'descripcion-' + i"
                  [placeholder]="'Descripción actividad'"
                  [value]="actividad.descripcion"
                  (input)="onActividadInput(i, 'descripcion', $event)"
                />
                <app-input
                  [inputId]="'porcentaje-' + i"
                  [type]="'text'"
                  [placeholder]="'25.00'"
                  [value]="actividad.porcentaje"
                  (input)="onActividadInput(i, 'porcentaje', $event)"
                />
                <app-input
                  [inputId]="'fechaInicio-' + i"
                  [type]="'date'"
                  [value]="actividad.fechaInicio"
                  (input)="onActividadInput(i, 'fechaInicio', $event)"
                />
                <app-input
                  [inputId]="'fechaTerminacion-' + i"
                  [type]="'date'"
                  [value]="actividad.fechaTerminacion"
                  (input)="onActividadInput(i, 'fechaTerminacion', $event)"
                />
                <button type="button" class="cronograma-remove" (click)="removeActividad(i)">×</button>
              </div>
            }

            <button type="button" class="cronograma-add" (click)="addActividad()">
              + Agregar Actividad
            </button>
          </div>

          <div class="project-form__row">
            <app-form-field
              [label]="'Porcentaje Total Programado'"
              [fieldId]="'porcentajeTotal'"
              [fieldType]="'input'"
              [inputType]="'text'"
              [placeholder]="'100.00'"
              [description]="'Suma de porcentajes debe ser 100'"
              [formatExample]="'XX.XX (ej: 100.00)'"
              [helperText]="'La suma de todos los porcentajes debe ser 100'"
            >
              <app-input
                [inputId]="'porcentajeTotal'"
                formControlName="porcentajeTotal"
                [placeholder]="'100.00'"
              />
            </app-form-field>
          </div>
        </section>

        <section class="project-form__section">
          <h2 class="project-form__section-title">Aspectos Específicos</h2>

          <app-form-field
            [label]="'¿Cuenta con resolución AEI?'"
            [fieldId]="'resolucionAEI'"
            [fieldType]="'select'"
            [isRequired]="true"
            [selectOptions]="[
              { value: 1, label: 'Sí' },
              { value: 2, label: 'No' }
            ]"
          >
            <app-select
              [selectId]="'resolucionAEI'"
              formControlName="resolucionAEI"
              [options]="[
                { value: 1, label: 'Sí' },
                { value: 2, label: 'No' }
              ]"
            />
          </app-form-field>

          <div class="project-form__row">
            <app-form-field
              [label]="'Tiempo de Recuperación (meses)'"
              [fieldId]="'tiempoRecuperacion'"
              [fieldType]="'input'"
              [inputType]="'number'"
              [placeholder]="'36'"
              [isRequired]="true"
              [isConditional]="true"
            >
              <app-input
                [inputId]="'tiempoRecuperacion'"
                formControlName="tiempoRecuperacion"
                [type]="'number'"
                [placeholder]="'36'"
              />
            </app-form-field>

            <app-form-field
              [label]="'Tasa Descuento'"
              [fieldId]="'tasaDescuento'"
              [fieldType]="'input'"
              [inputType]="'text'"
              [placeholder]="'12.50'"
              [description]="'Formato: XX.XX'"
              [formatExample]="'12.50'"
              [isConditional]="true"
            >
              <app-input
                [inputId]="'tasaDescuento'"
                formControlName="tasaDescuento"
                [placeholder]="'12.50'"
                [hasError]="hasError('tasaDescuento')"
              />
            </app-form-field>
          </div>
        </section>

        <div class="project-form__actions">
          <app-button variant="secondary" type="button" (click)="onCancel()">Cancelar</app-button>
          <app-button variant="primary" type="submit" [loading]="isSaving()">
            {{ isEditing() ? 'Actualizar' : 'Crear' }} Proyecto
          </app-button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .project-form {
      width: 90%;
      max-width: 100%;
      margin: 0 auto;
      box-sizing: border-box;
    }
    .project-form__header {
      margin-bottom: var(--spacing-xl);
    }
    .project-form__title {
      font-size: 1.875rem;
      font-weight: 700;
      margin: 0 0 var(--spacing-xs) 0;
    }
    .project-form__subtitle {
      color: var(--color-text-secondary);
      margin: 0;
    }
    .project-form__form {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xl);
    }
    .project-form__section {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-md);
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: var(--spacing-lg);
    }
    .project-form__section-title {
      font-size: 1.125rem;
      font-weight: 600;
      margin: 0 0 var(--spacing-lg) 0;
      padding-bottom: var(--spacing-md);
      border-bottom: 1px solid var(--color-border);
    }
    .project-form__row {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: var(--spacing-md);
      width: 100%;
      min-width: 0;
    }
    .project-form__field-full {
      width: 100%;
      min-width: 0;
      align-self: stretch;
    }
    .project-form__field-full app-form-field {
      display: block;
      width: 100%;
    }
    .project-form__field-full app-rich-text-editor {
      display: block;
      width: 100%;
    }
    .project-form__actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding-top: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
    }
    .project-form__help {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
      margin: 0 0 var(--spacing-md) 0;
    }
    .cronograma-container {
      margin-bottom: var(--spacing-lg);
    }
    .cronograma-header {
      display: grid;
      grid-template-columns: 150px 1fr 100px 130px 130px 40px;
      gap: var(--spacing-sm);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-radius: var(--radius-sm);
      font-size: 0.75rem;
      font-weight: 600;
      color: var(--color-text-secondary);
      margin-bottom: var(--spacing-sm);
    }
    .cronograma-row {
      display: grid;
      grid-template-columns: 150px 1fr 100px 130px 130px 40px;
      gap: var(--spacing-sm);
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--color-border);
      align-items: center;
    }
    .cronograma-row app-input,
    .cronograma-row app-select {
      width: 100%;
    }
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
    .cronograma-remove:hover {
      background: var(--color-error);
      color: white;
    }
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
    .cronograma-add:hover {
      border-color: var(--color-primary);
      color: var(--color-primary);
    }
  `]
})
export class ProjectFormComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly validationService = inject(ValidationService);
  private readonly projectState = inject(ProjectStateService);
  private readonly apiService = inject(ApiService);

  id = input<string>('');

  form = new FormGroup({
    codigo: new FormControl('', [Validators.required]),
    nombre: new FormControl('', [Validators.required, Validators.minLength(10)]),
    modalidad: new FormControl('', [Validators.required]),
    modalidadInversion: new FormControl('', [Validators.required]),
    valorTotal: new FormControl(0, [Validators.required, Validators.min(1)]),
    valorAprobado: new FormControl(0, [Validators.required]),
    justificacion: new FormControl('', [Validators.required, Validators.minLength(100)]),
    resolucionAEI: new FormControl(2, [Validators.required]),
    tiempoRecuperacion: new FormControl(0),
    tasaDescuento: new FormControl(''),
    porcentajeTotal: new FormControl('100.00')
  });

  cronogramaActividades = signal<CronogramaActividad[]>([
    { tipoActividad: '', descripcion: '', porcentaje: '0.00', fechaInicio: '', fechaTerminacion: '' }
  ]);

  tipoActividadOptions = [
    { value: '0501', label: '0501 - Prediseño' },
    { value: '0502', label: '0502 - Diseño' },
    { value: '0503', label: '0503 - Ejecución' },
    { value: '0504', label: '0504 - Interventoría' },
    { value: '0505', label: '0505 - Adquisición de bienes' },
    { value: '0506', label: '0506 - Adecuación de bienes' },
    { value: '0507', label: '0507 - Mantenimiento de bienes' },
    { value: '0508', label: '0508 - Dotación' },
    { value: '0509', label: '0509 - Otros' },
    { value: '0601', label: '0601 - Capacitación' },
    { value: '0602', label: '0602 - Evaluar' },
    { value: '0603', label: '0603 - Certificar' },
    { value: '0401', label: '0401 - Desembolso crédito social' },
    { value: '0402', label: '0402 - Desembolso microcrédito' },
    { value: '0403', label: '0403 - Desembolso crédito constructor' },
    { value: '0404', label: '0404 - Pago honorarios' },
    { value: '0405', label: '0405 - Pago nómina' },
    { value: '0406', label: '0406 - Pago proveedores' }
  ];

  addActividad(): void {
    this.cronogramaActividades.update(acts => [
      ...acts,
      { tipoActividad: '', descripcion: '', porcentaje: '0.00', fechaInicio: '', fechaTerminacion: '' }
    ]);
  }

  removeActividad(index: number): void {
    if (this.cronogramaActividades().length > 1) {
      this.cronogramaActividades.update(acts => acts.filter((_, i) => i !== index));
    }
  }

  updateActividad(index: number, field: keyof CronogramaActividad, value: string): void {
    this.cronogramaActividades.update(acts => {
      const updated = [...acts];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  }

  getCronogramaData(): any[] {
    return this.cronogramaActividades().map(act => ({
      tipoActividad: act.tipoActividad,
      descripcion: act.descripcion,
      porcentaje: act.porcentaje,
      fechaInicio: this.formatDateToAAAAMMDD(act.fechaInicio),
      fechaTerminacion: this.formatDateToAAAAMMDD(act.fechaTerminacion)
    }));
  }

  formatDateToAAAAMMDD(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}${month}${day}`;
  }

  onActividadTipoChange(index: number, value: string | number): void {
    this.updateActividad(index, 'tipoActividad', String(value));
  }

  onActividadInput(index: number, field: keyof CronogramaActividad, event: Event): void {
    const target = event.target as HTMLInputElement;
    this.updateActividad(index, field, target.value);
  }

  isSaving = signal(false);

  modalidadOptions = [
    { value: '1', label: 'Infraestructura' },
    { value: '2', label: 'Fondo de Crédito' },
    { value: '3', label: 'Fondo de Educación' },
    { value: '4', label: 'Fondo de Recreación' },
    { value: '5', label: 'Otros' }
  ];

  modalidadInversionOptions = [
    { value: 'INF', label: 'Infraestructura' },
    { value: 'FON', label: 'Fondo' },
    { value: 'EDU', label: 'Educación' },
    { value: 'REC', label: 'Recreación' }
  ];

  isEditing = computed(() => !!this.id());

  ngOnInit(): void {
    if (this.isEditing()) {
      this.loadProject();
    }
  }

  private loadProject(): void {
    const project = this.projectState.getProjectById(this.id());
    if (project) {
      this.form.patchValue({
        codigo: project.codigo,
        nombre: project.nombre,
        modalidad: project.modalidad,
        modalidadInversion: project.modalidadInversion,
        valorTotal: project.valorTotal,
        justificacion: ''
      });
    }
  }

  hasError(field: string): boolean {
    const control = this.form.get(field);
    return !!(control && control.invalid && control.touched);
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (!control || !control.errors) return '';

    const errors = control.errors;
    if (errors['required']) return 'Este campo es obligatorio';
    if (errors['minlength']) return `Mínimo ${errors['minlength'].requiredLength} caracteres`;
    if (errors['min']) return 'El valor debe ser mayor a 0';

    return 'Campo inválido';
  }

  onSubmit(): void {
    if (this.form.invalid) {
      Object.values(this.form.controls).forEach(control => {
        control.markAsTouched();
      });
      return;
    }

    this.isSaving.set(true);

    const formValue = this.form.value;

    if (this.isEditing()) {
      const updateCommand = {
        nombre: formValue.nombre || '',
        modalidadInversion: formValue.modalidadInversion || '',
        valorTotal: formValue.valorTotal || 0,
        valorAprobado: formValue.valorAprobado || 0,
        justificacion: formValue.justificacion || ''
      };
      this.apiService.updateProyecto(this.id(), updateCommand).subscribe({
        next: () => {
          this.isSaving.set(false);
          this.router.navigate(['/projects', this.id()]);
        },
        error: () => {
          this.isSaving.set(false);
        }
      });
    } else {
      const createCommand = {
        codigo: formValue.codigo || '',
        nombre: formValue.nombre || '',
        modalidadInversion: formValue.modalidadInversion || '',
        valorTotal: formValue.valorTotal || 0,
        valorAprobado: formValue.valorAprobado || 0,
        justificacion: formValue.justificacion || ''
      };
      this.apiService.createProyecto(createCommand).subscribe({
        next: (response) => {
          this.isSaving.set(false);
          this.router.navigate(['/projects', response.id]);
        },
        error: () => {
          this.isSaving.set(false);
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/projects']);
  }
}