import {Component, OnInit} from '@angular/core';
import {PointsHandlerService} from '../Services/points-handler.service';
import {PointEntity} from '../model/point';
import {Router} from '@angular/router';
import {isNumeric} from 'tslint';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  canvasName: string;
  formName: string;
  public points: PointEntity[];
  point: PointEntity = new PointEntity();
  errorR = '';
  errorX = '';
  errorY = '';
  what = '';


  constructor(public pointsHandlerService: PointsHandlerService, public router: Router) {
  }

  ngOnInit(): void {
    this.formName = 'Show';
    this.canvasName = 'Show';
    this.fill();
    // this.what = this.pointsHandlerService.getPoints();
    // get dummy data
    /*this.http.get('./dummy-data.json')
      .map((response: Response) => response.json())
      .subscribe(data => {
        // set items to json response
        this.allItems = data;
      });*/

  }

   fill(): void{
      const ans = this.pointsHandlerService.getPoints();
      document.getElementById('main-table').innerHTML = ans;
    }

  clickButton(): void {
    console.log(this.point.x + 'aaa' + this.point.y);
    this.point.username = localStorage.getItem('currentUser');
    console.log('Send is clicked!');
    if (this.validate()) {
      this.point.result = this.check();
      this.addPoint();
    }
  }

  check(): boolean {
    return (this.point.x < 0 && this.point.x > -(this.point.r / 2) && (this.point.y > 0 && this.point.y < this.point.r))
      || ((((this.point.x * this.point.x) + (this.point.y * this.point.y)) <= ((this.point.r * this.point.r)))
        && (this.point.x) > 0 && this.point.x < this.point.r)
      || ((this.point.x > 0 && this.point.x < this.point.r && this.point.y < (this.point.x - this.point.r)));
  }

  addPoint(): void {
    const table = document.getElementsByTagName('table').item(0);
    const row = table.insertRow(table.rows.length);
    row.insertCell(0).innerHTML = String(this.point.x);
    row.insertCell(1).innerHTML = String(this.point.y);
    row.insertCell(2).innerHTML = String(this.point.r);
    row.insertCell(3).innerHTML = String(this.point.result);
    this.pointsHandlerService.addPoint(this.point);
  }

  click(id: string): void {
    // document.getElementById('form').setAttribute('style', 'display: block');
    const form = document.getElementById(id);
    if (form.style.display === 'none' || form.style.display === '') {
      console.log(form.style.display);
      form.style.display = 'block';
      if (id === 'canvas') {
        this.canvasName = 'Hide';
      } else {
        this.formName = 'Hide';
      }
    } else {
      form.style.display = 'none';
      if (id === 'canvas') {
        this.canvasName = 'Show';
      } else {
        this.formName = 'Show';
      }
    }
    console.log('clicked');
  }

  validateR(): void {
    this.errorR = this.validateForm(this.point.r);
  }
  validateX(): void {
    this.errorX = this.validateForm(this.point.x);
  }
  validateY(): void {
    this.errorY = this.validateForm(this.point.y);
  }
  validateForm(val): string{
    if (val <= -3 || val >= 3){
      return 'Значение должно быть (-3..3)';
    }
    else{
      return '';
    }
  }

  validate(): boolean {
    console.log(this.point);
    return (this.point.x <= 3 && this.point.x >= -3)
      && (this.point.y <= 3 && this.point.y >= -3)
      && (this.point.r <= 3 && this.point.r >= -3);
  }

  setResult(): void {
    // this.point.result = this.check();
  }

  save(): void {
    this.setResult();
    this.pointsHandlerService.addPoint(this.point);
  }

  clear(): void {
    this.pointsHandlerService.clear();
    this.fill();

    // document.getElementById('point-history-card').innerHTML = '';
    // this.router.navigate(['/clear']);
  }

  logout(): void {
    this.pointsHandlerService.logOut();
  }

}
