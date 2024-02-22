// import { Injectable } from '@angular/core';
// import { Diary } from './models';
// import { Observable, Subject, from } from 'rxjs';
// import Dexie from 'dexie';

// @Injectable({
//   providedIn: 'root'
// })
// export class DiaryService extends Dexie{

//   private entries!:Dexie.Table<Diary, string>;
  
//   private entriesSubject = new Subject<Diary[]>(); 
//   entries$ = this.entriesSubject.asObservable(); //we link the subject to the obsverable

//   constructor(){
//     //call super(), parent method, and pass in the name to create the db if it does not exist
//     super('userdb');
//     const COL_ENTRIES = 'entries';
//     //define schema
//     this.version(3).stores({
//         //collection name followed by primary key
//         [COL_ENTRIES]: 'entry, date, type'
        
//     })
//     this.entries=this.table(COL_ENTRIES);
//     //for initial load.
//     //
//     this.getDiaryEntries().then(
//       (result) => this.entriesSubject.next(result)
//     )
// }
//   async addEntry(entry: Diary){
//     await this.entries.add(entry); // Add entry to Dexie DB
//     const allEntries = await this.entries.toArray(); // Fetch all entries
//     this.entriesSubject.next(allEntries); // Emit updated list of entries
//   }


//   getDiaryEntries(): Promise<Diary[]> {
//     return this.entries
//         .orderBy('date').reverse().toArray()
//   }

//   async getEntryByType(type:string) {
//     const collection = await this.entries
//       .where('type').equals(type) 
//       .toArray(); 

//     console.log(collection); // Now logging the actual entries
    
//     this.entriesSubject.next(collection); // Emit the filtered entries array
// }
    
//   }

