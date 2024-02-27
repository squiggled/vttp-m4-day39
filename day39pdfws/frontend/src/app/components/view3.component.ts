import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GiphyService } from '../giphy.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view3',
  templateUrl: './view3.component.html',
  styleUrl: './view3.component.css'
})
export class View3Component implements OnInit{

  constructor (private fb:FormBuilder){}
  private giphySvc = inject(GiphyService);
  private activatedRoute = inject(ActivatedRoute);
  private router = inject(Router);

  commentForm!:FormGroup;
  gifId!:string;

  ngOnInit(): void {
    this.gifId = this.activatedRoute.snapshot.params['id'];
    this.commentForm=this.fb.group({
      comment: ['', [Validators.required]],
    })
  }

  addComment(){
    console.log(this.gifId);
    this.giphySvc.addMongoComment(this.gifId, this.commentForm.value.comment)
     this.router.navigate(['/details', this.gifId])
  }

}
