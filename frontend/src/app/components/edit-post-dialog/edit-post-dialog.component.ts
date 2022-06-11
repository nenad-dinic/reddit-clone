import { DialogRef } from '@angular/cdk/dialog';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiPost } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-edit-post-dialog',
  templateUrl: './edit-post-dialog.component.html',
  styleUrls: ['./edit-post-dialog.component.css']
})
export class EditPostDialogComponent implements OnInit {

  editPostGroup= new FormGroup( {
    title: new FormControl("", [Validators.required, Validators.maxLength(100)]),
    description: new FormControl("", [Validators.required])
  }
  )

  data !: ApiPost;

  @Output()
  onUpdate = new EventEmitter();

  constructor(@Inject(MAT_DIALOG_DATA) data: ApiPost,
  private postService: PostService,
  private snackBar: MatSnackBar,
  private dialogRef: DialogRef<EditPostDialogComponent>) {
    this.data = data;
   }

  ngOnInit(): void {
    this.editPostGroup.setValue({
      title: this.data.title,
      description: this.data.text
    })
  }

  apply() {
    if (!this.editPostGroup.valid) {
      return;
    } else {
      this.postService.updatePost(this.data.id, this.editPostGroup.value.title, this.editPostGroup.value.description).subscribe(response => {
        if (response == undefined) {
          this.snackBar.open("Failed to update post", "Ok");
        } else {
          this.dialogRef.close();
          this.snackBar.open("Saved changes", "Ok");
          this.onUpdate.emit();
        }
      })
    }
  }
}
