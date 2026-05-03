import { Component, input, output, signal, inject, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P004CEstructuraFuenteRecursos, P004CRecurso, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-fuentes-recursos-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './fuentes-recursos-form.component.html',
  styleUrls: ['./fuentes-recursos-form.component.css']
})
export class FuentesRecursosFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);
  cargando = signal(false);

  datos: { codigoProyecto: string; recursos: P004CRecurso[] } = {
    codigoProyecto: '',
    recursos: []
  };

  tiposFuente = [
    { codigo: '01', nombre: '01 - Recursos Propios CCF' },
    { codigo: '02', nombre: '02 - Aportes Patronales' },
    { codigo: '03', nombre: '03 - Aportes Trabajadores' },
    { codigo: '04', nombre: '04 - Recursos del Estado' },
    { codigo: '05', nombre: '05 - Créditos' },
    { codigo: '06', nombre: '06 - Donaciones' },
    { codigo: '07', nombre: '07 - Otros' }
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
      this.agregarRecurso();
    }
  }

  private cargarDatos(codigo: string): void {
    this.cargando.set(true);
    this.estructuraService.obtenerP004C(codigo).subscribe({
      next: (data) => {
        if (data && data.recursos && data.recursos.length > 0) {
          this.datos.recursos = data.recursos;
        } else {
          this.datos.codigoProyecto = codigo;
          this.agregarRecurso();
        }
        this.cargando.set(false);
      },
      error: () => {
        this.datos.codigoProyecto = codigo;
        this.agregarRecurso();
        this.cargando.set(false);
      }
    });
  }

  agregarRecurso(): void {
    if (this.datos.recursos.length < 10) {
      this.datos.recursos.push({
        codigoFuente: Date.now().toString(),
        tipo: '',
        fuente: '',
        valor: 0
      });
    }
  }

  eliminarRecurso(index: number): void {
    if (this.datos.recursos.length > 1) {
      this.datos.recursos.splice(index, 1);
      this.actualizarValorTotal();
    }
  }

  actualizarValorTotal(): void {
  }

  get sumaValores(): number {
    return this.datos.recursos.reduce((sum, r) => sum + (r.valor || 0), 0);
  }

  get valorTotal(): number {
    return this.sumaValores;
  }

  guardar(): void {
    this.guardando.set(true);

    const fuentesRecursos: P004CEstructuraFuenteRecursos = {
      codigoProyecto: this.datos.codigoProyecto,
      recursos: this.datos.recursos,
      valorTotal: this.sumaValores
    };

    this.estructuraService.guardarP004C(fuentesRecursos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-004C', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos.recursos = [{
      codigoFuente: Date.now().toString(),
      tipo: '',
      fuente: '',
      valor: 0
    }];
  }
}