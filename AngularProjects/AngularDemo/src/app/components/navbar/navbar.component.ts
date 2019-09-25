import { Component, OnInit } from '@angular/core';

/**
 * This is what we call a "decorator" in Angular. It is
 * used to specify metadata about our component. The 
 * "selector" specifies how we should reference this
 * component within a template or style sheet.
 * 
 * The "styleUrls" specify the location of the style
 * sheets for this component.
 */
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
