import { DialogRef } from '@angular/cdk/dialog';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ApiCommunity } from 'src/app/models/community.model';
import { CommunityService } from 'src/app/services/community.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-community-dialog',
  templateUrl: './community-dialog.component.html',
  styleUrls: ['./community-dialog.component.css']
})
export class CommunityDialogComponent implements OnInit {

  communityGroup = new FormGroup( {
    name: new FormControl("", [Validators.required, Validators.maxLength(100)]),
    description: new FormControl("", [Validators.required])
  })

  @Input()
  data ?: ApiCommunity;

  constructor(private communityService: CommunityService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private dialogRef: DialogRef<CommunityDialogComponent>,
    private router: Router) { }

  ngOnInit(): void {
    if(this.data != undefined) {
      this.communityGroup.setValue({
        name: this.data.name,
        description: this.data.description
      })
    }
  }

  submit() {
    if(!this.communityGroup.valid) {
      return;
    }
    if(this.data != undefined) {
      this.communityService.updateCommunity(this.data.id, this.communityGroup.value.name, this.communityGroup.value.description).subscribe(response => {
        if(response == undefined) {
          this.snackBar.open("Failed to update community", "Ok")
        } else {
          this.snackBar.open("Community updated", "Ok")
          this.dialogRef.close();
        }
      })
    } else {
      let userId = this.userService.loggedInUser != undefined ? this.userService.loggedInUser.id : -1;
      this.communityService.createCommunity(userId, this.communityGroup.value.name, this.communityGroup.value.description).subscribe(response => {
        if(response == undefined) {
          this.snackBar.open("Failed to create community", "Ok")
        } else {
          this.snackBar.open("Community created", "Ok")
          this.dialogRef.close();
          this.router.navigateByUrl("/community?name=" + this.communityGroup.value.name);
        }
      })
    }
  }

}
