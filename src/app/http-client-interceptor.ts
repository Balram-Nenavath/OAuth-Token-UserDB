import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpClientInterceptor implements HttpInterceptor {
  private excludedUrls: string[] = ['http://localhost:8089/oauth/token'];
  constructor(private $localStorage: LocalStorageService) {

  }

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.$localStorage.retrieve("authenticationToken");
    console.log('authentication token ' + token);

    if (this.isUrlExcluded(req.url)) {
      return next.handle(req);
    }


    if (token) {
      const headers = req.headers
      .set("Authorization", "Bearer " + token)
      .set("Access-Control-Allow-Origin", "*")
      .set("Content-Type", "application/json");
      const cloned = req.clone({ headers });
      return next.handle(cloned);
    }
    else {
      return next.handle(req);
    }
  }

  private isUrlExcluded(url: string): boolean {
    return this.excludedUrls.some((excludedUrl) =>
      url.includes(excludedUrl)
    );
  }

}
