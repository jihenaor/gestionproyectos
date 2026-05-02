import { Injectable, signal, computed } from '@angular/core';

export interface ConditionalRule {
  fieldName: string;
  conditionField: string;
  conditionValue: any;
  requiredIfTrue: boolean;
  visibilityIfTrue: 'show' | 'hide';
  message?: string;
}

export interface TableReference {
  code: string;
  description: string;
}

@Injectable({ providedIn: 'root' })
export class ValidationService {
  private conditionalRules = signal<Map<string, ConditionalRule[]>>(new Map([
    ['P-013A', [
      { fieldName: 'ENT_COMPETENTE', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: [2, 3], requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=2 o 3' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=2 (En trámite)' },
      { fieldName: 'FECHA_RADICACION_SOLICITUD_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUMERO_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=3 (Expedida)' },
      { fieldName: 'FECHA_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_DISPONIBILIDAD_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
    ]],
    ['P-002A', [
      { fieldName: 'VALOR_PLANEADO', conditionField: 'MODALIDAD_INVERSION', conditionValue: 'INF', requiredIfTrue: false, visibilityIfTrue: 'show', message: 'Solo para proyectos de infraestructura' },
    ]],
  ]));

  private tableReferences = signal<Map<number, TableReference[]>>(new Map([
    [8, [
      { code: '1', description: 'A' },
      { code: '2', description: 'B' },
      { code: '3', description: 'C' },
      { code: '4', description: 'D' },
      { code: '5', description: 'E' },
    ]],
    [66, [
      { code: '1', description: 'Libre inversión' },
      { code: '2', description: 'Consumo' },
      { code: '3', description: 'Créditos Educativos' },
      { code: '4', description: 'Créditos de Salud' },
      { code: '5', description: 'Créditos de vivienda' },
      { code: '6', description: 'Fomento, Emprendimiento' },
      { code: '7', description: 'Otros' },
      { code: '8', description: 'Crédito de mercadeo' },
      { code: '9', description: 'Recreación y turismo' },
    ]],
    [68, [
      { code: '1', description: 'Enero' },
      { code: '2', description: 'Febrero' },
      { code: '3', description: 'Marzo' },
      { code: '4', description: 'Abril' },
      { code: '5', description: 'Mayo' },
      { code: '6', description: 'Junio' },
      { code: '7', description: 'Julio' },
      { code: '8', description: 'Agosto' },
      { code: '9', description: 'Septiembre' },
      { code: '10', description: 'Octubre' },
      { code: '11', description: 'Noviembre' },
      { code: '12', description: 'Diciembre' },
    ]],
    [115, [
      { code: '0501', description: 'Prediseño' },
      { code: '0502', description: 'Diseño' },
      { code: '0503', description: 'Ejecución' },
      { code: '0504', description: 'Interventoría' },
      { code: '0505', description: 'Adquisición de bienes' },
      { code: '0506', description: 'Adecuación de bienes' },
      { code: '0507', description: 'Mantenimiento de bienes' },
      { code: '0508', description: 'Dotación' },
      { code: '0509', description: 'Otros' },
      { code: '0601', description: 'Capacitación' },
      { code: '0602', description: 'Evaluar' },
      { code: '0603', description: 'Certificar' },
      { code: '0701', description: 'Fortalecimiento' },
      { code: '0702', description: 'Gestión' },
      { code: '0401', description: 'Desembolso crédito social' },
      { code: '0402', description: 'Desembolso microcrédito' },
      { code: '0403', description: 'Desembolso crédito constructor' },
    ]],
    [203, [
      { code: '1', description: 'Interna' },
      { code: '2', description: 'Externa' },
    ]],
    [204, [
      { code: '1', description: 'N.A.' },
      { code: '2', description: 'En trámite' },
      { code: '3', description: 'Expedida' },
    ]],
  ]));

  getConditionalRules(structureCode: string): ConditionalRule[] {
    return this.conditionalRules().get(structureCode) || [];
  }

  getTableValues(tableNumber: number): TableReference[] {
    return this.tableReferences().get(tableNumber) || [];
  }

  isFieldVisible(fieldName: string, structureCode: string, formValues: Record<string, any>): boolean {
    const rules = this.getConditionalRules(structureCode);
    const rule = rules.find(r => r.fieldName === fieldName);
    if (!rule) return true;

    const conditionValue = formValues[rule.conditionField];
    if (Array.isArray(rule.conditionValue)) {
      return rule.conditionValue.includes(conditionValue);
    }
    return conditionValue === rule.conditionValue;
  }

  isFieldRequired(fieldName: string, structureCode: string, formValues: Record<string, any>): boolean {
    const rules = this.getConditionalRules(structureCode);
    const rule = rules.find(r => r.fieldName === fieldName);
    if (!rule) return false;

    const conditionValue = formValues[rule.conditionField];
    if (Array.isArray(rule.conditionValue)) {
      return rule.conditionValue.includes(conditionValue);
    }
    return conditionValue === rule.conditionValue;
  }

  validateDateFormat(value: string): boolean {
    if (!value) return true;
    return /^\d{8}$/.test(value);
  }

  validateNumericNoSeparator(value: string): boolean {
    if (!value) return true;
    return /^\d+$/.test(value);
  }

  validateDecimalFormat(value: string): boolean {
    if (!value) return true;
    return /^\d{1,2}\.\d{1,2}$/.test(value);
  }

  validateCodigoProyecto(value: string): boolean {
    if (!value) return true;
    return /^CCF\d{3}-\d{2}-\d{5}$/.test(value);
  }

  validateCodigoFovis(value: string): boolean {
    if (!value) return true;
    return /^\d{2}-\d{2}-\d{5}$/.test(value);
  }
}