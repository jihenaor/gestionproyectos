import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P026AArrendamientoBienes, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-arrendamiento-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './arrendamiento-form.component.html',
  styleUrls: ['./arrendamiento-form.component.css']
})
export class ArrendamientoFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P026AArrendamientoBienes = {
    codigoProyecto: '',
    numContrato: '',
    fechaContrato: '',
    bienArrendado: '',
    valorCanon: 0,
    plazoMeses: 0,
    fechaCertTradicionLibertad: ''
  };

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP026A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-026A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      numContrato: '',
      fechaContrato: '',
      bienArrendado: '',
      valorCanon: 0,
      plazoMeses: 0,
      fechaCertTradicionLibertad: ''
    };
  }
}