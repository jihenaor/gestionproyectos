import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface DocumentoMetadata {
  id: string;
  nombre: string;
  tipo: string;
  proyectoId: string;
  tamanho: number;
}

@Injectable({ providedIn: 'root' })
export class DocumentoService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/v1/documentos`;

  subirDocumento(proyectoId: string, tipoDocumento: string, file: File): Observable<DocumentoMetadata> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<DocumentoMetadata>(
      `${this.baseUrl}/upload/${proyectoId}/${tipoDocumento}`,
      formData
    );
  }

  listarPorProyecto(proyectoId: string): Observable<DocumentoMetadata[]> {
    return this.http.get<DocumentoMetadata[]>(`${this.baseUrl}/proyecto/${proyectoId}`);
  }

  eliminarDocumento(documentoId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${documentoId}`);
  }

  obtenerViewerUrl(documentoId: string): Observable<{ viewerUrl: string }> {
    return this.http.get<{ viewerUrl: string }>(`${this.baseUrl}/${documentoId}/viewer`);
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}