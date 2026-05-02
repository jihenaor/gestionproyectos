import { Component, input, output, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileUploadComponent } from '../../../shared/organisms/file-upload/file-upload.component';
import { DocumentListComponent, DocumentoItem } from '../../../shared/organisms/document-list/document-list.component';
import { DocumentoService } from '../../../core/services/documento.service';

@Component({
  selector: 'app-documentos-manager',
  standalone: true,
  imports: [CommonModule, FileUploadComponent, DocumentListComponent],
  template: `
    <div class="documentos-manager">
      <div class="documentos-manager__header">
        <h3 class="documentos-manager__title">Documentos del Proyecto</h3>
        <span class="documentos-manager__count">{{ documentos().length }} documentos</span>
      </div>

      <div class="documentos-manager__upload">
        <app-file-upload
          (fileSelected)="onFileSelected($event)"
          #fileUpload />
      </div>

      @if (currentFile()) {
        <div class="documentos-manager__action">
          <button type="button"
                  class="btn btn--primary"
                  [disabled]="isUploading()"
                  (click)="subirDocumento()">
            @if (isUploading()) {
              <span class="spinner"></span>
              Subiendo...
            } @else {
              Subir {{ currentFile()!.name }}
            }
          </button>
        </div>
      }

      @if (successMessage()) {
        <div class="documentos-manager__success">
          {{ successMessage() }}
        </div>
      }

      @if (errorMessage()) {
        <div class="documentos-manager__error">
          {{ errorMessage() }}
        </div>
      }

      <app-document-list
        title="Documentos cargados"
        [documentos]="documentos()"
        (viewDocument)="onViewDocument($event)"
        (deleteDocument)="onDeleteDocument($event)" />

      @if (showViewer()) {
        <div class="documentos-manager__viewer-overlay" (click)="closeViewer()">
          <div class="documentos-manager__viewer" (click)="$event.stopPropagation()">
            <div class="documentos-manager__viewer-header">
              <h4>{{ viewerFileName() }}</h4>
              <button type="button" class="documentos-manager__viewer-close" (click)="closeViewer()">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                </svg>
              </button>
            </div>
            <iframe [src]="viewerUrl()" class="documentos-manager__viewer-iframe" title="Visor de documento"></iframe>
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .documentos-manager {
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
    }

    .documentos-manager__header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .documentos-manager__title {
      margin: 0;
      font-size: 1.25rem;
      font-weight: 600;
      color: var(--color-text-primary);
    }

    .documentos-manager__count {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
      background: var(--color-bg-secondary);
      padding: 0.25rem 0.75rem;
      border-radius: var(--radius-full);
    }

    .documentos-manager__upload {
      margin-bottom: 1rem;
    }

    .documentos-manager__action {
      display: flex;
      justify-content: flex-end;
    }

    .btn {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      padding: 0.75rem 1.5rem;
      border: none;
      border-radius: var(--radius-md);
      font-weight: 500;
      cursor: pointer;
      transition: all 0.2s ease;
    }

    .btn--primary {
      background: var(--color-primary);
      color: white;
    }

    .btn--primary:hover:not(:disabled) {
      background: var(--color-primary-dark);
    }

    .btn--primary:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }

    .spinner {
      width: 16px;
      height: 16px;
      border: 2px solid white;
      border-top-color: transparent;
      border-radius: 50%;
      animation: spin 0.8s linear infinite;
    }

    @keyframes spin {
      to { transform: rotate(360deg); }
    }

    .documentos-manager__success {
      padding: 0.75rem 1rem;
      background: var(--color-success-bg);
      border: 1px solid var(--color-success);
      border-radius: var(--radius-md);
      color: var(--color-success);
    }

    .documentos-manager__error {
      padding: 0.75rem 1rem;
      background: var(--color-error-bg);
      border: 1px solid var(--color-error);
      border-radius: var(--radius-md);
      color: var(--color-error);
    }

    .documentos-manager__viewer-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.8);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
    }

    .documentos-manager__viewer {
      width: 90%;
      height: 90%;
      background: white;
      border-radius: var(--radius-lg);
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }

    .documentos-manager__viewer-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1rem;
      border-bottom: 1px solid var(--color-border);
      background: var(--color-bg);
    }

    .documentos-manager__viewer-header h4 {
      margin: 0;
      font-size: 1rem;
      font-weight: 600;
      color: var(--color-text-primary);
    }

    .documentos-manager__viewer-close {
      width: 32px;
      height: 32px;
      border: none;
      background: var(--color-bg-secondary);
      border-radius: var(--radius-md);
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .documentos-manager__viewer-close:hover {
      background: var(--color-error);
      color: white;
    }

    .documentos-manager__viewer-close svg {
      width: 20px;
      height: 20px;
    }

    .documentos-manager__viewer-iframe {
      flex: 1;
      border: none;
    }
  `]
})
export class DocumentosManagerComponent implements OnInit {
  private documentoService = inject(DocumentoService);

