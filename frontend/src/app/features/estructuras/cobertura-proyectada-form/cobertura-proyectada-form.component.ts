import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P011ACoberturaProyectada, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-cobertura-proyectada-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './cobertura-proyectada-form.component.html',
  styleUrls: ['./cobertura-proyectada-form.component.css']
})
export class CoberturaProyectadaFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P011ACoberturaProyectada = {
    codigoProyecto: '',
    numeroBeneficiarios: 0,
    tipoBeneficiario: '',
    departamento: '',
    municipio: ''
  };

  tipoBeneficiarioOptions = [
    { codigo: 'PEN', nombre: 'Pensionado' },
    { codigo: 'TRA', nombre: 'Trabajador Activo' },
    { codigo: 'BEN', nombre: 'Beneficiario' },
    { codigo: 'OTR', nombre: 'Otro' }
  ];

  departamentos = [
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
    { codigo: '88', nombre: 'Archipiélago de San Andrés' },
    { codigo: '91', nombre: 'Amazonas' },
    { codigo: '94', nombre: 'Guainía' },
    { codigo: '95', nombre: 'Guaviare' },
    { codigo: '97', nombre: 'Vaupés' },
    { codigo: '99', nombre: 'Vichada' }
  ];

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP011A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-011A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      numeroBeneficiarios: 0,
      tipoBeneficiario: '',
      departamento: '',
      municipio: ''
    };
  }
}