import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P013AAspectosInfraestructura, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-infraestructura-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './infraestructura-form.component.html',
  styleUrls: ['./infraestructura-form.component.css']
})
export class InfraestructuraFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  modalidadInversion = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);

  datos: P013AAspectosInfraestructura = {
    codigoProyecto: '',
    modalidadInversion: '',
    licenciaConstruccion: 1,
    numLicencia: '',
    fechaLicencia: '',
    interventoria: 1,
    numActaInterventoria: '',
    fechaActaInterventoria: ''
  };

  requiereLicencia = signal(false);

  ngOnInit(): void {
    this.datos.codigoProyecto = this.codigoProyecto();
    this.datos.modalidadInversion = this.modalidadInversion();
    this.actualizarRequiereLicencia();
  }

  private actualizarRequiereLicencia(): void {
    const modalidad = this.modalidadInversion();
    this.requiereLicencia.set(modalidad === 'INF' || modalidad === 'REC');
  }

  get esInfraestructura(): boolean {
    return this.modalidadInversion() === 'INF';
  }

  get requiereDatosInterventoria(): boolean {
    return this.modalidadInversion() === 'INF' || this.modalidadInversion() === 'EDU';
  }

  guardar(): void {
    this.guardando.set(true);
    this.estructuraService.guardarP013A(this.datos).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-013A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      modalidadInversion: this.modalidadInversion(),
      licenciaConstruccion: 1,
      numLicencia: '',
      fechaLicencia: '',
      interventoria: 1,
      numActaInterventoria: '',
      fechaActaInterventoria: ''
    };
  }
}