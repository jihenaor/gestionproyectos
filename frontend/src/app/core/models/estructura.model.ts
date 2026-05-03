export interface P001ADatosGenerales {
  codigoProyecto: string;
  nombreProyecto?: string;
  modalidadInversion?: string;
  valorTotalProyecto?: number;
  valorAprobadoVigencia?: number;
  justificacion?: string;
  objetivos?: string;
  resolucionAEI?: number;
  numActa?: string;
  numConsejeros?: number;
  tiempoRecuperacion?: number;
  tasaDescuento?: string;
  numeroBeneficiarios?: number;
  descripcionObjetivo?: string;
}

export interface P002ACronogramaActividad {
  tipoActividad: string;
  descripcionActividad?: string;
  descripcion?: string;
  porcentajeProyectado?: string;
  porcentaje?: number;
  fechaInicio: string;
  fechaTerminacion: string;
  unidadMedida?: string;
  cantidadProgramada?: number;
}

export interface P002ACronograma {
  codigoProyecto: string;
  actividades: P002ACronogramaActividad[];
  porcentajeTotal?: string;
}

export interface P003ALocalizacion {
  codigoProyecto: string;
  departamento: string;
  municipio: string;
  direccion?: string;
  barrio?: string;
  telefono?: string;
  contacto?: string;
  fechaInicioOperacion?: string;
  latitude?: number;
  longitude?: number;
  coordenadas?: string;
}

export interface P004CFuenteRecurso {
  codigoFuente: string;
  nombreFuente?: string;
  fuente?: string;
  valor: number;
  porcentaje?: string;
  tipo?: string;
  tipoRecurso?: string;
  centroCosto?: string;
}

export interface P004CEstructuraFuenteRecursos {
  codigoProyecto: string;
  fuentes?: P004CFuenteRecurso[];
  recursos?: P004CFuenteRecurso[];
  valorTotal?: number;
}

export type P004CRecurso = P004CFuenteRecurso;

export interface P011ACoberturaProyectadaItem {
  codigoCategoria: string;
  cantidadBeneficiarios: number;
  valorPerCapita: number;
  anio1?: number;
  anio2?: number;
  anio3?: number;
  anio4?: number;
  anio5?: number;
}

export interface P011ACoberturaProyectada {
  codigoProyecto: string;
  coberturas?: P011ACoberturaProyectadaItem[];
  numeroBeneficiarios?: number;
  tipoBeneficiario?: string;
  departamento?: string;
  municipio?: string;
}

export interface P011BCoberturaEjecutadaItem {
  codigoCategoria: string;
  cantidadBeneficiarios: number;
  valorPerCapita: number;
  anio1Ejecutado: number;
  anio2Ejecutado: number;
  anio3Ejecutado: number;
  anio4Ejecutado: number;
  anio5Ejecutado: number;
}

export interface P011BCoberturaEjecutada {
  codigoProyecto: string;
  coberturas: P011BCoberturaEjecutadaItem[];
  observaciones?: string;
  numeroBeneficiarios?: number;
  tipoBeneficiario?: string;
  departamento?: string;
  municipio?: string;
  fechaEjecucion?: string;
}

export interface P012ASeguimientoItem {
  tipoActividad: string;
  descripcionActividad: string;
  porcentajeEjecutado: string;
  valorPlaneado: number;
  valorEjecutado: number;
  costoActual: number;
  valorPagado: number;
  valorGanado: number;
  cantidadEjecucionFisica: number;
  fechaInicio: string;
  fechaTerminacion: string;
  observaciones?: string;
}

export interface P012ASeguimientoProyecto {
  codigoProyecto: string;
  periodoReporte?: string;
  seguimientos?: P012ASeguimientoItem[];
  estado?: string;
  avancePorcentaje?: number;
  observaciones?: string;
  fechaSeguimiento?: string;
}

