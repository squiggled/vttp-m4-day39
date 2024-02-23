import { Component, ElementRef, OnDestroy, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StompService } from '../stomp-service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})
export class FormComponent implements OnInit, OnDestroy{
  //'file' must match the #name in the form 
  @ViewChild('file') imageFile!:ElementRef;

  constructor(private fb: FormBuilder){}
  
  private stompSvc = inject(StompService);
  sub!:Subscription;

  form!: FormGroup;
  url!:string;
  urlObs$!:Observable<string>;

  ngOnInit(): void {
    this.form = this.fb.group({
      user: this.fb.control<string>(''),
      description: this.fb.control<string>(''),
    })
    this.urlObs$ = this.stompSvc.url$;
    console.log("form component ",this.urlObs$);
    
    
  }
  process(){
    this.stompSvc.upload(this.form, this.imageFile);
  }
  ngOnDestroy(): void {
    this.sub.unsubscribe()
  }

}
