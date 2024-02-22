import { Component, OnDestroy, OnInit, inject } from '@angular/core';
// import { DiaryService } from '../diary-service.service';
import { Observable, Subscription, from } from 'rxjs';
import { Diary } from '../models';
import { DiaryStore } from '../diary-store';
import { FilterService } from '../filter-service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit, OnDestroy{
  ngOnDestroy(): void {
    // this.entries$.unsubscribe();
  }
  private filterSvc = inject(FilterService);
  private diaryStore = inject(DiaryStore)
  //when subscribing, we need to assign what the observable returns to a subscription
  //NOT a string variable even tho the data emitted is a string
  private filterSubscription!: Subscription;
  entries$!: Observable<Diary[]>;


  //an observable is a QUERY; 
  ngOnInit(): void {
    this.entries$ = this.diaryStore.getEntries;
    //we must add the line above for initial load
    //if not, the below will only trigger when you first select a filter in the drop down table
    this.filterSubscription = this.filterSvc.filter$.subscribe(filter => {
      // Use the emitted string to get all entries
      this.entries$ = this.diaryStore.getEntriesByType(filter);
    });
  }

}
