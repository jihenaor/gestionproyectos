import { Component, input, output, signal, inject, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/atoms/button/button.component';
import { FormFieldComponent } from '../../../shared/molecules/form-field/form-field.component';
import { EstructuraService } from '../../../core/services/estructura.service';
import { P003ALocalizacion, EstructuraGuardadaEvent } from '../../../core/models/estructura.model';

@Component({
  selector: 'app-localizacion-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonComponent, FormFieldComponent],
  templateUrl: './localizacion-form.component.html',
  styleUrls: ['./localizacion-form.component.css']
})
export class LocalizacionFormComponent implements OnInit {
  codigoProyecto = input<string>('');
  guardado = output<EstructuraGuardadaEvent>();

  private readonly estructuraService = inject(EstructuraService);
  guardando = signal(false);
  cargando = signal(false);

  datos: {
    codigoProyecto: string;
    departamento: string;
    municipio: string;
    direccion: string;
    barrio: string;
    telefono: string;
    contacto: string;
    fechaInicioOperacion: string;
    coordenadas: string;
  } = {
    codigoProyecto: '',
    departamento: '',
    municipio: '',
    direccion: '',
    barrio: '',
    telefono: '',
    contacto: '',
    fechaInicioOperacion: '',
    coordenadas: ''
  };

  departamentos = [
    { codigo: '66', nombre: 'Risaralda' },
    { codigo: '17', nombre: 'Caldas' },
    { codigo: '63', nombre: 'Quindío' },
    { codigo: '91', nombre: 'Amazonas' },
    { codigo: '05', nombre: 'Antioquia' },
    { codigo: '81', nombre: 'Arauca' },
    { codigo: '88', nombre: 'Archipiélago de San Andrés' },
    { codigo: '08', nombre: 'Atlántico' },
    { codigo: '13', nombre: 'Bolívar' },
    { codigo: '11', nombre: 'Bogotá D.C.' },
    { codigo: '15', nombre: 'Boyacá' },
    { codigo: '18', nombre: 'Caquetá' },
    { codigo: '85', nombre: 'Casanare' },
    { codigo: '19', nombre: 'Cauca' },
    { codigo: '20', nombre: 'Cesar' },
    { codigo: '27', nombre: 'Chocó' },
    { codigo: '23', nombre: 'Córdoba' },
    { codigo: '25', nombre: 'Cundinamarca' },
    { codigo: '94', nombre: 'Guainía' },
    { codigo: '95', nombre: 'Guaviare' },
    { codigo: '41', nombre: 'Huila' },
    { codigo: '44', nombre: 'La Guajira' },
    { codigo: '47', nombre: 'Magdalena' },
    { codigo: '50', nombre: 'Meta' },
    { codigo: '52', nombre: 'Nariño' },
    { codigo: '54', nombre: 'Norte de Santander' },
    { codigo: '86', nombre: 'Putumayo' },
    { codigo: '68', nombre: 'Santander' },
    { codigo: '70', nombre: 'Sucre' },
    { codigo: '73', nombre: 'Tolima' },
    { codigo: '76', nombre: 'Valle del Cauca' },
    { codigo: '97', nombre: 'Vaupés' },
    { codigo: '99', nombre: 'Vichada' }
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
    }
  }

  private cargarDatos(codigo: string): void {
    this.cargando.set(true);
    this.estructuraService.obtenerP003A(codigo).subscribe({
      next: (data) => {
        if (data && data.departamento) {
          this.datos.departamento = data.departamento || '';
          this.datos.municipio = data.municipio || '';
          this.datos.direccion = data.direccion || '';
          this.datos.barrio = data.barrio || '';
          this.datos.telefono = data.telefono || '';
          this.datos.contacto = data.contacto || '';
          this.datos.fechaInicioOperacion = data.fechaInicioOperacion || '';
          if (data.latitude != null && data.longitude != null) {
            this.datos.coordenadas = `${data.latitude},${data.longitude}`;
          } else {
            this.datos.coordenadas = '';
          }
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

  guardar(): void {
    this.guardando.set(true);
    let lat: number | undefined;
    let lng: number | undefined;
    if (this.datos.coordenadas) {
      const parts = this.datos.coordenadas.split(',');
      if (parts.length === 2) {
        lat = parseFloat(parts[0].trim());
        lng = parseFloat(parts[1].trim());
        if (isNaN(lat)) lat = undefined;
        if (isNaN(lng)) lng = undefined;
      }
    }
    const datosApi: P003ALocalizacion = {
      codigoProyecto: this.datos.codigoProyecto,
      departamento: this.datos.departamento,
      municipio: this.datos.municipio,
      direccion: this.datos.direccion || undefined,
      barrio: this.datos.barrio || undefined,
      telefono: this.datos.telefono || undefined,
      contacto: this.datos.contacto || undefined,
      fechaInicioOperacion: this.datos.fechaInicioOperacion || undefined,
      latitude: lat,
      longitude: lng
    };
    this.estructuraService.guardarP003A(datosApi).subscribe({
      next: (result) => {
        this.guardando.set(false);
        this.guardado.emit({ estructura: 'P-003A', datos: result });
      },
      error: () => {
        this.guardando.set(false);
      }
    });
  }

  cancelar(): void {
    this.datos = {
      codigoProyecto: this.codigoProyecto(),
      departamento: '',
      municipio: '',
      direccion: '',
      barrio: '',
      telefono: '',
      contacto: '',
      fechaInicioOperacion: '',
      coordenadas: ''
    };
  }
}