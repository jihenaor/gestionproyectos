import { Component, input, output, forwardRef, signal, effect } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputComponent),
      multi: true
    }
  ],
  template: `
    <div class="input-wrapper" [class.has-error]="hasError()">
      @if (label()) {
        <label [for]="inputId()" class="input__label">
          {{ label() }}
          @if (required()) {
            <span class="input__required">*</span>
          }
        </label>
      }
      <div class="input__container">
        @if (prefixIcon()) {
          <span class="input__prefix" [innerHTML]="prefixIcon()"></span>
        }
        <input
          [id]="inputId()"
          [type]="type()"
          [placeholder]="placeholder()"
          [disabled]="isDisabled()"
          [readonly]="readonly()"
          [attr.maxlength]="maxLength() || null"
          [attr.aria-describedby]="hasError() ? inputId() + '-error' : null"
          [attr.aria-invalid]="hasError()"
          class="input__field"
          [value]="internalValue()"
          (input)="onInput($event)"
          (blur)="onTouched()" />
        @if (suffixIcon()) {
          <span class="input__suffix" [innerHTML]="suffixIcon()"></span>
        }
      </div>
      @if (helperText() && !hasError()) {
        <span class="input__helper">{{ helperText() }}</span>
      }
      @if (hasError() && errorMessage()) {
        <span [id]="inputId() + '-error'" class="input__error">{{ errorMessage() }}</span>
      }
      @if (showCharCount() && maxLength()) {
        <span class="input__charcount">{{ internalValue().length }}/{{ maxLength() }}</span>
      }
    </div>
  `,
  styles: [`
    .input-wrapper {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xs);
    }
    .input__label {
      font-size: 0.875rem;
      font-weight: 500;
      color: var(--color-text-primary);
    }
    .input__required {
      color: var(--color-error);
      margin-left: 2px;
    }
    .input__container {
      display: flex;
      align-items: center;
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      background: var(--color-background);
      overflow: hidden;
      transition: border-color 0.15s ease;
    }
    .input__container:focus-within {
      border-color: var(--color-border-focus);
      box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
    }
    .has-error .input__container {
      border-color: var(--color-error);
    }
    .input__prefix,
    .input__suffix {
      display: flex;
      align-items: center;
      padding: 0 var(--spacing-sm);
      color: var(--color-text-secondary);
    }
    .input__field {
      flex: 1;
      padding: var(--spacing-sm) var(--spacing-md);
      border: none;
      background: transparent;
      font-size: 1rem;
      color: var(--color-text-primary);
      outline: none;
      width: 100%;
    }
    .input__field::placeholder {
      color: var(--color-text-tertiary);
    }
    .input__field:disabled {
      background: var(--color-background-secondary);
      cursor: not-allowed;
    }
    .input__helper {
      font-size: 0.75rem;
      color: var(--color-text-secondary);
    }
    .input__error {
      font-size: 0.75rem;
      color: var(--color-error);
    }
    .input__charcount {
      font-size: 0.75rem;
      color: var(--color-text-tertiary);
      text-align: right;
    }
  `]
})
export class InputComponent implements ControlValueAccessor {
  label = input<string>('');
  type = input<'text' | 'email' | 'password' | 'number' | 'tel' | 'date'>('text');
  placeholder = input<string>('');
  helperText = input<string>('');
  errorMessage = input<string>('');
  required = input<boolean>(false);
  disabled = input<boolean>(false);
  readonly = input<boolean>(false);
  maxLength = input<number>(0);
  showCharCount = input<boolean>(false);
  hasError = input<boolean>(false);
  prefixIcon = input<string>('');
  suffixIcon = input<string>('');
  inputId = input<string>('');
  value = input<string>('');
  valueChange = output<string>();

  internalValue = signal('');
  isDisabled = signal(false);

  constructor() {
    effect(() => {
      const v = this.value();
      if (v !== undefined && v !== null) {
        this.internalValue.set(v);
      }
    }, { allowSignalWrites: true });
  }

  onChange: (value: string) => void = () => {};
  onTouched: () => void = () => {};

  writeValue(val: string): void {
    this.internalValue.set(val || '');
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled.set(isDisabled);
  }

  onInput(event: Event): void {
    const val = (event.target as HTMLInputElement).value;
    this.internalValue.set(val);
    this.onChange(val);
    this.valueChange.emit(val);
  }
}