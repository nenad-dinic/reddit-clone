import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiPost } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-community-page',
  templateUrl: './community-page.component.html',
  styleUrls: ['./community-page.component.css']
})
export class CommunityPageComponent implements OnInit {

  communityName = "";

  posts: ApiPost[]=[];

  constructor(private route: ActivatedRoute, 
    private router: Router,
    private postService: PostService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.communityName = params["name"];
    })
    if (this.communityName == undefined) {
      this.router.navigateByUrl("/home")
    } else {
      this.postService.getAllForCommunity(this.communityName).subscribe(response => {
        if(response != null) {
          this.posts = response;
        }
      })
    }
  }

}
