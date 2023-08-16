import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiPost } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';
import { EditPostDialogComponent } from '../edit-post-dialog/edit-post-dialog.component';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input()
  data!: ApiPost;

  @Output()
  onPostChanged = new EventEmitter();

  constructor(public userService: UserService,
    private postService: PostService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  deletePost() {
    this.postService.deletePost(this.data.id).subscribe(response => {
      if (response != undefined) {
        this.snackBar.open("Successfully deleted post", "Ok")
        this.onPostChanged.emit();
      } else {
        this.snackBar.open("Failed to delete post", "Ok")
      }
    })
  }

  editPost() {
    let ref = this.dialog.open(EditPostDialogComponent, {
      width: "60%",
      data: this.data
    });
    ref.componentInstance.onUpdate.subscribe(()=> {
      this.onPostChanged.emit();
    })
  }

  addReaction(type: "UPVOTE" | "DOWNVOTE"){
    this.postService.addReaction(type, this.userService.loggedInUser != undefined ? this.userService.loggedInUser.id : -1, this.data.id).subscribe(response => {
      if(response == undefined){
        this.snackBar.open("Failed to add reaction", "Ok");
      } else {
        this.onPostChanged.emit();
      }
    })
  }

  downloadPdf(path: string){
    window.open(environment.APIUrl + path.substring(1), "_blank");
  }

}
