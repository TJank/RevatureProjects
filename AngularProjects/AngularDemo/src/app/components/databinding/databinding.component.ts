import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-databinding',
  templateUrl: './databinding.component.html',
  styleUrls: ['./databinding.component.css']
})
export class DatabindingComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  someProp:string = "{{someProp}} -- is also being binded";
  bindMe:string = "I'm being binded from the back to the front!";
  yourFavoriteBoyband:string = "Beastie Boys";

  colorChanged:boolean = true;
  isItHidden:boolean = true;

  doStuff() {
    let p = document.getElementById("eventBound");

    if(this.colorChanged = !this.colorChanged) {
      p.style.backgroundColor = "red";
    } else {
      p.style.backgroundColor = "limegreen";
    }
  }

  hideMe() {
    this.isItHidden = !this.isItHidden;
  }

}
