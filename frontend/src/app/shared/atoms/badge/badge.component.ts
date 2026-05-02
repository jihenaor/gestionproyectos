import { Component, input } from '@angular/core';

type BadgeVariant = 'success' | 'warning' | 'error' | 'info' | 'neutral';
type BadgeSize = 'sm' | 'md';

@Component({
  selector: 'app-badge',
  standalone: true,
  template: `
    <span [class]="badgeClass()">
      @if (icon()) {
        <span class="badge__icon" [innerHTML]="icon()"></span>
      }
      <ng-content />
    </span>
  `,
  styles: [`
    .badge {
      display: inline-flex;
      align-items: center;
      gap: var(--spacing-xs);
      font-weight: 500;
      border-radius: var(--radius-full);
      white-space: nowrap;
    }
    /* Sizes */
    .badge--sm {
      padding: 2px var(--spacing-sm);
      font-size: 0.75rem;
    }
    .badge--md {
      padding: var(--spacing-xs) var(--spacing-sm);
      font-size: 0.875rem;
    }
    /* Variants */
    .badge--success {
      background: var(--color-success-bg);
      color: var(--color-success);
    }
    .badge--warning {
      background: var(--color-warning-bg);
      color: #B45309;
    }
    .badge--error {
      background: var(--color-error-bg);
      color: var(--color-error);
    }
    .badge--info {
      background: var(--color-info-bg);
      color: var(--color-info);
    }
    .badge--neutral {
      background: var(--color-background-tertiary);
      color: var(--color-text-secondary);
    }
    .badge__icon {
      display: inline-flex;
      align-items: center;
    }
  `]
})
export class BadgeComponent {
  variant = input<BadgeVariant>('neutral');
  size = input<BadgeSize>('md');
  icon = input<string>('');

  badgeClass() {
    return `badge badge--${this.variant()} badge--${this.size()}`;
  }
}