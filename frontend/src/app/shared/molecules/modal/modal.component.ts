import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  template: `
    @if (isOpen()) {
      <div class="modal-backdrop" (click)="onBackdropClick()" role="presentation">
        <div
          class="modal"
          [class.modal--sm]="size() === 'sm'"
          [class.modal--md]="size() === 'md'"
          [class.modal--lg]="size() === 'lg'"
          [class.modal--xl]="size() === 'xl'"
          role="dialog"
          [attr.aria-labelledby]="titleId()"
          [attr.aria-modal]="true"
          (click)="$event.stopPropagation()">
          <header class="modal__header">
            <h2 [id]="titleId()" class="modal__title">{{ title() }}</h2>
            <button
              type="button"
              class="modal__close"
              aria-label="Cerrar"
              (click)="close.emit()">
              <svg viewBox="0 0 24 24" fill="none" width="24" height="24">
                <path d="M18 6L6 18M6 6l12 12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </button>
          </header>
          <div class="modal__body">
            <ng-content />
          </div>
          @if (showFooter()) {
            <footer class="modal__footer">
              <ng-content select="[modal-footer]" />
            </footer>
          }
        </div>
      </div>
    }
  `,
  styles: [`
    .modal-backdrop {
      position: fixed;
      inset: 0;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
      padding: var(--spacing-md);
      animation: fadeIn 0.15s ease;
    }
    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }
    .modal {
      background: var(--color-background);
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-lg);
      max-height: calc(100vh - 4rem);
      display: flex;
      flex-direction: column;
      animation: slideIn 0.2s ease;
    }
    @keyframes slideIn {
      from {
        opacity: 0;
        transform: translateY(-1rem);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }
    .modal--sm { width: 100%; max-width: 24rem; }
    .modal--md { width: 100%; max-width: 32rem; }
    .modal--lg { width: 100%; max-width: 48rem; }
    .modal--xl { width: 100%; max-width: 64rem; }
    .modal__header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: var(--spacing-lg);
      border-bottom: 1px solid var(--color-border);
    }
    .modal__title {
      font-size: 1.25rem;
      font-weight: 600;
      margin: 0;
    }
    .modal__close {
      background: transparent;
      border: none;
      color: var(--color-text-secondary);
      cursor: pointer;
      padding: var(--spacing-xs);
      border-radius: var(--radius-sm);
    }
    .modal__close:hover {
      background: var(--color-background-secondary);
    }
    .modal__body {
      padding: var(--spacing-lg);
      overflow-y: auto;
      flex: 1;
    }
    .modal__footer {
      padding: var(--spacing-lg);
      border-top: 1px solid var(--color-border);
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-sm);
    }
  `]
})
export class ModalComponent {
  title = input.required<string>();
  isOpen = input<boolean>(false);
  size = input<'sm' | 'md' | 'lg' | 'xl'>('md');
  showFooter = input<boolean>(true);
  closeOnBackdrop = input<boolean>(true);

  close = output<void>();

  titleId(): string {
    return 'modal-title-' + Math.random().toString(36).substr(2, 9);
  }

  onBackdropClick(): void {
    if (this.closeOnBackdrop()) {
      this.close.emit();
    }
  }
}