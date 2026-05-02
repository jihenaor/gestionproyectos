import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class DateUtilsService {
  formatDateToAAAAMMDD(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}${month}${day}`;
  }

  parseAAAAMMDD(aaaammdd: string): Date | null {
    if (!aaaammdd || aaaammdd.length !== 8) return null;
    const year = parseInt(aaaammdd.substring(0, 4), 10);
    const month = parseInt(aaaammdd.substring(4, 6), 10) - 1;
    const day = parseInt(aaaammdd.substring(6, 8), 10);
    return new Date(year, month, day);
  }

  formatDateToDisplay(aaaammdd: string): string {
    const date = this.parseAAAAMMDD(aaaammdd);
    if (!date) return '';
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  }

  isValidDate(dateStr: string): boolean {
    if (!dateStr) return false;
    const date = new Date(dateStr);
    return !isNaN(date.getTime());
  }
}

@Injectable({ providedIn: 'root' })
export class ValidationUtilsService {
  isValidCodigoProyecto(codigo: string): boolean {
    const pattern = /^CCF\d{3}-\d{2}-\d{5}$/;
    return pattern.test(codigo);
  }

  isValidCodigoFovis(codigo: string): boolean {
    const pattern = /^\d{2}-\d{2}-\d{5}$/;
    return pattern.test(codigo);
  }

  isValidNumber(value: string | number, min?: number, max?: number): boolean {
    const num = typeof value === 'string' ? parseInt(value, 10) : value;
    if (isNaN(num)) return false;
    if (min !== undefined && num < min) return false;
    if (max !== undefined && num > max) return false;
    return true;
  }

  sanitizeNumericInput(value: string): string {
    return value.replace(/[^\d]/g, '');
  }
}

@Injectable({ providedIn: 'root' })
export class StringUtilsService {
  truncate(text: string, maxLength: number): string {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength - 3) + '...';
  }

  removeHtmlTags(html: string): string {
    const tmp = document.createElement('div');
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || '';
  }

  stripWhitespace(text: string): string {
    return text.replace(/\s+/g, ' ').trim();
  }
}