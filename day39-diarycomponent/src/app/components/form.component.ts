import { DatePipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Diary } from '../models';
// import { DiaryService } from '../diary-service.service';
import { DiaryStore } from '../diary-store';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})
export class FormComponent implements OnInit{
  
  form!:FormGroup;
  private fb = inject(FormBuilder);
  // private diarySvc = inject(DiaryService);
  private diaryStore = inject(DiaryStore);

  ngOnInit(): void {
    this.form = this.fb.group({
      
    })
    this.form = this.fb.group({
      date: this.fb.control<Date>(new Date, [ Validators.required ]),
      entry: this.fb.control<string>('', [ Validators.required, Validators.email ]),
      type: this.fb.control<string>('travel', [ Validators.required ])
    })
  }
  process(){
    const diary : Diary = this.form.value;
    // this.diarySvc.addEntry(diary);
    this.diaryStore.addDiary(diary)
    this.form.reset();
  }

}
