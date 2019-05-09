/* "Barrel" of Http Interceptors */
import {HTTP_INTERCEPTORS} from '@angular/common/http';

import {XhrInterceptor} from './xhr-interceptor.service';


/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
  {provide: HTTP_INTERCEPTORS, useClass: EnsureHostInterceptor, multi: true}
];
