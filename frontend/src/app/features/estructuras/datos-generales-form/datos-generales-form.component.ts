import { Component, input, output, signal, inject, OnInit, effect } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { RichTextEditorComponent } from '../../../shared/atoms/rich-text-editor/rich-text-editor.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P001ADatosGenerales, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-datos-generales-form',
  standalone: true,
  imports: [
    FormsModule,
    ButtonComponent,
    FormFieldComponent,
    RichTextEditorComponent
  ],
  templateUrl: './datos-generales-form.component.html',
  styleUrls: ['./datos-generales-form.component.css']
})
export class DatosGeneralesFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);
  cargando = signal(false);

  datos: P001ADatosGenerales = this.inicializarDatos();

  constructor() {
    effect(() => {
      const codigo = this.codigoProyecto();
      if (codigo) {
        this.cargarDatos(codigo);
      }
    });
  }

  ngOnInit(): void {
    const codigo = this.codigoProyecto();
    if (codigo) {
      this.cargarDatos(codigo);
    }
  }

  private cargarDatos(codigo: string): void {
    this.cargando.set(true);
    this.estructuraService.obtenerP001A(codigo).subscribe({
      next: (data) => {
        if (data && typeof data === 'object') {
          this.datos = data;
        } else {
          this.datos.codigoProyecto = codigo;
        }
        this.cargando.set(false);
      },
      error: () => {
        this.datos.codigoProyecto = codigo;
        this.cargando.set(false);
      }
    });
  }

  private inicializarDatos(): P001ADatosGenerales {
    return {
      codigoProyecto: '',
      nombreProyecto: '',
      modalidadInversion: '',
      valorTotalProyecto: 0,
      valorAprobadoVigencia: 0,
      justificacion: '',
      objetivos: '',
      resolucionAEI: 2,
      numActa: '',
      numConsejeros: 7,
      tiempoRecuperacion: 0,
      tasaDescuento: ''
    };
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP001A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-001A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = this.inicializarDatos();
    this.datos.codigoProyecto = this.codigoProyecto();
  }
}