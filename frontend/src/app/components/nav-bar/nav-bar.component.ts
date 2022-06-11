import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { CommunityDialogComponent } from '../community-dialog/community-dialog.component';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public dialog: MatDialog,
    private router: Router,
    public userService: UserService) {}
  
  ngOnInit(): void {
  }

  openLoginDialog() {
    let ref = this.dialog.open(LoginDialogComponent);
    ref.componentInstance.changeToRegister.subscribe(()=> {
      ref.close();
      this.openRegisterDialog();
    });
  }

  openRegisterDialog() {
    let ref = this.dialog.open(RegisterDialogComponent);
    ref.componentInstance.changeToLogin.subscribe(()=> {
      ref.close();
      this.openLoginDialog();
    })
  }

  search(input: HTMLInputElement) {
    if (input.value === "") {
      return;
    }
    this.router.navigateByUrl("/community?name=" + input.value).then(()=>{
      window.location.reload();
    });
  }

  logOutUser() {
    this.userService.logOut();
  }

  createCommunityDialog() {
    this.dialog.open(CommunityDialogComponent);
  }
}
