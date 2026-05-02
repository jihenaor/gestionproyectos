import { Component, input, output, forwardRef, signal, effect } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

export interface SelectOption {
  value: string | number;
  label: string;
  disabled?: boolean;
}

@Component({
  selector: 'app-select',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectComponent),
      multi: true
    }
  ],
  template: `
    <div class="select-wrapper" [class.has-error]="hasError()">
      @if (label()) {
        <label [for]="selectId()" class="select__label">
          {{ label() }}
          @if (required()) {
            <span class="select__required">*</span>
          }
        </label>
      }
      <div class="select__container">
        <select
          [id]="selectId()"
          [disabled]="isDisabled()"
          [attr.aria-describedby]="hasError() ? selectId() + '-error' : null"
          [attr.aria-invalid]="hasError()"
          class="select__field"
          [value]="internalValue()"
          (change)="onSelectChange($event)"
          (blur)="onTouched()">
          @if (placeholder()) {
            <option value="" disabled>{{ placeholder() }}</option>
          }
          @for (option of options(); track option.value) {
            <option [value]="option.value" [disabled]="option.disabled">
              {{ option.label }}
            </option>
          }
        </select>
        <span class="select__arrow">
          <svg width="12" height="12" viewBox="0 0 12 12" fill="none">
            <path d="M3 4.5L6 7.5L9 4.5" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </span>
      </div>
      @if (helperText() && !hasError()) {
        <span class="select__helper">{{ helperText() }}</span>
      }
      @if (hasError() && errorMessage()) {
        <span [id]="selectId() + '-error'" class="select__error">{{ errorMessage() }}</span>
      }
    </div>
  `,
  styles: [`
    .select-wrapper {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xs);
    }
    .select__label {
      font-size: 0.875rem;
      font-weight: 500;
      color: var(--color-text-primary);
    }
    .select__required {
      color: var(--color-error);
      margin-left: 2px;
    }
    .select__container {
      position: relative;
      display: flex;
      align-items: center;
    }
    .select__field {
      width: 100%;
      padding: var(--spacing-sm) var(--spacing-md);
      padding-right: 2.5rem;
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      background: var(--color-background);
      font-size: 1rem;
      color: var(--color-text-primary);
      cursor: pointer;
      appearance: none;
      outline: none;
      transition: border-color 0.15s ease;
    }
    .select__field:focus {
      border-color: var(--color-border-focus);
      box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
    }
    .select__field:disabled {
      background: var(--color-background-secondary);
      cursor: not-allowed;
    }
    .has-error .select__field {
      border-color: var(--color-error);
    }
    .select__arrow {
      position: absolute;
      right: var(--spacing-sm);
      pointer-events: none;
      color: var(--color-text-secondary);
    }
    .select__helper {
      font-size: 0.75rem;
      color: var(--color-text-secondary);
    }
    .select__error {
      font-size: 0.75rem;
      color: var(--color-error);
    }
  `]
})
export class SelectComponent implements ControlValueAccessor {
  label = input<string>('');
  placeholder = input<string>('Seleccione...');
  helperText = input<string>('');
  errorMessage = input<string>('');
  required = input<boolean>(false);
  disabled = input<boolean>(false);
  hasError = input<boolean>(false);
  options = input<SelectOption[]>([]);
  selectId = input<string>('');
  value = input<string | number>('');
  valueChange = output<string | number>();

  internalValue = signal<string | number>('');
  isDisabled = signal(false);

  constructor() {
    effect(() => {
      const v = this.value();
      if (v !== undefined && v !== null) {
        this.internalValue.set(v);
      }
    }, { allowSignalWrites: true });
  }

  onChange: (value: string | number) => void = () => {};
  onTouched: () => void = () => {};

  writeValue(val: string | number): void {
    this.internalValue.set(val);
  }

  registerOnChange(fn: (value: string | number) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled.set(isDisabled);
  }

  onSelectChange(event: Event): void {
    const val = (event.target as HTMLSelectElement).value;
    this.internalValue.set(val);
    this.onChange(val);
    this.valueChange.emit(val);
  }
}