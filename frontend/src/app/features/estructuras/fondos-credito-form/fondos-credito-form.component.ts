import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P023AAspectosFondosCredito, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-fondos-credito-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './fondos-credito-form.component.html',
  styleUrls: ['./fondos-credito-form.component.css']
})
export class FondosCreditoFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P023AAspectosFondosCredito = {
    codigoProyecto: '',
    tipoCredito: '',
    numeroCreditos: 0,
    valorTotalCreditos: 0,
    plazoPromedio: 0
  };

  tipoCreditoOptions = [
    { codigo: 'COM', nombre: 'Comercial' },
    { codigo: 'FOVIS', nombre: 'FOVIS' },
    { codigo: 'MICRO', nombre: 'Microcrédito' },
    { codigo: 'OTR', nombre: 'Otro' }
  ];

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP023A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-023A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      tipoCredito: '',
      numeroCreditos: 0,
      valorTotalCreditos: 0,
      plazoPromedio: 0
    };
  }
}