export const environment = {
  production: false,
  /** Relativo al origen (ng serve + proxy → backend). */
  apiUrl: '/api/v1',
  /** Login JWT: context-path `/api` + `/auth/login` (no va bajo `/v1`). */
  authLoginUrl: '/api/auth/login',
  codigoSistemaSec: '196',
  /** URL del API externo de Supersubsidio/CIEF para parametrización */
  ciefApiUrl: 'http://10.25.2.135/cief/api'
};