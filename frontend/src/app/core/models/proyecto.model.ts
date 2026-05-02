export interface CrearProyectoCommand {
  codigo: string;
  nombre: string;
  modalidadInversion: string;
  valorTotal: number;
  valorAprobado: number;
  justificacion: string;
}

export interface ActualizarProyectoCommand {
  nombre: string;
  modalidadInversion: string;
  valorTotal: number;
  valorAprobado: number;
  justificacion: string;
}

export interface ProyectoResponse {
  id: string;
  codigo: string;
  nombre: string;
  /** Presente si el API lo expone; si no, usar `modalidadInversion`. */
  modalidad?: string;
  modalidadInversion: string;
  valorTotal: number;
  valorAprobado: number;
  justificacion: string;
  estado: string;
  fechaCreacion: string;
  ultimaActualizacion?: string;
  estructurasCompletadas: string[];
  porcentajeCompletado: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}

export interface EstructuraResponse {
  codigo: string;
  nombre: string;
  completado: boolean;
  datos?: unknown;
}

export interface DocumentoResponse {
  id: string;
  nombre: string;
  tipo: string;
  url: string;
  fechaSubida: string;
}

export interface TablaReferencia {
  codigo: string;
  descripcion: string;
  categoria?: string;
}

export interface ReporteResponse {
  fechaInicio: string;
  fechaFin: string;
  proyectos: ReporteProyecto[];
  totalProyectos: number;
  valorTotal: number;
}

export interface ReporteProyecto {
  id: string;
  codigo: string;
  nombre: string;
  modalidad: string;
  valorTotal: number;
  estado: string;
}

export interface PeriodoResponse {
  codigo: number;
  codigoPeriodo: string;
  descripcion: string;
  anio: number;
  mes: number;
  nombreMes: string;
  periodoActivo: string;
}