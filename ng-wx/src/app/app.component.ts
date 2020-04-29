import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ng-wx';
  user: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    console.log('App index init startd.')
  }

  getUser() {
    this.http.get('user/all').subscribe(res => this.user = res);
  }

}
