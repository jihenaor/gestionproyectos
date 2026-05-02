import { Injectable, inject, signal, computed } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { ApiService } from './api.service';
import { ProyectoResponse } from '../models/proyecto.model';

export interface Proyecto {
  id: string;
  codigo: string;
  nombre: string;
  modalidad: string;
  modalidadInversion: string;
  valorTotal: number;
  estado: string;
  fechaCreacion: string;
  estructurasCompletadas: Set<string>;
}

export interface EstructuraEstado {
  codigo: string;
  nombre: string;
  estado: 'completed' | 'pending' | 'not-applicable' | 'overdue';
  fechaUltimaActualizacion?: Date;
}

function mapProyectoResponse(r: ProyectoResponse): Proyecto {
  const modalidad = r.modalidad ?? r.modalidadInversion;
  return {
    id: String(r.id),
    codigo: r.codigo,
    nombre: r.nombre,
    modalidad,
    modalidadInversion: r.modalidadInversion,
    valorTotal: r.valorTotal,
    estado: r.estado,
    fechaCreacion: r.fechaCreacion,
    estructurasCompletadas: new Set(r.estructurasCompletadas ?? [])
  };
}

@Injectable({ providedIn: 'root' })
export class ProjectStateService {
  private readonly api = inject(ApiService);

  private _projects = signal<Proyecto[]>([]);
  private _selectedProject = signal<Proyecto | null>(null);
  private _loading = signal(false);
  private _filterText = signal('');

  readonly projects = this._projects.asReadonly();
  readonly selectedProject = this._selectedProject.asReadonly();
  readonly loading = this._loading.asReadonly();
  readonly filterText = this._filterText.asReadonly();

  readonly filteredProjects = computed(() => {
    const filter = this._filterText().toLowerCase();
    if (!filter) return this._projects();
    return this._projects().filter(p =>
      p.codigo.toLowerCase().includes(filter) ||
      p.nombre.toLowerCase().includes(filter)
    );
  });

  readonly projectCount = computed(() => this._projects().length);

  readonly requiredStructures = signal<string[]>([
    'P-001A', 'P-002A', 'P-003A', 'P-004C', 'P-005A',
    'P-011A', 'P-013A'
  ]);

  readonly optionalStructures = signal<string[]>([
    'P-011B', 'P-012A', 'P-023A', 'P-024A',
    'P-026A', 'P-031A', 'P-034A', 'P-040A',
    'P-050A', 'P-055A'
  ]);

  readonly completedCount = computed(() =>
    this._projects().filter(p => p.estado === 'Completado').length
  );

  readonly pendingCount = computed(() =>
    this._projects().filter(p => p.estado !== 'Completado').length
  );

  loadProjects(projects: Proyecto[]): void {
    this._projects.set(projects);
  }

  /** GET /api/v1/proyectos — los datos vienen del backend (H2 con perfil `local` o AS400 con `as400`). */
  reloadFromServer(page = 0, size = 100): void {
    this._loading.set(true);
    this.api
      .getProyectos(page, size)
      .pipe(finalize(() => this._loading.set(false)))
      .subscribe(res => this.loadProjects(res.content.map(mapProyectoResponse)));
  }

  addProject(proyecto: Proyecto): void {
    this._projects.update(projects => [...projects, proyecto]);
  }

  updateProject(proyecto: Proyecto): void {
    this._projects.update(projects =>
      projects.map(p => p.id === proyecto.id ? proyecto : p)
    );
  }

  deleteProject(id: string): void {
    this._projects.update(projects => projects.filter(p => p.id !== id));
    if (this._selectedProject()?.id === id) {
      this._selectedProject.set(null);
    }
  }

  selectProject(project: Proyecto | null): void {
    this._selectedProject.set(project);
  }

  setFilter(text: string): void {
    this._filterText.set(text);
  }

  setLoading(isLoading: boolean): void {
    this._loading.set(isLoading);
  }

  getProjectById(id: string): Proyecto | undefined {
    return this._projects().find(p => p.id === id);
  }

  getEstructurasEstado(projectId: string): EstructuraEstado[] {
    const project = this.getProjectById(projectId);
    if (!project) return [];

    const estructuras: EstructuraEstado[] = [];
    const allStructures = [
      ...this.requiredStructures(),
      ...this.optionalStructures()
    ];

    const estructuraNames: Record<string, string> = {
      'P-001A': 'Datos Generales',
      'P-002A': 'Cronograma',
      'P-003A': 'Localización',
      'P-004C': 'Fuentes y Centros',
      'P-005A': 'Ficha Técnica (PDF)',
      'P-011A': 'Cobertura Proyectada',
      'P-011B': 'Cobertura Ejecutada',
      'P-012A': 'Seguimiento',
      'P-013A': 'Aspectos Infraestructura',
      'P-023A': 'Aspectos Fondos de Crédito',
      'P-024A': 'Cartera por Edades',
      'P-026A': 'Arrendamiento',
      'P-031A': 'Comodato',
      'P-034A': 'Compra',
      'P-040A': 'Permuta',
      'P-050A': 'Negociación Acciones',
      'P-055A': 'Capitalizaciones',
    };

    for (const code of allStructures) {
      estructuras.push({
        codigo: code,
        nombre: estructuraNames[code] || code,
        estado: project.estructurasCompletadas.has(code) ? 'completed' : 'pending'
      });
    }

    return estructuras;
  }

  markStructureCompleted(projectId: string, structureCode: string): void {
    this._projects.update(projects =>
      projects.map(p => {
        if (p.id === projectId) {
          const newStructuras = new Set(p.estructurasCompletadas);
          newStructuras.add(structureCode);
          return { ...p, estructurasCompletadas: newStructuras };
        }
        return p;
      })
    );
  }

  getCompletionPercentage(projectId: string): number {
    const project = this.getProjectById(projectId);
    if (!project) return 0;
    const total = this.requiredStructures().length;
    const completed = project.estructurasCompletadas.size;
    return Math.round((completed / total) * 100);
  }
}