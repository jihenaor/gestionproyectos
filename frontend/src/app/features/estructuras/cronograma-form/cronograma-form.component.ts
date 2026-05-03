import { Component, input, output, signal, inject, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P002ACronograma, P002ACronogramaActividad, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

interface ActividadForm {
  tipoActividad: string;
  descripcion: string;
  porcentaje: number;
  fechaInicio: string;
  fechaTerminacion: string;
}

@Component({
  selector: 'app-cronograma-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './cronograma-form.component.html',
  styleUrls: ['./cronograma-form.component.css']
})
export class CronogramaFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);
  cargando = signal(false);

  datos: { codigoProyecto: string; actividades: ActividadForm[] } = {
    codigoProyecto: '',
    actividades: []
  };

  tiposActividad = [
    { codigo: '01', descripcion: '01 - Construcción obras' },
    { codigo: '02', descripcion: '02 - Estudios diseño' },
    { codigo: '03', descripcion: '03 - Estudios prefactibilidad' },
    { codigo: '04', descripcion: '04 - Estudios factibilidad' },
    { codigo: '05', descripcion: '05 - Estudios impacto ambiental' },
    { codigo: '06', descripcion: '06 - Estudios ingeniería' },
    { codigo: '07', descripcion: '07 - Estudios económicos' },
    { codigo: '08', descripcion: '08 - Licencias permisos' },
    { codigo: '09', descripcion: '09 - Otros estudios' },
    { codigo: '10', descripcion: '10 - Instalaciones' },
    { codigo: '11', descripcion: '11 - Equipos' },
    { codigo: '12', descripcion: '12 - Capacitación' }
  ];

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
    } else {
      this.agregarActividad();
    }
  }

  private cargarDatos(codigo: string): void {
    this.cargando.set(true);
    this.estructuraService.obtenerP002A(codigo).subscribe({
      next: (data) => {
        if (data && data.actividades && data.actividades.length > 0) {
          this.datos.actividades = data.actividades.map((act: P002ACronogramaActividad) => ({
            tipoActividad: act.tipoActividad || '',
            descripcion: act.descripcionActividad || (act as any).descripcion || '',
            porcentaje: act.porcentajeProyectado ? parseFloat(act.porcentajeProyectado) : (act as any).porcentaje || 0,
            fechaInicio: act.fechaInicio || '',
            fechaTerminacion: act.fechaTerminacion || ''
          }));
        } else {
          this.datos.codigoProyecto = codigo;
          this.agregarActividad();
        }
        this.cargando.set(false);
      },
      error: () => {
        this.datos.codigoProyecto = codigo;
        this.agregarActividad();
        this.cargando.set(false);
      }
    });
  }

  agregarActividad(): void {
    if (this.datos.actividades.length < 20) {
      this.datos.actividades.push({
        tipoActividad: '',
        descripcion: '',
        porcentaje: 0,
        fechaInicio: '',
        fechaTerminacion: ''
      });
    }
  }

  eliminarActividad(index: number): void {
    if (this.datos.actividades.length > 1) {
      this.datos.actividades.splice(index, 1);
    }
  }

  get sumaPorcentajes(): number {
    return this.datos.actividades.reduce((sum, act) => sum + (act.porcentaje || 0), 0);
  }

  get porcentajeRestante(): number {
    return 100 - this.sumaPorcentajes;
  }

  guardar(): void {
    if (Math.abs(this.sumaPorcentajes - 100) > 0.01) {
      alert(`La suma de porcentajes debe ser 100%. Actualmente es ${this.sumaPorcentajes.toFixed(2)}%.`);
      return;
    }

    const actividades: P002ACronogramaActividad[] = this.datos.actividades.map(act => ({
      tipoActividad: act.tipoActividad,
      descripcionActividad: act.descripcion,
      porcentajeProyectado: act.porcentaje.toFixed(2),
      fechaInicio: act.fechaInicio,
      fechaTerminacion: act.fechaTerminacion,
      unidadMedida: undefined,
      cantidadProgramada: undefined
    }));

    const cronograma: P002ACronograma = {
      codigoProyecto: this.datos.codigoProyecto,
      actividades: actividades,
      porcentajeTotal: this.sumaPorcentajes.toFixed(2)
    };

    this.guardando.set(true);
    this.estructuraService.guardarP002A(cronograma).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-002A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos.actividades = [{
      tipoActividad: '',
      descripcion: '',
      porcentaje: 0,
      fechaInicio: '',
      fechaTerminacion: ''
    }];
  }
}