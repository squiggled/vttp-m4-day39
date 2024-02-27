import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GiphyService } from '../giphy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrl: './view0.component.css'
})
export class View0Component implements OnInit{
  private router = inject(Router);

  constructor (private fb:FormBuilder){}
  private giphySvc = inject(GiphyService);

  form!:FormGroup;

  ngOnInit(): void {
    this.form=this.fb.group({
      search: ['', [Validators.required]],
    })
  }

  process(){
    console.log(this.form.value.search);
    this.giphySvc.search(this.form.value.search);
    this.router.navigate(['/list']);
  }

}
