import { DialogRef } from '@angular/cdk/dialog';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {

  loginGroup= new FormGroup(
    {
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
    }
  )

  @Output()
  changeToRegister = new EventEmitter();

  constructor(private userService:UserService, 
    public dialogRef: DialogRef<LoginDialogComponent>,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  login() {
    if(!this.loginGroup.valid){
      return;
    } else {
      let data = this.loginGroup.value
      this.userService.login(data.username, data.password).subscribe(response=>{
        if (response==null) {
          this.snackBar.open("Login failed", "Ok")
        } else {
          this.userService.getTokenId(response.token);
          
          this.dialogRef.close();
        }      })
    }
  }

  changeWindow() {
    this.changeToRegister.emit();
  }
}
