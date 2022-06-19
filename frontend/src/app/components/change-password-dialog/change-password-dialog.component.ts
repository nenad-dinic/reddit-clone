import { DialogRef } from '@angular/cdk/dialog';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.css']
})
export class ChangePasswordDialogComponent implements OnInit {

  changePasswordGroup = new FormGroup( {
    oldPassword: new FormControl("", [Validators.required]),
    newPassword: new FormControl("", [Validators.required, Validators.minLength(5)]),
    newPasswordRepeat: new FormControl("", Validators.required)
  })

  constructor(private userService: UserService,
    private snackBar: MatSnackBar,
    private dialogRef: DialogRef) { }

  ngOnInit(): void {
  }

  submit() {
    if(!this.changePasswordGroup.valid) {
      return
    }
    
    let data = this.changePasswordGroup.value;
    if (data.newPassword != data.newPasswordRepeat) {
      this.snackBar.open("New password must match", "Ok");
      return
    } 
    this.userService.changePassword(this.userService.loggedInUser?.username, data.oldPassword, data.newPassword).subscribe(response => {
      if(response != undefined) {
        this.dialogRef.close();
        this.snackBar.open("Password changed successfully", "Ok");
      } else {
        this.snackBar.open("Failed to change password", "OK")
      }
    })
  }

}
