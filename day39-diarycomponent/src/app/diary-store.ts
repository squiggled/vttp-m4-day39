import { ComponentStore, OnStoreInit } from "@ngrx/component-store";
import { Diary, DiarySlice } from "./models";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
// import { DiaryService } from "./diary-service.service";

//initialise the initial state
const INIT_STATE: DiarySlice = {
    entries: []
}

@Injectable()
export class DiaryStore extends ComponentStore<DiarySlice> {
    //implements OnStoreInit
    constructor(){super(INIT_STATE)} //get initial state

    //this svc is only needed if you are getting request from HTTP
    // private diarySvc = inject(DiaryService);
    //lifecycle method to initialise the store to preload existing data
    // ngrxOnStoreInit():void{
    //     this.
    // }


    //mutation - ADD
    readonly addDiary = this.updater<Diary>( //<diary> refers to the data that the func will use to update the state
        (slice: DiarySlice, diary:Diary) => {
            const newSlice: DiarySlice = {
                entries: [...slice.entries, diary,]
            };
        return newSlice;//return new slice within the inner function
    }) 


    //explaining the HO function
    //most outside function: this.updater<diary()
    //add diary becomes a function, but its not the 'same' function as this.updater<diary>
    //adddiary is the function RETURNED by this.updater<diary>. it is the func you can call to update the state
    //it takes in a function as its argument, the:
    //2nd level function((slice: DiarySlice, diary: Diary) => {...})
    //it returns yet another function, addDiary
    //2nd level function describes how to update the state when updater is called and it returns 

     //selector 
     //complicated method to only return certain entries
     readonly getAllEntriesComplex = this.select(
        (slice: DiarySlice) => slice.entries.map(
            diary => ({
                date: diary.date,
                entry: diary.entry,
                type: diary.entry
            } as Diary )
        )
    )
    readonly getEntries = this.select<Diary[]>( //the thing in the <> is what is returned by the function
        (slice: DiarySlice) => slice.entries
      )
    
    readonly getNumberOfEntries = this.select<number>(
    (slice: DiarySlice) => slice.entries.length
    )

    readonly getEntriesByType = (type: string) => {
        return this.select<Diary[]>(
          (slice: DiarySlice) => {
            if (!type || type === 'all') {
              return slice.entries;
            } else {
              return slice.entries.filter(diary => diary.type === type);
            }
          }
        );
      };
    // readonly filteredEntries$ = this.select<Diary []>(
    //     (slice: DiarySlice, filter: string) => {
    //       if (filter === 'all') {
    //         return slice.entries;
    //       } else {
    //         return slice.entries.filter(entry => entry.type === filter);
    //       }
    //     }
    //   );

    //set filter
  
    

}