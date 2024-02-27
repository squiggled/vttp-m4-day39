import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, Subject, firstValueFrom, map } from 'rxjs';
import { SearchResult, Comment } from './models';

@Injectable({
  providedIn: 'root'
})
export class GiphyService {
  
  private httpClient = inject(HttpClient);

  //Search array
  searchResult:SearchResult[] = [];

  //comments array
  comments:Comment[]=[];

  //search subjects
  private searchSubject = new Subject<SearchResult[]>;
  searchObs$ = this.searchSubject.asObservable();

  //gif subjects
  private gifDeetsSubject = new Subject<SearchResult>;
  gifDeets$ = this.gifDeetsSubject.asObservable();

  //comment subjects
  private commentSubject = new BehaviorSubject<Comment[]>([]);
  commentsObs$ = this.commentSubject.asObservable();

  currentOffset: number = 0;
  currentSearchParam!:string;

  constructor() { }

  search(value: string) {
    this.currentSearchParam=value;
    const params = new HttpParams().set('phrase', this.currentSearchParam).set('offset', this.currentOffset);
    console.log("current search in search function ", this.currentSearchParam);
    
    firstValueFrom(this.httpClient.get<SearchResult[]>("https://first-stamp-production.up.railway.app/api/search", {params}))
    .then(result => {
      this.searchResult = result;
      // console.log(result);
      this.searchSubject.next(this.searchResult); 
    })
    .catch(error => console.log(error))
  }

  addOffSet(){
    console.log("original offset in svc", this.currentOffset);
    this.currentOffset = this.currentOffset+20;
    this.search(this.currentSearchParam);
  }
  decOffSet(){
    this.currentOffset = this.currentOffset-20;
    this.search(this.currentSearchParam);
  }

  // getComments(id:string){
  //   // firstValueFrom(this.httpClient.get<Comment[]>(`https://first-stamp-production.up.railway.app/api/searchmongo/${id}`))
  //   //   .then(result => {
  //   //     this.commentSubject.next(result)
  //   //     console.log(".then in getcomments" + result);
        
  //   //   })
  //   //   .catch(error => console.log(error))

  // }
  fetchComments(id: string): void {
    this.httpClient.get<Comment[]>(`https://first-stamp-production.up.railway.app/api/searchmongo/${id}`).pipe(
      map(comments => comments.map(comment => ({
        ...comment,
        timestamp: new Date(comment.timestamp)
      })))
    ).subscribe(comments => {
      this.commentSubject.next(comments);
    });
  }

  getComments(): Observable<Comment[]> {
    return this.commentSubject.asObservable();
  }

  getGif(id:string){
    firstValueFrom(this.httpClient.get<SearchResult>(`https://first-stamp-production.up.railway.app/api/searchredis/${id}`))
      .then(result => {
        this.gifDeetsSubject.next(result);
      })
      .catch(error => console.log(error))
    firstValueFrom(this.httpClient.get<Comment[]>(`https://first-stamp-production.up.railway.app/api/searchmongo/${id}`))
      .then(result => {
        this.commentSubject.next(result)
        console.log(".then in getgif" + result);
      })
      .catch(error => console.log(error))
  }

  addMongoComment(gifId:string, commentText:string){
    const commentObj: Comment = {
      gifId: gifId,
      comment: commentText,
      timestamp: new Date()
    };
    firstValueFrom(this.httpClient.post<Comment[]>(`https://first-stamp-production.up.railway.app/api/addmongo`, commentObj))
    .then(result=> {
      this.commentSubject.next(result)
      console.log(".then in addmongocomment" + result);
    })
    .catch(error => console.log(error))
  }
}
