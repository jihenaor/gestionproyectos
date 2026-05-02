import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P024ACarteraPorEdades, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-cartera-edades-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './cartera-edades-form.component.html',
  styleUrls: ['./cartera-edades-form.component.css']
})
export class CarteraEdadesFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P024ACarteraPorEdades = {
    codigoProyecto: '',
    edadCartera: '',
    numeroOperaciones: 0,
    valorCartera: 0,
    provision: 0
  };

  edadCarteraOptions = [
    { codigo: '0-30', nombre: '0-30 días' },
    { codigo: '31-60', nombre: '31-60 días' },
    { codigo: '61-90', nombre: '61-90 días' },
    { codigo: '91-180', nombre: '91-180 días' },
    { codigo: '181-360', nombre: '181-360 días' },
    { codigo: '>360', nombre: 'Mayor a 360 días' }
  ];

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP024A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-024A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      edadCartera: '',
      numeroOperaciones: 0,
      valorCartera: 0,
      provision: 0
    };
  }
}