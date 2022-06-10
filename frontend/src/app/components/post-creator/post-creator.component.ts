import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

  constructor(private postService: PostService) { }

  ngOnInit(): void {
  }

  createPost() {
    if(!this.postGroup.valid) {
      return
    } else {
      let data = this.postGroup.value;
      this.postService.createPost(data.title, data.description, 1, this.communityId).subscribe(response => {
        if (response == undefined) {
          alert("failed to create post")
        } else {
          alert("Post created")
        }
      })
    }
  }
}
