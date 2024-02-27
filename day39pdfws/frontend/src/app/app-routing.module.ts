import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { View0Component } from './components/view0.component';
import { View1Component } from './components/view1.component';
import { View2Component } from './components/view2.component';
import { View3Component } from './components/view3.component';

const routes: Routes = [
  {path: '', component: View0Component},
  {path: 'list', component:View1Component},
  {path: 'details/:id', component:View2Component},
  {path: 'comment/:id', component:View3Component},
  { path: '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
