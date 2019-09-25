import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../classes/movie';

/**
 * This decorator specifies that this service may be 
 * injected into other components in this applicaion.
 */
@Injectable({
  providedIn: 'root'
})

/**
 * This movie service will be used to grab the movies
 * that are returned by our API. In order to do this we 
 * must inject an HttpClient object into our movie service.
 */
export class MovieService {

  /**
   * In order to use dependency injection with Angular,
   * we must include a private HttpClient parameter in
   * this constructor.
   */
  constructor(private http:HttpClient) { }

  getAllMovies():Observable<Movie[]> {
    return this.http.get("http://localhost:8088/ServletDemo/getflix/api/movies") as Observable<Movie[]>
  }
}
