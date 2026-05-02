import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface DocumentoItem {
  id: string;
  nombre: string;
  tipo: string;
  proyectoId: string;
  tamanho: number;
  fechaCargue?: Date;
}

@Component({
  selector: 'app-document-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="document-list">
      <h4 class="document-list__title">{{ title() }}</h4>

      @if (documentos().length === 0) {
        <div class="document-list__empty">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
          </svg>
          <p>No hay documentos cargados</p>
        </div>
      } @else {
        <ul class="document-list__items">
          @for (doc of documentos(); track doc.id) {
            <li class="document-list__item">
              <div class="document-list__info">
                <svg class="document-list__icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                </svg>
                <div class="document-list__details">
                  <span class="document-list__name">{{ doc.nombre }}</span>
                  <span class="document-list__meta">
                    {{ doc.tipo }} | {{ formatSize(doc.tamanho) }}
                  </span>
                </div>
              </div>
              <div class="document-list__actions">
                @if (doc.tipo.includes('PDF') || doc.nombre.endsWith('.pdf')) {
                  <button type="button"
                          class="document-list__btn document-list__btn--view"
                          (click)="viewDocument.emit(doc.id)"
                          title="Ver documento">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                    </svg>
                  </button>
                }
                <button type="button"
                        class="document-list__btn document-list__btn--delete"
                        (click)="deleteDocument.emit(doc.id)"
                        title="Eliminar documento">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                  </svg>
                </button>
              </div>
            </li>
          }
        </ul>
      }
    </div>
  `,
  styles: [`
    .document-list {
      background: var(--color-bg);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: 1rem;
    }

    .document-list__title {
      margin: 0 0 1rem 0;
      font-size: 1rem;
      font-weight: 600;
      color: var(--color-text-primary);
    }

    .document-list__empty {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.5rem;
      padding: 2rem;
      color: var(--color-text-secondary);
    }

    .document-list__empty svg {
      width: 48px;
      height: 48px;
      opacity: 0.5;
    }

    .document-list__empty p {
      margin: 0;
    }

    .document-list__items {
      list-style: none;
      padding: 0;
      margin: 0;
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }

    .document-list__item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0.75rem;
      background: var(--color-bg-secondary);
      border-radius: var(--radius-md);
      transition: background 0.2s ease;
    }

    .document-list__item:hover {
      background: var(--color-bg-hover);
    }

    .document-list__info {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }

    .document-list__icon {
      width: 32px;
      height: 32px;
      color: var(--color-error);
      flex-shrink: 0;
    }

    .document-list__details {
      display: flex;
      flex-direction: column;
    }

    .document-list__name {
      font-weight: 500;
      color: var(--color-text-primary);
      word-break: break-all;
    }

    .document-list__meta {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }

    .document-list__actions {
      display: flex;
      gap: 0.5rem;
    }

    .document-list__btn {
      width: 32px;
      height: 32px;
      border: none;
      border-radius: var(--radius-md);
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.2s ease;
    }

    .document-list__btn svg {
      width: 18px;
      height: 18px;
    }

    .document-list__btn--view {
      background: var(--color-primary) / 10%;
      color: var(--color-primary);
    }

    .document-list__btn--view:hover {
      background: var(--color-primary);
      color: white;
    }

    .document-list__btn--delete {
      background: var(--color-bg-secondary);
      color: var(--color-text-secondary);
    }

    .document-list__btn--delete:hover {
      background: var(--color-error);
      color: white;
    }
  `]
})
export class DocumentListComponent {
  title = input<string>('Documentos');
  documentos = input<DocumentoItem[]>([]);

  viewDocument = output<string>();
  deleteDocument = output<string>();

  formatSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}