import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {WebcamModule} from 'ngx-webcam';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WebcamComponent } from './components/webcam.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    WebcamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    WebcamModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
