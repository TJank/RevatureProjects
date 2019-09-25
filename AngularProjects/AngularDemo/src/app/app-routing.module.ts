import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DatabindingComponent } from './components/databinding/databinding.component';
import { DirectivesComponent } from './components/directives/directives.component';

/**
 * This array of routes contains a list of objects which
 * link a path to a component that will be injectd into
 * our page.
 */
const routes: Routes = [
  {
    path: "databinding",
    component: DatabindingComponent
  },
  {
    path: "directives",
    component: DirectivesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
