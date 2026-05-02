import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * 401 en rutas API (excepto login): sesión inválida o expirada → login con returnUrl.
 */
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const isLogin = req.url.includes('/auth/login');
      if (err.status === 401 && !isLogin) {
        auth.clearToken();
        const current = router.url;
        const returnUrl =
          current && !current.startsWith('/login') ? current : '/dashboard';
        void router.navigate(['/login'], {
          queryParams: { returnUrl },
          replaceUrl: false
        });
      }
      return throwError(() => err);
    })
  );
};
