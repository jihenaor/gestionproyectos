export interface TokenPayload {
  sub: string;
  name?: string;
  username?: string;
  cedula?: string;
  ascodarea?: string;
  cacodcandi?: string | number;
  nombreCompleto?: string;
  prcodproce?: number[];
  roles?: string[];
  role?: string;
  exp?: number;
  iat?: number;
}

export interface UserInfo {
  login: string;
  name: string;
  cedula: string;
  ascodarea: string;
  cacodcandi: string | number;
  nombreCompleto: string;
  prcodproce: number[];
  roles: string[];
  tokenExpiration: string;
}