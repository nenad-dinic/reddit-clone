import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public dialog: MatDialog,
    private router: Router) {}
  
  ngOnInit(): void {
  }

  openLoginDialog() {
    this.dialog.open(LoginDialogComponent);
  }

  openRegisterDialog() {
    this.dialog.open(RegisterDialogComponent);
  }

  search(input: HTMLInputElement) {
    if (input.value === "") {
      return;
    }
    this.router.navigateByUrl("/community?name=" + input.value).then(()=>{
      window.location.reload();
    });
  }
}
