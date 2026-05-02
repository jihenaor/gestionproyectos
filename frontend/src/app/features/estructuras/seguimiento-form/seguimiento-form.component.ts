import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P012ASeguimientoProyecto, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-seguimiento-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './seguimiento-form.component.html',
  styleUrls: ['./seguimiento-form.component.css']
})
export class SeguimientoFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P012ASeguimientoProyecto = {
    codigoProyecto: '',
    estado: '',
    avancePorcentaje: 0,
    observaciones: '',
    fechaSeguimiento: ''
  };

  estadoOptions = [
    { codigo: 'EJP', nombre: 'En Progreso' },
    { codigo: 'PEN', nombre: 'Pendiente' },
    { codigo: 'COM', nombre: 'Completado' },
    { codigo: 'CAN', nombre: 'Cancelado' }
  ];

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP012A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-012A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      estado: '',
      avancePorcentaje: 0,
      observaciones: '',
      fechaSeguimiento: ''
    };
  }
}