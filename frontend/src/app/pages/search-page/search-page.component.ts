import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiCommunity } from 'src/app/models/community.model';
import { CommunityService } from 'src/app/services/community.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

    communities: ApiCommunity[] = [];

  constructor(public communityService: CommunityService,
    private route: ActivatedRoute) {
    
   }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
        this.communityService.getCommunities(params["query"]).subscribe(data => {
            this.communities = data;
          });
    })
  }

}
