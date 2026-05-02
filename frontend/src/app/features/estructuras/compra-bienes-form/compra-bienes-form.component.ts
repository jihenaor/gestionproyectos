import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P034ACompraBienes, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-compra-bienes-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './compra-bienes-form.component.html',
  styleUrls: ['./compra-bienes-form.component.css']
})
export class CompraBienesFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P034ACompraBienes = {
    codigoProyecto: '',
    numEscritura: '',
    fechaEscritura: '',
    bienComprado: '',
    valorCompra: 0,
    fechaCertTradicionLibertad: ''
  };

  ngOnInit(): void {
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
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      numEscritura: '',
      fechaEscritura: '',
      bienComprado: '',
      valorCompra: 0,
      fechaCertTradicionLibertad: ''
    };
  }
}