import { DialogRef } from '@angular/cdk/dialog';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-profile-dialog',
  templateUrl: './edit-profile-dialog.component.html',
  styleUrls: ['./edit-profile-dialog.component.css']
})
export class EditProfileDialogComponent implements OnInit {

  editProfileGroup = new FormGroup( {
    displayName: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required]),
    description: new FormControl("")
  })

  constructor(private userService: UserService,
    private snackBar: MatSnackBar,
    private dialogRef: DialogRef<EditProfileDialogComponent>) { }

  ngOnInit(): void {
    this.editProfileGroup.setValue({
      displayName: this.userService.loggedInUser ? this.userService.loggedInUser.displayName : "",
      email: this.userService.loggedInUser ? this.userService.loggedInUser.email : "",
      description: this.userService.loggedInUser ? this.userService.loggedInUser.description : ""
    })
  }

  apply() {
    if(!this.editProfileGroup.valid) {
      return;
    } 
    let userId = this.userService.loggedInUser ? this.userService.loggedInUser.id : -1;
    let data = this.editProfileGroup.value;
    this.userService.editProfile(userId, data.displayName, data.email, data.description).subscribe( response => {
      if (response != undefined) {
        this.snackBar.open("Changes saved", "Ok");
        this.dialogRef.close();
        this.userService.loggedInUser = response;
      } else {
        this.snackBar.open("Failed to save changes", "Ok");
      }
    })
  }

}
