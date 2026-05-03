import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {
  ProyectoResponse,
  PaginatedResponse,
  CrearProyectoCommand,
  ActualizarProyectoCommand,
  DocumentoResponse,
  TablaReferencia,
  ReporteResponse,
  EstructuraResponse,
  PeriodoResponse
} from '../models/proyecto.model';
import { EstructurasResponse } from '../models/estructura.model';
import { environment } from '../../../environments/environment';

export interface PeriodoSupersubsidio {
  codigo: number;
  codigoPeriodo: string;
  fechaInicial: number;
  fechaFinal: number;
  descripcion: string;
  periodicidad: string;
  periodicidad2: string;
  motivoCodigo: string;
  fechaLimiteReporte: number;
  fechaLimiteRevision: number;
  anio: number;
  mes: number;
  codigoAnterior: number | null;
  solicitanteCodigo: number | null;
  codigoPeriodoTipo: string | null;
  codigoInfraestructura: number | null;
  motivoCodigoXml: string | null;
  codigoPeriodoLq: string | null;
  nombreMes: string | null;
  periodoAaaaMm: string | null;
  periodoActivo: string;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = '/api/v1';
  private readonly ciefApiUrl = environment.ciefApiUrl;
  private readonly http = inject(HttpClient);

  getProyectos(page = 0, size = 20): Observable<PaginatedResponse<ProyectoResponse>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PaginatedResponse<ProyectoResponse>>(`${this.baseUrl}/proyectos`, { params });
  }

  getProyectoById(id: string): Observable<ProyectoResponse> {
    return this.http.get<ProyectoResponse>(`${this.baseUrl}/proyectos/${id}`);
  }

  createProyecto(data: CrearProyectoCommand): Observable<ProyectoResponse> {
    return this.http.post<ProyectoResponse>(`${this.baseUrl}/proyectos`, data);
  }

  updateProyecto(id: string, data: ActualizarProyectoCommand): Observable<ProyectoResponse> {
    return this.http.put<ProyectoResponse>(`${this.baseUrl}/proyectos/${id}`, data);
  }

  deleteProyecto(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/proyectos/${id}`);
  }

  getEstructuras(projectId: string): Observable<EstructuraResponse[]> {
    return this.http.get<EstructuraResponse[]>(`${this.baseUrl}/proyectos/${projectId}/estructuras`);
  }

  saveEstructura(projectId: string, estructuraCode: string, data: unknown): Observable<unknown> {
    return this.http.post(`${this.baseUrl}/proyectos/${projectId}/estructuras/${estructuraCode}`, data);
  }

  uploadPdf(projectId: string, estructuraCode: string, file: File): Observable<DocumentoResponse> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<DocumentoResponse>(`${this.baseUrl}/proyectos/${projectId}/upload/${estructuraCode}`, formData);
  }

  getTablasReferencia(tablaNumber: number): Observable<TablaReferencia[]> {
    return this.http.get<TablaReferencia[]>(`${this.baseUrl}/tablas/${tablaNumber}`);
  }

  getFovisProyectos(page = 0, size = 20): Observable<PaginatedResponse<ProyectoResponse>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PaginatedResponse<ProyectoResponse>>(`${this.baseUrl}/fovis/proyectos`, { params });
  }

  getReportes(fechaInicio: string, fechaFin: string): Observable<ReporteResponse> {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.http.get<ReporteResponse>(`${this.baseUrl}/reportes`, { params });
  }

  obtenerEstructurasPorProyecto(codigoProyecto: string): Observable<EstructurasResponse> {
    return this.http.get<EstructurasResponse>(`${this.baseUrl}/estructuras/proyecto/${codigoProyecto}`);
  }

  getPeriodoActivo(): Observable<PeriodoResponse> {
    return this.http.get<PeriodoResponse>(`${this.baseUrl}/generacion-xml-informes/periodo/activo`);
  }

  generarXml(proyectoId: string, estructuraCodigo: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/generacion-xml-informes/xml/${proyectoId}/${estructuraCodigo}`, {
      responseType: 'text'
    });
  }

  generarZipXml(proyectoId: string, codigoPeriodo?: string): Observable<Blob> {
    const params = codigoPeriodo ? new HttpParams().set('codigoPeriodo', codigoPeriodo) : undefined;
    return this.http.get(`${this.baseUrl}/generacion-xml-informes/xml/proyecto/${proyectoId}`, {
      responseType: 'blob',
      params
    });
  }

  getEstructurasDisponibles(): Observable<{codigo: string; nombreXml: string; nombreArchivo: string}[]> {
    return this.http.get<{codigo: string; nombreXml: string; nombreArchivo: string}[]>(
      `${this.baseUrl}/generacion-xml-informes/estructuras`
    );
  }

  getPeriodosSupersubsidio(mocodmotiv = '01'): Observable<PeriodoSupersubsidio[]> {
    const params = new HttpParams().set('mocodmotiv', mocodmotiv);
    return this.http.get<PeriodoSupersubsidio[]>(
      `${this.ciefApiUrl}/parametrizacion/periodos-reporte`,
      { params }
    ).pipe(
      map(periodos => {
        const activos = periodos.filter(p => p.periodoActivo === 'S');
        return activos.length > 0 ? activos : periodos;
      })
    );
  }

  private getAuthToken(): string | null {
    return localStorage.getItem('gestionproyectos_auth_token');
  }
}