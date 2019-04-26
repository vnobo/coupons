import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  principal: any;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get('/api/user/me/').subscribe(data => this.principal = data);
  }


}
