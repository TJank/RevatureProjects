import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/classes/movie';

@Component({
  selector: 'app-directives',
  templateUrl: './directives.component.html',
  styleUrls: ['./directives.component.css']
})
export class DirectivesComponent implements OnInit {

  /**
   * Let's inject our movie service into our Directives component
   */
  constructor(private ms:MovieService) { }
  data:Movie[] = [];
  /**
   * We'll take advantage of this ngOnInit() function to 
   * automatically pull our movies into this component
   * when it is instantiated.
   * 
   * ngOnInit is just one of many Angular "Lifecycle Hooks"
   * that is invoked during a component's life cycle.
   */
  ngOnInit() {
    this.getAllMovies();
  }
  
  getAllMovies() {
    this.ms.getAllMovies().subscribe(
      
      /**
       * When Working with observables, we have access to two
       * callback functions: one that executes in case of success
       * and another that executes in case of failure.
       */
      data => {
        this.data = data;
        console.log(this.data);
      },
      error => {
        error = "Sorry. couldn't get those sweet movies!";
        console.log(error);
      }
    )
  }

  visible:boolean =  true;
  

  styleObject = {
    color:"pink",
    backgroundColor: "lavender"
  }


}
