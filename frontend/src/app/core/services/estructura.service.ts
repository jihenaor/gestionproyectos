import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  P001ADatosGenerales,
  P002ACronograma,
  P003ALocalizacion,
  P004CEstructuraFuenteRecursos,
  P011ACoberturaProyectada,
  P011BCoberturaEjecutada,
  P012ASeguimientoProyecto,
  P013AAspectosInfraestructura,
  P023AAspectosFondosCredito,
  P024ACarteraPorEdades,
  P026AArrendamientoBienes,
  P031AComodatoBienes,
  P034ACompraBienes,
  P040APermutaBienes,
  P050ANegociacionAcciones,
  P055ACapitalizaciones,
  EstructurasResponse
} from '../models/estructura.model';

@Injectable({ providedIn: 'root' })
export class EstructuraService {
  private readonly baseUrl = '/api/v1/estructuras';
  private readonly http = inject(HttpClient);

  guardarP001A(datos: P001ADatosGenerales): Observable<P001ADatosGenerales> {
    return this.http.post<P001ADatosGenerales>(`${this.baseUrl}/P-001A`, datos);
  }

  guardarP002A(datos: P002ACronograma): Observable<P002ACronograma> {
    return this.http.post<P002ACronograma>(`${this.baseUrl}/P-002A`, datos);
  }

  guardarP003A(datos: P003ALocalizacion): Observable<P003ALocalizacion> {
    return this.http.post<P003ALocalizacion>(`${this.baseUrl}/P-003A`, datos);
  }

  guardarP004C(datos: P004CEstructuraFuenteRecursos): Observable<P004CEstructuraFuenteRecursos> {
    return this.http.post<P004CEstructuraFuenteRecursos>(`${this.baseUrl}/P-004C`, datos);
  }

  guardarP011A(datos: P011ACoberturaProyectada): Observable<P011ACoberturaProyectada> {
    return this.http.post<P011ACoberturaProyectada>(`${this.baseUrl}/P-011A`, datos);
  }

  guardarP011B(datos: P011BCoberturaEjecutada): Observable<P011BCoberturaEjecutada> {
    return this.http.post<P011BCoberturaEjecutada>(`${this.baseUrl}/P-011B`, datos);
  }

  guardarP012A(datos: P012ASeguimientoProyecto): Observable<P012ASeguimientoProyecto> {
    return this.http.post<P012ASeguimientoProyecto>(`${this.baseUrl}/P-012A`, datos);
  }

  guardarP013A(datos: P013AAspectosInfraestructura): Observable<P013AAspectosInfraestructura> {
    return this.http.post<P013AAspectosInfraestructura>(`${this.baseUrl}/P-013A`, datos);
  }

  guardarP023A(datos: P023AAspectosFondosCredito): Observable<P023AAspectosFondosCredito> {
    return this.http.post<P023AAspectosFondosCredito>(`${this.baseUrl}/P-023A`, datos);
  }

  guardarP024A(datos: P024ACarteraPorEdades): Observable<P024ACarteraPorEdades> {
    return this.http.post<P024ACarteraPorEdades>(`${this.baseUrl}/P-024A`, datos);
  }

  guardarP026A(datos: P026AArrendamientoBienes): Observable<P026AArrendamientoBienes> {
    return this.http.post<P026AArrendamientoBienes>(`${this.baseUrl}/P-026A`, datos);
  }

  guardarP031A(datos: P031AComodatoBienes): Observable<P031AComodatoBienes> {
    return this.http.post<P031AComodatoBienes>(`${this.baseUrl}/P-031A`, datos);
  }

  guardarP034A(datos: P034ACompraBienes): Observable<P034ACompraBienes> {
    return this.http.post<P034ACompraBienes>(`${this.baseUrl}/P-034A`, datos);
  }

  guardarP040A(datos: P040APermutaBienes): Observable<P040APermutaBienes> {
    return this.http.post<P040APermutaBienes>(`${this.baseUrl}/P-040A`, datos);
  }

  guardarP050A(datos: P050ANegociacionAcciones): Observable<P050ANegociacionAcciones> {
    return this.http.post<P050ANegociacionAcciones>(`${this.baseUrl}/P-050A`, datos);
  }

  guardarP055A(datos: P055ACapitalizaciones): Observable<P055ACapitalizaciones> {
    return this.http.post<P055ACapitalizaciones>(`${this.baseUrl}/P-055A`, datos);
  }

  obtenerEstructurasPorProyecto(codigoProyecto: string): Observable<EstructurasResponse> {
    return this.http.get<EstructurasResponse>(`${this.baseUrl}/proyecto/${codigoProyecto}`);
  }

  obtenerEstructura(codigoProyecto: string, estructura: string): Observable<unknown> {
    return this.http.get(`${this.baseUrl}/${codigoProyecto}/${estructura}`);
  }

  obtenerP001A(codigoProyecto: string): Observable<P001ADatosGenerales> {
    return this.http.get<P001ADatosGenerales>(`${this.baseUrl}/P-001A/${codigoProyecto}`);
  }

  obtenerP002A(codigoProyecto: string): Observable<P002ACronograma> {
    return this.http.get<P002ACronograma>(`${this.baseUrl}/P-002A/${codigoProyecto}`);
  }

  obtenerP003A(codigoProyecto: string): Observable<P003ALocalizacion> {
    return this.http.get<P003ALocalizacion>(`${this.baseUrl}/P-003A/${codigoProyecto}`);
  }

  obtenerP004C(codigoProyecto: string): Observable<P004CEstructuraFuenteRecursos> {
    return this.http.get<P004CEstructuraFuenteRecursos>(`${this.baseUrl}/P-004C/${codigoProyecto}`);
  }

  obtenerP011A(codigoProyecto: string): Observable<P011ACoberturaProyectada> {
    return this.http.get<P011ACoberturaProyectada>(`${this.baseUrl}/P-011A/${codigoProyecto}`);
  }

  obtenerP013A(codigoProyecto: string): Observable<P013AAspectosInfraestructura> {
    return this.http.get<P013AAspectosInfraestructura>(`${this.baseUrl}/P-013A/${codigoProyecto}`);
  }
}