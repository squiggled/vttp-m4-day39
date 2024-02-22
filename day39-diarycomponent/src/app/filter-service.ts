import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

@Injectable()
export class FilterService{

private filterSubject = new Subject<string>();
filter$ = this.filterSubject.asObservable();
    
  setFilter(category: string) {
    this.filterSubject.next(category); //list will sub to this
  }

}