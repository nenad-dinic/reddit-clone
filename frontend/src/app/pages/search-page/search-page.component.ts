import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiCommunity } from 'src/app/models/community.model';
import { ApiPost } from 'src/app/models/post.model';
import { CommunityService } from 'src/app/services/community.service';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-search-page',
    templateUrl: './search-page.component.html',
    styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

    communities: ApiCommunity[] = [];
    posts:ApiPost[] = [];

    constructor(public communityService: CommunityService,
        public postService: PostService,
        private route: ActivatedRoute) {

    }

    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
            if (params["type"] == "community") {
                this.communityService.getCommunities(params["query"]).subscribe(data => {
                    this.posts = [];
                    this.communities = data;
                });
            } else if (params["type"] == "post") {
                this.postService.getPosts(params["query"]).subscribe(data => {
                    this.communities = [];
                    this.posts = data;
                });
            }
        })
    }

}
