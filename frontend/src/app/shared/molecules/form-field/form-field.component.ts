import { Component, input, output, signal, ContentChild, ElementRef, AfterContentInit } from '@angular/core';
import { InputComponent } from '../../atoms/input/input.component';
import { SelectComponent, SelectOption } from '../../atoms/select/select.component';

export interface FieldValidation {
  type: 'required' | 'format' | 'range' | 'custom';
  rule: string;
  message: string;
}

@Component({
  selector: 'app-form-field',
  standalone: true,
  imports: [InputComponent, SelectComponent],
  template: `
    <div class="form-field" [class.has-error]="hasError()" [class.is-conditional]="isConditional()">
      <div class="form-field__header">
        <label [for]="fieldId()" class="form-field__label">
          {{ label() }}
          @if (isRequired()) {
            <span class="form-field__required">*</span>
          }
          @if (isConditional()) {
            <span class="form-field__conditional-badge">Condicional</span>
          }
        </label>
        @if (tableReference()) {
          <span class="form-field__table-ref">Tabla {{ tableReference() }}</span>
        }
      </div>

      <div class="form-field__content">
        @switch (fieldType()) {
          @case ('input') {
            <app-input
              [inputId]="fieldId()"
              [type]="inputType()"
              [placeholder]="placeholder()"
              [helperText]="helperText()"
              [required]="isRequired()"
              [disabled]="isDisabled()"
              [maxLength]="maxLength()"
              [showCharCount]="showCharCount()"
              [hasError]="hasError()"
              [errorMessage]="errorMessage()"
              [prefixIcon]="prefixIcon()"
              [suffixIcon]="suffixIcon()"
            />
          }
          @case ('select') {
            <app-select
              [selectId]="fieldId()"
              [label]="''"
              [placeholder]="placeholder()"
              [options]="selectOptions()"
              [required]="isRequired()"
              [disabled]="isDisabled()"
              [hasError]="hasError()"
              [errorMessage]="errorMessage()"
            />
          }
          @default {
            <ng-content></ng-content>
          }
        }
        <button
          type="button"
          class="form-field__help-btn"
          [attr.aria-label]="'Ayuda para ' + label()"
          (click)="toggleHelp()">
          ?
        </button>
      </div>

      @if (showHelp()) {
        <div class="form-field__help-panel" role="tooltip">
          @if (description()) {
            <p class="form-field__description">{{ description() }}</p>
          }
          @if (formatExample()) {
            <p class="form-field__format">
              <strong>Formato:</strong> {{ formatExample() }}
            </p>
          }
          @if (validationRules().length > 0) {
            <ul class="form-field__rules">
              @for (rule of validationRules(); track rule) {
                <li>{{ rule }}</li>
              }
            </ul>
          }
        </div>
      }
    </div>
  `,
  styles: [`
    :host {
      display: block;
      width: 100%;
      min-width: 0;
    }
    .form-field {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xs);
      margin-bottom: var(--spacing-md);
      width: 100%;
      min-width: 0;
    }
    .form-field__header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .form-field__label {
      font-weight: 500;
      color: var(--color-text-primary);
      font-size: 0.875rem;
    }
    .form-field__required {
      color: var(--color-error);
      margin-left: 2px;
    }
    .form-field__conditional-badge {
      font-size: 0.75rem;
      background: var(--color-info);
      color: white;
      padding: 2px 8px;
      border-radius: var(--radius-full);
      margin-left: var(--spacing-sm);
    }
    .form-field__table-ref {
      font-size: 0.75rem;
      color: var(--color-info);
    }
    .form-field__content {
      display: flex;
      align-items: flex-start;
      gap: var(--spacing-sm);
      width: 100%;
      min-width: 0;
    }
    .form-field__content > :first-child {
      flex: 1;
      min-width: 0;
      align-self: stretch;
    }
    .form-field__help-btn {
      width: 1.5rem;
      height: 1.5rem;
      border-radius: 50%;
      background: var(--color-info);
      color: white;
      border: none;
      cursor: pointer;
      font-weight: bold;
      font-size: 0.75rem;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }
    .form-field__help-btn:hover {
      background: var(--color-primary-hover);
    }
    .form-field__help-panel {
      background: var(--color-background-secondary);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      padding: var(--spacing-md);
      margin-top: var(--spacing-xs);
    }
    .form-field__description {
      margin: 0 0 var(--spacing-sm) 0;
      color: var(--color-text-secondary);
      font-size: 0.875rem;
    }
    .form-field__format {
      margin: 0 0 var(--spacing-sm) 0;
      font-family: monospace;
      font-size: 0.875rem;
      background: var(--color-background);
      padding: var(--spacing-xs) var(--spacing-sm);
      border-radius: var(--radius-sm);
    }
    .form-field__rules {
      margin: 0;
      padding-left: var(--spacing-lg);
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }
    .form-field__rules li {
      margin-bottom: var(--spacing-xs);
    }
    .has-error {
      --tw-ring-color: var(--color-error);
    }
  `]
})
export class FormFieldComponent {
  label = input.required<string>();
  fieldId = input.required<string>();
  fieldType = input<'input' | 'select' | 'custom'>('input');
  inputType = input<'text' | 'email' | 'password' | 'number' | 'tel' | 'date'>('text');
  placeholder = input<string>('');
  description = input<string>('');
  formatExample = input<string>('');
  tableReference = input<string>('');
  validationRules = input<string[]>([]);
  helperText = input<string>('');
  isRequired = input<boolean>(false);
  isConditional = input<boolean>(false);
  isDisabled = input<boolean>(false);
  maxLength = input<number>(0);
  showCharCount = input<boolean>(false);
  hasError = input<boolean>(false);
  errorMessage = input<string>('');
  prefixIcon = input<string>('');
  suffixIcon = input<string>('');
  selectOptions = input<SelectOption[]>([]);

  showHelp = signal(false);

  toggleHelp() {
    this.showHelp.update(v => !v);
  }
}