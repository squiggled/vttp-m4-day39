import { Component } from '@angular/core';
import { WebcamImage } from 'ngx-webcam';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-webcam',
  templateUrl: './webcam.component.html',
  styleUrl: './webcam.component.css'
})
export class WebcamComponent {

  private triggerSub =new Subject<void>();
  triggerObs$ = this.triggerSub.asObservable();

  webcamImage!: WebcamImage;

  webcamImageDataUrl!: string;

  snap(){
    this.triggerSub.next();
  }

  imageCapture(webcamImage: WebcamImage){
    this.webcamImageDataUrl = webcamImage.imageAsDataUrl;
  }
}
