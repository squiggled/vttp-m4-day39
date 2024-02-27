import { Component, OnInit, inject } from '@angular/core';
import { GiphyService } from '../giphy.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrl: './view1.component.css'
})
export class View1Component implements OnInit{
    
  searchObs$!: Observable<any>;
  private giphySvc = inject(GiphyService);

  ngOnInit(): void {
    this.searchObs$= this.giphySvc.searchObs$;
  }
  loadMore(){
    // console.log("number offset in view1 component: ", offset);
    this.giphySvc.addOffSet();
  }
  loadLess(){
    this.giphySvc.decOffSet();
  }

}
