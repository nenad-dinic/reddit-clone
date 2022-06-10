import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post-creator',
  templateUrl: './post-creator.component.html',
  styleUrls: ['./post-creator.component.css']
})
export class PostCreatorComponent implements OnInit {

  postGroup = new FormGroup(
    {
      title: new FormControl("", [Validators.required, Validators.maxLength(100)]),
      description: new FormControl("", [Validators.required])
    }
  )

  @Input()
  communityId!: number;

  @Output()
  onPostCreation = new EventEmitter();

  constructor(private postService: PostService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  createPost() {
    if(!this.postGroup.valid) {
      return
    } else {
      let data = this.postGroup.value;
      this.postService.createPost(data.title, data.description, 1, this.communityId).subscribe(response => {
        if (response == undefined) {
          this.snackBar.open("Failed to create post", "OK")
        } else {
          this.snackBar.open("Post created", "Ok")
          this.onPostCreation.emit();
          this.postGroup.reset();
        }
      })
    }
  }
}
