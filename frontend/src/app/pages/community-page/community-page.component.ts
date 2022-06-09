import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiCommunity } from 'src/app/models/community.model';
import { ApiPost } from 'src/app/models/post.model';
import { CommunityService } from 'src/app/services/community.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-community-page',
  templateUrl: './community-page.component.html',
  styleUrls: ['./community-page.component.css']
})
export class CommunityPageComponent implements OnInit {

  communityName = "";
  communityData !: ApiCommunity;

  posts: ApiPost[] = [];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
    private communityService: CommunityService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.communityName = params["name"];
      this.communityName = this.communityName[0].toUpperCase() + this.communityName.substring(1).toLowerCase();
      if (this.communityName == undefined) {
        this.router.navigateByUrl("/home")
      } else {
        this.communityService.getByName(this.communityName).subscribe(response => {
          if (response == undefined) {
            this.router.navigateByUrl("/home");
          } else {
            this.communityData = response;
          }
        })
        this.postService.getAllForCommunity(this.communityName).subscribe(response => {
          if (response != null) {
            this.posts = response;
          }
        })
      }
    })
  }
}
