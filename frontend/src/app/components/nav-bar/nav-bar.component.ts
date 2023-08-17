import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { ChangePasswordDialogComponent } from '../change-password-dialog/change-password-dialog.component';
import { CommunityDialogComponent } from '../community-dialog/community-dialog.component';
import { EditProfileDialogComponent } from '../edit-profile-dialog/edit-profile-dialog.component';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';
import { CommunityService } from 'src/app/services/community.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public dialog: MatDialog,
    private router: Router,
    public userService: UserService,
    private communityService: CommunityService) {}
  
  ngOnInit(): void {
  }

  openLoginDialog() {
    let ref = this.dialog.open(LoginDialogComponent, {
      width: "350px"
    });
    ref.componentInstance.changeToRegister.subscribe(()=> {
      ref.close();
      this.openRegisterDialog();
    });
  }

  openRegisterDialog() {
    let ref = this.dialog.open(RegisterDialogComponent, {
      width: "350px"
    });
    ref.componentInstance.changeToLogin.subscribe(()=> {
      ref.close();
      this.openLoginDialog();
    });
  }

  search(input: HTMLInputElement) {
    if (input.value === "") {
      return;
    }
    /* this.router.navigateByUrl("/community?name=" + input.value).then(()=>{
      window.location.reload();
    }); */
    this.router.navigateByUrl("/search?query=" + input.value);
    /* this.communityService.getCommunities(input.value).subscribe(data => {
        console.log(data);
    }); */
  }

  logOutUser() {
    this.userService.logOut();
  }

  createCommunityDialog() {
    this.dialog.open(CommunityDialogComponent, {
      width: "350px"
    });
  }

  viewProfilePage() {
    this.router.navigateByUrl("/profile")
  }
}
