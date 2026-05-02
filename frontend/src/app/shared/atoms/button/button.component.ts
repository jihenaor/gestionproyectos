import { Component, input, output } from '@angular/core';

type ButtonVariant = 'primary' | 'secondary' | 'outline' | 'ghost' | 'danger';
type ButtonSize = 'sm' | 'md' | 'lg';

@Component({
  selector: 'app-button',
  standalone: true,
  template: `
    <button
      [type]="type()"
      [disabled]="disabled() || loading()"
      [class]="buttonClass()"
      [attr.aria-busy]="loading()">
      @if (loading()) {
        <span class="button__spinner"></span>
      }
      @if (iconLeft()) {
        <span class="button__icon button__icon--left" [innerHTML]="iconLeft()"></span>
      }
      <span class="button__text"><ng-content /></span>
      @if (iconRight()) {
        <span class="button__icon button__icon--right" [innerHTML]="iconRight()"></span>
      }
    </button>
  `,
  styles: [`
    .button {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-sm);
      font-weight: 500;
      border-radius: var(--radius-md);
      border: 1px solid transparent;
      cursor: pointer;
      transition: all 0.15s ease;
      white-space: nowrap;
    }
    .button:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
    .button__spinner {
      width: 1rem;
      height: 1rem;
      border: 2px solid currentColor;
      border-right-color: transparent;
      border-radius: 50%;
      animation: spin 0.6s linear infinite;
    }
    @keyframes spin {
      to { transform: rotate(360deg); }
    }
    /* Sizes */
    .button--sm {
      padding: var(--spacing-xs) var(--spacing-sm);
      font-size: 0.875rem;
    }
    .button--md {
      padding: var(--spacing-sm) var(--spacing-md);
      font-size: 1rem;
    }
    .button--lg {
      padding: var(--spacing-md) var(--spacing-lg);
      font-size: 1.125rem;
    }
    /* Variants */
    .button--primary {
      background: var(--color-primary);
      color: white;
    }
    .button--primary:hover:not(:disabled) {
      background: var(--color-primary-hover);
    }
    .button--secondary {
      background: var(--color-background-tertiary);
      color: var(--color-text-primary);
    }
    .button--secondary:hover:not(:disabled) {
      background: var(--color-border);
    }
    .button--outline {
      border-color: var(--color-border);
      background: transparent;
      color: var(--color-text-primary);
    }
    .button--outline:hover:not(:disabled) {
      background: var(--color-background-secondary);
    }
    .button--ghost {
      background: transparent;
      color: var(--color-text-primary);
    }
    .button--ghost:hover:not(:disabled) {
      background: var(--color-background-secondary);
    }
    .button--danger {
      background: var(--color-error);
      color: white;
    }
    .button--danger:hover:not(:disabled) {
      background: #B91C1C;
    }
    .button__icon {
      display: inline-flex;
      align-items: center;
    }
    .button__icon--left {
      margin-right: var(--spacing-xs);
    }
    .button__icon--right {
      margin-left: var(--spacing-xs);
    }
  `]
})
export class ButtonComponent {
  variant = input<ButtonVariant>('primary');
  size = input<ButtonSize>('md');
  type = input<'button' | 'submit' | 'reset'>('button');
  disabled = input<boolean>(false);
  loading = input<boolean>(false);
  iconLeft = input<string>('');
  iconRight = input<string>('');

  buttonClass() {
    return `button button--${this.variant()} button--${this.size()}`;
  }
}