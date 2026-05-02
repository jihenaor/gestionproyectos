import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../../shared/organisms/header/header.component';
import { SidebarComponent } from '../../shared/organisms/sidebar/sidebar.component';
import { ToastContainerComponent } from '../../shared/molecules/toast/toast.component';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, SidebarComponent, ToastContainerComponent],
  template: `
    <div class="app-layout">
      <app-header />
      <div class="app-body">
        <app-sidebar />
        <main class="app-main">
          <router-outlet />
        </main>
      </div>
      <app-toast-container />
    </div>
  `,
  styles: [`
    .app-layout {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
    .app-body {
      display: flex;
      flex: 1;
    }
    .app-main {
      flex: 1;
      padding: var(--spacing-xl);
      background: var(--color-background-secondary);
      overflow-y: auto;
    }
  `]
})
export class AppShellComponent {}
