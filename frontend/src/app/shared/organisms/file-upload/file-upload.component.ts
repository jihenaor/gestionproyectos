import { Component, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="file-upload"
         [class.dragover]="isDragOver()"
         [class.has-file]="selectedFile()"
         [class.error]="hasError()"
         (dragover)="onDragOver($event)"
         (dragleave)="onDragLeave($event)"
         (drop)="onDrop($event)">

      @if (!selectedFile()) {
        <div class="file-upload__placeholder">
          <svg class="file-upload__icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
          </svg>
          <p class="file-upload__text">
            Arrastra el archivo PDF aquí o
            <label class="file-upload__browse">
              busca tu archivo
              <input type="file"
                     accept="application/pdf"
                     (change)="onFileSelect($event)"
                     class="file-upload__input" />
            </label>
          </p>
          <p class="file-upload__hint">Formato: PDF | Tamaño máximo: 50MB</p>
        </div>
      } @else {
        <div class="file-upload__file-info">
          <svg class="file-upload__file-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
          </svg>
          <div class="file-upload__details">
            <span class="file-upload__name">{{ selectedFile()!.name }}</span>
            <span class="file-upload__size">{{ formatSize(selectedFile()!.size) }}</span>
          </div>
          <button type="button" class="file-upload__remove" (click)="removeFile()" aria-label="Eliminar archivo">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>
        </div>
      }

      @if (hasError()) {
        <p class="file-upload__error">{{ errorMessage() }}</p>
      }

@if (isUploading()) {
        <div class="file-upload__progress">
          <div class="file-upload__progress-bar" [style.width.%]="uploadProgressValue()"></div>
        </div>
        <span class="file-upload__progress-text">Subiendo... {{ uploadProgressValue() }}%</span>
      }
    </div>
  `,
  styles: [`
    .file-upload {
      border: 2px dashed var(--color-border);
      border-radius: var(--radius-lg);
      padding: 2rem;
      text-align: center;
      transition: all 0.2s ease;
      background: var(--color-bg-secondary);
    }

    .file-upload.dragover {
      border-color: var(--color-primary);
      background: var(--color-primary-light) / 10%;
    }

    .file-upload.has-file {
      border-style: solid;
      border-color: var(--color-success);
      background: var(--color-success) / 5%;
    }

    .file-upload.error {
      border-color: var(--color-error);
    }

    .file-upload__placeholder {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.5rem;
    }

    .file-upload__icon {
      width: 48px;
      height: 48px;
      color: var(--color-text-secondary);
    }

    .file-upload__text {
      color: var(--color-text-primary);
      margin: 0;
    }

    .file-upload__browse {
      color: var(--color-primary);
      cursor: pointer;
      text-decoration: underline;
    }

    .file-upload__browse:hover {
      color: var(--color-primary-dark);
    }

    .file-upload__input {
      display: none;
    }

    .file-upload__hint {
      color: var(--color-text-secondary);
      font-size: 0.875rem;
      margin: 0.5rem 0 0 0;
    }

    .file-upload__file-info {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 0.5rem;
    }

    .file-upload__file-icon {
      width: 40px;
      height: 40px;
      color: var(--color-error);
    }

    .file-upload__details {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
    }

    .file-upload__name {
      font-weight: 500;
      color: var(--color-text-primary);
      word-break: break-all;
    }

    .file-upload__size {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
    }

    .file-upload__remove {
      width: 32px;
      height: 32px;
      border: none;
      background: var(--color-bg-secondary);
      border-radius: var(--radius-md);
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      color: var(--color-text-secondary);
    }

    .file-upload__remove:hover {
      background: var(--color-error);
      color: white;
    }

    .file-upload__remove svg {
      width: 20px;
      height: 20px;
    }

    .file-upload__error {
      color: var(--color-error);
      font-size: 0.875rem;
      margin: 0.5rem 0 0 0;
    }

    .file-upload__progress {
      height: 4px;
      background: var(--color-border);
      border-radius: 2px;
      margin-top: 1rem;
      overflow: hidden;
    }

    .file-upload__progress-bar {
      height: 100%;
      background: var(--color-primary);
      transition: width 0.3s ease;
    }

    .file-upload__progress-text {
      font-size: 0.875rem;
      color: var(--color-text-secondary);
      margin-top: 0.25rem;
      display: block;
    }
  `]
})
export class FileUploadComponent {
  accept = input<string>('application/pdf');
  maxSizeMb = input<number>(50);
  disabled = input<boolean>(false);

  fileSelected = output<File>();
  uploadStart = output<void>();
  uploadComplete = output<void>();
  uploadError = output<string>();

  isDragOver = signal(false);
  selectedFile = signal<File | null>(null);
  isUploading = signal(false);
  uploadProgressValue = signal(0);
  hasError = signal(false);
  errorMessage = signal('');

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    if (!this.disabled()) {
      this.isDragOver.set(true);
    }
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);

    if (this.disabled()) return;

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.processFile(files[0]);
    }
  }

  onFileSelect(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.processFile(input.files[0]);
      input.value = '';
    }
  }

  private processFile(file: File) {
    this.hasError.set(false);
    this.errorMessage.set('');

    if (file.type !== 'application/pdf') {
      this.hasError.set(true);
      this.errorMessage.set('Solo se permiten archivos PDF');
      return;
    }

    const maxBytes = this.maxSizeMb() * 1024 * 1024;
    if (file.size > maxBytes) {
      this.hasError.set(true);
      this.errorMessage.set(`El archivo excede el tamaño máximo de ${this.maxSizeMb()}MB`);
      return;
    }

    this.selectedFile.set(file);
    this.fileSelected.emit(file);
  }

  removeFile() {
    this.selectedFile.set(null);
    this.hasError.set(false);
    this.errorMessage.set('');
  }

  setUploading(uploading: boolean) {
    this.isUploading.set(uploading);
  }

  setProgress(progress: number) {
    this.uploadProgressValue.set(progress);
  }

  setError(message: string) {
    this.hasError.set(true);
    this.errorMessage.set(message);
  }

  clearError() {
    this.hasError.set(false);
    this.errorMessage.set('');
  }

  formatSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}