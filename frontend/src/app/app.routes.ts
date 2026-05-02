import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/login/login.component').then(m => m.LoginComponent),
    title: 'Iniciar sesión - Gestión de Proyectos'
  },
  {
    path: '',
    loadComponent: () =>
      import('./core/layout/app-shell.component').then(m => m.AppShellComponent),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/projects/dashboard/dashboard.component').then(
            m => m.DashboardComponent
          ),
        title: 'Dashboard - Gestión de Proyectos'
      },
      {
        path: 'projects',
        loadComponent: () =>
          import('./features/projects/project-list/project-list.component').then(
            m => m.ProjectListComponent
          ),
        title: 'Proyectos - Gestión de Proyectos'
      },
      {
        path: 'projects/new',
        loadComponent: () =>
          import('./features/projects/project-form/project-form.component').then(
            m => m.ProjectFormComponent
          ),
        title: 'Nuevo Proyecto - Gestión de Proyectos'
      },
      {
        path: 'projects/:id',
        loadComponent: () =>
          import('./features/projects/project-detail/project-detail.component').then(
            m => m.ProjectDetailComponent
          ),
        title: 'Detalle del Proyecto - Gestión de Proyectos'
      },
      {
        path: 'projects/:id/edit',
        loadComponent: () =>
          import('./features/projects/project-form/project-form.component').then(
            m => m.ProjectFormComponent
          ),
        title: 'Editar Proyecto - Gestión de Proyectos'
      },
      {
        path: 'fovis',
        loadComponent: () =>
          import('./features/fovis/fovis-list/fovis-list.component').then(
            m => m.FovisListComponent
          ),
        title: 'Proyectos FOVIS - Gestión de Proyectos'
      },
      {
        path: 'reports',
        loadComponent: () =>
          import('./features/reports/reports-view/reports-view.component').then(
            m => m.ReportsViewComponent
          ),
        title: 'Reportes - Gestión de Proyectos'
      },
      {
        path: 'xml-generator',
        loadComponent: () =>
          import('./features/reports/xml-generator/xml-generator.component').then(
            m => m.XmlGeneratorComponent
          ),
        title: 'Generador XML - Gestión de Proyectos'
      },
      {
        path: '**',
        redirectTo: 'dashboard'
      }
    ]
  }
];
