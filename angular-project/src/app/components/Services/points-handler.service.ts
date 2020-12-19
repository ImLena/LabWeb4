import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {PointEntity} from '../model/point';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppComponent} from '../../app.component';

@Injectable()
export class PointsHandlerService {
  constructor(public http: Http) {
  }

  addPoint(point: PointEntity): void {
    let ans: string;
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/x-www-form-urlencoded');
    const data = {
      x: point.x,
      y: point.y,
      r: point.r,
      result: point.result,
      username: point.username
    };
    console.log(data);
    const request = new XMLHttpRequest();
    // tslint:disable-next-line:max-line-length
    const arr = 'x=' + data.x + '&y=' + data.y + '&r=' + data.r + '&result=' + encodeURIComponent(data.result) + '&username=' + encodeURIComponent(point.username);
    request.open('POST', AppComponent.API_URL + '/main', false);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(arr);
    if ((request.status !== 200) && (request.status !== 403)) {
      console.log(request.status + ': ' + request.statusText);
      console.log(request.response);
      console.log(request.responseText);
    } else {
      console.log(request.response);
      ans = request.response;
      console.log('AAAAAAAAAAA ' + ans);
    }
  }

  public getPoints(): string {
    // let ans: string;
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/x-www-form-urlencoded');
    const data = {
      username: localStorage.getItem('currentUser')
    };
    console.log(data.username);
    const request = new XMLHttpRequest();
    const arr = 'username=' + encodeURIComponent(data.username);
    request.open('POST', AppComponent.API_URL + '/receive', false);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(arr);
    const ans = request.responseText;
    /*if ((request.status !== 200) && (request.status !== 403)) {
      console.log(request.status + ': ' + request.statusText);
      console.log(request.response);
      console.log(request.responseText);
    } else {
      console.log(request.response);
      ans = request.response;
      console.log('AAAAAAAAAAA ' + ans);
      return ans;
    }*/
    return ans;
  }

  clear(): void {
    console.log('points-handler clear');
    const request = new XMLHttpRequest();
    // tslint:disable-next-line:max-line-length
    const arr = 'username=' + encodeURIComponent(localStorage.getItem('currentUser'));
    request.open('POST', AppComponent.API_URL + '/clear', false);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(arr);
    console.log(request.response);
    console.log(request.responseText);
    // load from bd?
  }

  logOut(): void {
    localStorage.removeItem('currentUser');
  }
}
