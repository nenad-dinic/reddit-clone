import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordDialogComponent } from 'src/app/components/change-password-dialog/change-password-dialog.component';
import { EditProfileDialogComponent } from 'src/app/components/edit-profile-dialog/edit-profile-dialog.component';
import { ApiUser } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

  data?: ApiUser;

  constructor(public userService: UserService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    
  }

  changePasswordDialog() {
    this.dialog.open(ChangePasswordDialogComponent, {
      width: "300px"
    })
  }

  editProfileDialog() {
    this.dialog.open(EditProfileDialogComponent)
  }

}
