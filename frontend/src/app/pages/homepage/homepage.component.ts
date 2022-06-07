import { Component, OnInit } from '@angular/core';
import { ApiPost } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  posts: ApiPost[]=[];

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(response => {
      if(response != null) {
        this.posts = response;
      }
    })
  }

}
