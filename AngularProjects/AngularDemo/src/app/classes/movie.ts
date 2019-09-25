/*
* This is how we create a class in Typescript. Note that
* if you don't export this class, you won't be able to
* import it elsewhere
*/

export class Movie {

    /**
     * This is how we create a constructor for our class.
     * We must use the constructor keyword.
     */
    constructor(id:number, movie_name:string, genre_id:number, academy_id:number){
        this.id = id;
        this.movie_name = movie_name;
        this.genre_id = genre_id;
        this.academy_id = academy_id;
    }

    /**
     * This is how we declare variables in Typescript.
     * We use an access modifier (either public or private)
     * and specify the type of the variable.
     */
    public id:number;
    public movie_name:string;
    public genre_id:number;
    public academy_id:number;

    // public obj:Object;
    // public bool:boolean;
    // public n:null;
    // public u:undefined;
    // public f:Function;

    /**
     * When declaring functions in Typescript, we cannot
     * use the "function" keyword. You should also note 
     * that you must use the "this" keyword when referring
     * to fields or functions in this class.
     */
    public getId() {return this.id;}
    public getName() {return this.movie_name;}

}