  proyectoId = input.required<string>();
  documentosChange = output<DocumentoItem[]>();

  currentFile = signal<File | null>(null);
  isUploading = signal(false);
  successMessage = signal('');
  errorMessage = signal('');

  showViewer = signal(false);
  viewerUrl = signal<string>('');
  viewerFileName = signal<string>('');

  documentos = signal<DocumentoItem[]>([]);

  ngOnInit() {
    this.cargarDocumentos();
  }

  cargarDocumentos() {
    this.documentoService.listarPorProyecto(this.proyectoId()).subscribe({
      next: (docs) => {
        this.documentos.set(docs.map(d => ({
          id: d.id,
          nombre: d.nombre,
          tipo: d.tipo,
          proyectoId: d.proyectoId,
          tamanho: d.tamanho
        })));
        this.documentosChange.emit(this.documentos());
      },
      error: (err) => console.error('Error cargando documentos:', err)
    });
  }

  onFileSelected(file: File) {
    this.currentFile.set(file);
    this.clearMessages();
  }

  subirDocumento() {
    const file = this.currentFile();
    if (!file) return;

    this.isUploading.set(true);
    this.clearMessages();

    const tipoDocumento = this.detectarTipoDocumento(file.name);

    this.documentoService.subirDocumento(this.proyectoId(), tipoDocumento, file).subscribe({
      next: (response) => {
        this.isUploading.set(false);
        this.successMessage.set(`Documento "${file.name}" subido exitosamente`);
        this.currentFile.set(null);
        this.cargarDocumentos();
      },
      error: (err) => {
        this.isUploading.set(false);
        this.errorMessage.set(`Error al subir documento: ${err.message || 'Error desconocido'}`);
      }
    });
  }

  onViewDocument(documentoId: string) {
    this.documentoService.obtenerViewerUrl(documentoId).subscribe({
      next: (response) => {
        this.viewerUrl.set(response.viewerUrl);
        const doc = this.documentos().find(d => d.id === documentoId);
        this.viewerFileName.set(doc?.nombre || 'Documento');
        this.showViewer.set(true);
      },
      error: () => {
        this.errorMessage.set('No se pudo obtener el visor del documento');
      }
    });
  }

  onDeleteDocument(documentoId: string) {
    if (!confirm('¿Está seguro de eliminar este documento?')) {
      return;
    }

    this.documentoService.eliminarDocumento(documentoId).subscribe({
      next: () => {
        this.successMessage.set('Documento eliminado');
        this.cargarDocumentos();
      },
      error: (err) => {
        this.errorMessage.set(`Error al eliminar documento: ${err.message || 'Error desconocido'}`);
      }
    });
  }

  closeViewer() {
    this.showViewer.set(false);
    this.viewerUrl.set('');
  }

  private detectarTipoDocumento(fileName: string): string {
    const name = fileName.toUpperCase();
    if (name.includes('P-005A') || name.includes('FICHA')) return 'P-005A';
    if (name.includes('P-014A') || name.includes('INFRAESTRUCTURA')) return 'P-014A';
    if (name.includes('P-027A') || name.includes('ARRENDAMIENTO')) return 'P-027A';
    if (name.includes('P-032A') || name.includes('COMODATO')) return 'P-032A';
    if (name.includes('P-035A') || name.includes('COMPRA')) return 'P-035A';
    if (name.includes('P-041A') || name.includes('PERMUTA')) return 'P-041A';
    if (name.includes('P-051A') || name.includes('NEGOCIACION')) return 'P-051A';
    if (name.includes('P-056A') || name.includes('CAPITALIZ')) return 'P-056A';
    if (name.includes('P-005F') || name.includes('FOVIS')) return 'P-005F';
    if (name.includes('P-015F')) return 'P-015F';
    if (name.includes('P-024F')) return 'P-024F';
    if (name.includes('P-030F')) return 'P-030F';
    if (name.includes('P-040F')) return 'P-040F';
    if (name.includes('P-050F')) return 'P-050F';
    return 'DOCUMENTO';
  }

  private clearMessages() {
    this.successMessage.set('');
    this.errorMessage.set('');
  }
}