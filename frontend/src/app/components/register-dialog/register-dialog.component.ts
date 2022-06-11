import { DialogRef } from '@angular/cdk/dialog';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css']
})
export class RegisterDialogComponent implements OnInit {

  registerGroup= new FormGroup(
    {
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.email, Validators.required])
    }
  )

  @Output()
  changeToLogin = new EventEmitter();

  constructor(private userService:UserService, 
    public dialogRef: DialogRef<RegisterDialogComponent>,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  register() {
    if(!this.registerGroup.valid){
      return;
    } else {
      let data = this.registerGroup.value
      this.userService.register(data.username, data.password, data.email).subscribe(response=>{
        if (response==null) {
          this.snackBar.open("Failed to register", "Ok")
        } else {
          this.snackBar.open("Succesfull registration", "Ok")
          this.dialogRef.close();
        }
            
      })
    }
  }

  changeWindow() {
    this.changeToLogin.emit();
  }

}
