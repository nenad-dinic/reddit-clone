import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
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

  constructor(private userService:UserService) { }

  ngOnInit(): void {
  }

  login() {
    if(!this.loginGroup.valid){
      return;
    } else {
      let data = this.loginGroup.value
      this.userService.login(data.username, data.password).subscribe(response=>{
        console.log(response)
      })
    }
  }
}
