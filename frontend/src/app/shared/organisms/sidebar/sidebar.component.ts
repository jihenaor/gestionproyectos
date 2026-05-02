import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

interface NavItem {
  label: string;
  route: string;
  icon: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <aside class="sidebar">
      <nav class="sidebar__nav">
        @for (item of navItems; track item.route) {
          <a
            [routerLink]="item.route"
            routerLinkActive="is-active"
            class="sidebar__link">
            <span class="sidebar__icon" [innerHTML]="item.icon"></span>
            <span class="sidebar__label">{{ item.label }}</span>
          </a>
        }
      </nav>
      <div class="sidebar__footer">
        <span class="sidebar__version">v1.0.0</span>
      </div>
    </aside>
  `,
  styles: [`
    .sidebar {
      width: 16rem;
      background: var(--color-background);
      border-right: 1px solid var(--color-border);
      display: flex;
      flex-direction: column;
      padding: var(--spacing-lg) 0;
    }
    .sidebar__nav {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-xs);
      padding: 0 var(--spacing-sm);
    }
    .sidebar__link {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--radius-md);
      color: var(--color-text-secondary);
      text-decoration: none;
      transition: all 0.15s ease;
    }
    .sidebar__link:hover {
      background: var(--color-background-secondary);
      color: var(--color-text-primary);
    }
    .sidebar__link.is-active {
      background: var(--color-info-bg);
      color: var(--color-info);
    }
    .sidebar__icon {
      display: flex;
      align-items: center;
      width: 1.5rem;
      height: 1.5rem;
    }
    .sidebar__icon :global(svg) {
      width: 100%;
      height: 100%;
    }
    .sidebar__label {
      font-size: 0.875rem;
      font-weight: 500;
    }
    .sidebar__footer {
      margin-top: auto;
      padding: var(--spacing-lg) var(--spacing-md);
      border-top: 1px solid var(--color-border);
    }
    .sidebar__version {
      font-size: 0.75rem;
      color: var(--color-text-tertiary);
    }
  `]
})
export class SidebarComponent {
  navItems: NavItem[] = [
    {
      label: 'Dashboard',
      route: '/dashboard',
      icon: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1"/><rect x="14" y="3" width="7" height="5" rx="1"/><rect x="14" y="12" width="7" height="9" rx="1"/><rect x="3" y="16" width="7" height="5" rx="1"/></svg>'
    },
    {
      label: 'Proyectos',
      route: '/projects',
      icon: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 19a2 2 0 01-2 2H4a2 2 0 01-2-2V5a2 2 0 012-2h5l2 3h9a2 2 0 012 2z"/></svg>'
    },
    {
      label: 'Proyectos FOVIS',
      route: '/fovis',
      icon: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"/><polyline points="9,22 9,12 15,12 15,22"/></svg>'
    },
    {
      label: 'Reportes',
      route: '/reports',
      icon: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/></svg>'
    },
    {
      label: 'Circular superintendencia',
      route: '/xml-generator',
      icon: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/><polyline points="14,2 14,8 20,8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10,9 9,9 8,9"/></svg>'
    }
  ];
}