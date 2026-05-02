import { Component, input, forwardRef, signal } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-checkbox',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CheckboxComponent),
      multi: true
    }
  ],
  template: `
    <label class="checkbox" [class.is-checked]="isChecked()" [class.is-disabled]="disabled()">
      <input
        type="checkbox"
        [checked]="isChecked()"
        [disabled]="disabled()"
        [indeterminate]="indeterminate()"
        class="checkbox__input"
        (change)="toggle()"
        (blur)="onTouched()" />
      <span class="checkbox__box">
        @if (isChecked()) {
          <svg class="checkbox__check" viewBox="0 0 12 12" fill="none">
            <path d="M2.5 6L5 8.5L9.5 3.5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        }
        @if (indeterminate()) {
          <svg class="checkbox__indeterminate" viewBox="0 0 12 12" fill="none">
            <path d="M2.5 6H9.5" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
          </svg>
        }
      </span>
      <span class="checkbox__label">
        <ng-content />
      </span>
    </label>
  `,
  styles: [`
    .checkbox {
      display: inline-flex;
      align-items: center;
      gap: var(--spacing-sm);
      cursor: pointer;
      user-select: none;
    }
    .checkbox.is-disabled {
      cursor: not-allowed;
      opacity: 0.5;
    }
    .checkbox__input {
      position: absolute;
      width: 1px;
      height: 1px;
      opacity: 0;
    }
    .checkbox__box {
      width: 1.25rem;
      height: 1.25rem;
      border: 2px solid var(--color-border);
      border-radius: var(--radius-sm);
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.15s ease;
      flex-shrink: 0;
    }
    .is-checked .checkbox__box {
      background: var(--color-primary);
      border-color: var(--color-primary);
      color: white;
    }
    .checkbox:hover:not(.is-disabled) .checkbox__box {
      border-color: var(--color-primary);
    }
    .checkbox__check,
    .checkbox__indeterminate {
      width: 0.75rem;
      height: 0.75rem;
    }
    .checkbox__label {
      font-size: 0.875rem;
      color: var(--color-text-primary);
    }
  `]
})
export class CheckboxComponent implements ControlValueAccessor {
  disabled = input<boolean>(false);
  indeterminate = input<boolean>(false);

  isChecked = signal(false);

  onChange: (value: boolean) => void = () => {};
  onTouched: () => void = () => {};

  writeValue(value: boolean): void {
    this.isChecked.set(!!value);
  }

  registerOnChange(fn: (value: boolean) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  toggle(): void {
    if (!this.disabled()) {
      const newValue = !this.isChecked();
      this.isChecked.set(newValue);
      this.onChange(newValue);
    }
  }
}