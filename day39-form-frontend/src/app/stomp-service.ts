import { HttpClient } from "@angular/common/http";
import { ElementRef, Injectable, inject } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { Subject, firstValueFrom } from "rxjs";

@Injectable()
export class StompService {
    private client = inject(HttpClient);
    private urlSubject = new Subject<string>;
    url$ = this.urlSubject.asObservable();
    url!:string;

    upload(form: FormGroup, imageFile: ElementRef){
        console.log("form: ", form.value);
        console.log("imagefile: ", imageFile);

        //create form data here
        const formData = new FormData();
        //{user: 'fdsfs', description: 'fsfsddfs'} we access using . notation
        formData.set('user', form.value.user);
        formData.set('description', form.value.description);
        // console.log("user value: ", formData);
        //what u send is a json
        formData.set('file', imageFile.nativeElement.files[0]);
        firstValueFrom(this.client.post<any>('/stomp', formData))
        .then(response => {this.url=response.url; console.log("url in service ",this.url)})
        .then(response=>{this.urlSubject.next(this.url)})
        .catch(error => {console.log("error displaying image");
        });
        
        
    }
}