export interface P013AAspectosInfraestructura {
  codigoProyecto: string;
  interventoriaSupervision?: number;
  valorTotalInterventoria?: number;
  licenciaConstruccion?: number;
  entidadCompetente?: string;
  numRadicadoLicencia?: string;
  fechaRadicacionLicencia?: string;
  numeroLicencia?: string;
  numLicencia?: string;
  fechaLicencia?: string;
  vigenciaLicencia?: string;
  serviciosPublicos?: number;
  fechaRadicacionAAA?: string;
  numRadicadoAAA?: string;
  fechaExpedicionAAA?: string;
  numDisponibilidadAAA?: string;
  vigenciaAAA?: number;
  fechaRadicacionEEA?: string;
  numRadicadoEEA?: string;
  fechaExpedicionEEA?: string;
  numDisponibilidadEEA?: string;
  vigenciaEEA?: number;
  fechaRadicacionGNA?: string;
  numRadicadoGNA?: string;
  fechaExpedicionGNA?: string;
  numDisponibilidadGNA?: string;
  vigenciaGNA?: number;
  proyeccionGeneracionEmpleo?: number;
  modalidadInversion?: string;
  interventoria?: number;
  numActaInterventoria?: string;
  fechaActaInterventoria?: string;
}

export interface P023AFondoCreditoItem {
  modalidadCredito: number;
  codigoCategoria: string;
  tasaInteresMinima: string;
  tasaInteresMaxima: string;
  cantCreditos: number;
  valMontoCreditos: number;
  plazoCredito: number;
  porcentajeSubsidio: string;
}

export interface P023AAspectosFondosCredito {
  codigoProyecto: string;
  items?: P023AFondoCreditoItem[];
  tipoCredito?: string;
  numeroCreditos?: number;
  valorTotalCreditos?: number;
  plazoPromedio?: number;
}

export interface P024ACarteraItem {
  rangoEdad: string;
  edadCartera: number;
  modalidadCredito: number;
  codigoCategoria: string;
  cantCreditos: number;
  valorTotalMontoCartera: number;
}

export interface P024ACarteraPorEdades {
  codigoProyecto: string;
  items?: P024ACarteraItem[];
  edadCartera?: string;
  numeroOperaciones?: number;
  valorCartera?: number;
  provision?: number;
}

export interface P026AArrendamientoBienes {
  codigoProyecto: string;
  fechaCertTradicionLibertad?: string;
  fechaAvaluo?: string;
  perito?: string;
  valorAvaluo?: number;
  valorCanonMensual?: number;
  valorCanon?: number;
  tiempoContrato?: number;
  plazoMeses?: number;
  destinacionInmueble?: string;
  usoAutorizado?: string;
  numContrato?: string;
  bienArrendado?: string;
  fechaContrato?: string;
}

export interface P031AComodatoBienes {
  codigoProyecto: string;
  fechaCertTradicionLibertad?: string;
  destinacionInmueble?: string;
  usoAutorizado?: string;
  numConvenio?: string;
  fechaConvenio?: string;
  bienComodato?: string;
  plazoMeses?: number;
}

export interface P034ACompraBienes {
  codigoProyecto: string;
  fechaCertTradicionLibertad?: string;
  fechaAvaluo?: string;
  perito?: string;
  valorAvaluo?: number;
  destinacionInmueble?: string;
  usoAutorizado?: string;
  numEscritura?: string;
  fechaEscritura?: string;
  bienComprado?: string;
  valorCompra?: number;
}

export interface P040APermutaBienes {
  codigoProyecto: string;
  fechaCertTradicionLibertad?: string;
  fechaAvaluoRecibe?: string;
  fechaAvaluoEntrega?: string;
  avaluadorRecibe?: string;
  avaluadorEntrega?: string;
  valorAvaluoRecibe?: number;
  valorAvaluoEntrega?: number;
  destinacionInmueble?: string;
  usoAutorizado?: string;
  valorEnLibros?: number;
  utilidadPerdida?: number;
  origenRecursos?: string;
  destinacion?: string;
  numEscritura?: string;
  fechaEscritura?: string;
  bienEntregado?: string;
  bienRecibido?: string;
  valorBienEntregado?: number;
  valorBienRecibido?: number;
}

export interface P050ANegociacionAcciones {
  codigoProyecto: string;
  numAccionesCuotas: number;
  valorAccionesCuotas: number;
  porcentajeParticipacion: string;
  valorNominalAcciones: number;
  valorMercadoAcciones: number;
  numContrato?: string;
  fechaContrato?: string;
  empresa?: string;
}

export interface P055ACapitalizaciones {
  codigoProyecto: string;
  numAccionesCuotas: number;
  valorAccionesCuotas: number;
  porcentajeParticipacion: string;
  valorNominalAcciones: number;
  valorMercadoAcciones: number;
  numEscritura?: string;
  fechaEscritura?: string;
  empresa?: string;
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