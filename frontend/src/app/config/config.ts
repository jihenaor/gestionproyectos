import { environment } from '../../environments/environment';

const API_BASE = environment.apiUrl ?? 'http://localhost:8080/api/v1';
export const URL_SERVICIOS = API_BASE.replace(/\/+$/, '');