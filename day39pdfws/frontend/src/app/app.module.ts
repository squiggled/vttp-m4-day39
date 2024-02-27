import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { View0Component } from './components/view0.component';
import { View1Component } from './components/view1.component';
import { View2Component } from './components/view2.component';
import { View3Component } from './components/view3.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { GiphyService } from './giphy.service';


@NgModule({
  declarations: [
    AppComponent,
    View0Component,
    View1Component,
    View2Component,
    View3Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
