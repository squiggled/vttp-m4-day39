import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchResult } from '../models';
import { Observable } from 'rxjs';
import { GiphyService } from '../giphy.service';
import { Comment } from '../models';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrl: './view2.component.css'
})
export class View2Component implements OnInit{

  private giphySvc = inject(GiphyService);
  private router = inject(Router);
  constructor(private activatedRoute: ActivatedRoute) { }
  displayGif!:SearchResult
  gifId!: string;
  gifDeets$!: Observable<SearchResult>;
  commentsObs$!:Observable<Comment[]>;


  ngOnInit(): void {
    this.gifId = this.activatedRoute.snapshot.params['id'];
    this.giphySvc.getGif(this.gifId);
    this.gifDeets$ = this.giphySvc.gifDeets$;
    this.commentsObs$=this.giphySvc.commentsObs$;

  }
  addComment(){
    this.router.navigate(['/comment', this.gifId]);
  }

}
