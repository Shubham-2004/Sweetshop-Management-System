import { Routes } from '@angular/router';
import { LandingPage } from '../landing-page/landing-page';
import { LoginPage } from '../login-page/login-page';
import { Dashboard } from '../dashboard/dashboard';

export const routes: Routes = [
  {
    path: '',
    component: LandingPage
  },
  {
    path: 'login',
    component: LoginPage
  },
  {
    path: 'dashboard',
    component: Dashboard
  }
];