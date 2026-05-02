export interface P001ADatosGenerales {
  codigoProyecto: string;
  nombreProyecto: string;
  modalidadInversion: string;
  valorTotalProyecto: number;
  valorAprobadoVigencia: number;
  justificacion: string;
  objetivos: string;
  resolucionAEI: number;
  numActa: string;
  numConsejeros: number;
  tiempoRecuperacion: number;
  tasaDescuento: string;
}

export interface P002AActividad {
  tipoActividad: string;
  descripcion: string;
  porcentaje: number;
  fechaInicio: string;
  fechaTerminacion: string;
}

export interface P002ACronograma {
  codigoProyecto: string;
  actividades: P002AActividad[];
}

export interface P003ALocalizacion {
  codigoProyecto: string;
  departamento: string;
  municipio: string;
  direccion: string;
  coordenadas: string;
}

export interface P004CRecurso {
  tipo: string;
  fuente: string;
  valor: number;
}

export interface P004CEstructuraFuenteRecursos {
  codigoProyecto: string;
  recursos: P004CRecurso[];
  valorTotal: number;
}

export interface P011ACoberturaProyectada {
  codigoProyecto: string;
  numeroBeneficiarios: number;
  tipoBeneficiario: string;
  departamento: string;
  municipio: string;
}

export interface P011BCoberturaEjecutada {
  codigoProyecto: string;
  numeroBeneficiarios: number;
  tipoBeneficiario: string;
  departamento: string;
  municipio: string;
  fechaEjecucion: string;
}

export interface P012ASeguimientoProyecto {
  codigoProyecto: string;
  estado: string;
  avancePorcentaje: number;
  observaciones: string;
  fechaSeguimiento: string;
}

export interface P013AAspectosInfraestructura {
  codigoProyecto: string;
  modalidadInversion: string;
  licenciaConstruccion: number;
  numLicencia: string;
  fechaLicencia: string;
  interventoria: number;
 	numActaInterventoria: string;
  fechaActaInterventoria: string;
}

export interface P023AAspectosFondosCredito {
  codigoProyecto: string;
  tipoCredito: string;
  numeroCreditos: number;
  valorTotalCreditos: number;
  plazoPromedio: number;
}

export interface P024ACarteraPorEdades {
  codigoProyecto: string;
  edadCartera: string;
  numeroOperaciones: number;
  valorCartera: number;
  provision: number;
}

export interface P026AArrendamientoBienes {
  codigoProyecto: string;
  numContrato: string;
  fechaContrato: string;
  bienArrendado: string;
  valorCanon: number;
  plazoMeses: number;
  fechaCertTradicionLibertad: string;
}

export interface P031AComodatoBienes {
  codigoProyecto: string;
  numConvenio: string;
  fechaConvenio: string;
  bienComodato: string;
  plazoMeses: number;
  fechaCertTradicionLibertad: string;
}

export interface P034ACompraBienes {
  codigoProyecto: string;
  numEscritura: string;
  fechaEscritura: string;
  bienComprado: string;
  valorCompra: number;
  fechaCertTradicionLibertad: string;
}

export interface P040APermutaBienes {
  codigoProyecto: string;
  numEscritura: string;
  fechaEscritura: string;
  bienEntregado: string;
  bienRecibido: string;
  valorBienEntregado: number;
  valorBienRecibido: number;
  fechaCertTradicionLibertad: string;
}

export interface P050ANegociacionAcciones {
  codigoProyecto: string;
  numContrato: string;
  fechaContrato: string;
  empresa: string;
  numAcciones: number;
  valorAcciones: number;
}

export interface P055ACapitalizaciones {
  codigoProyecto: string;
  numEscritura: string;
  fechaEscritura: string;
  empresa: string;
  valorCapitalizacion: number;
}

export interface EstructuraGuardadaEvent {
  estructura: string;
  datos: unknown;
}

export interface EstructuraResponse {
  codigo: string;
  nombre: string;
  completado: boolean;
  datos?: unknown;
}

export interface EstructurasResponse {
  estructuras: Record<string, EstructuraResponse>;
}