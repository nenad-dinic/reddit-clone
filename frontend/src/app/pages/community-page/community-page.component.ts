import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CommunityDialogComponent } from 'src/app/components/community-dialog/community-dialog.component';
import { ApiCommunity } from 'src/app/models/community.model';
import { ApiPost } from 'src/app/models/post.model';
import { CommunityService } from 'src/app/services/community.service';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';

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
    private communityService: CommunityService,
    public userService: UserService,
    private matDialog: MatDialog,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.communityName = params["name"];
      if (this.communityName != undefined) {
        this.communityName = this.communityName[0].toUpperCase() + this.communityName.substring(1).toLowerCase();
        this.loadCommunity();
      } else {
        this.router.navigateByUrl("/home");
      }
    })
  }

  loadPosts() {
    this.postService.getAllForCommunity(this.communityData.id).subscribe(response => {
      if (response != null) {
        this.posts = response;
      } else {
        this.posts = [];
      }
    })
  }

  loadCommunity() {
    this.communityService.getByName(this.communityName).subscribe(response => {
      if (response == undefined) {
        this.router.navigateByUrl("/home");
      } else {
        this.communityData = response;
        this.loadPosts();
      }
    })
  }

  editCommunity() {
    this.matDialog.open(CommunityDialogComponent, {
      data: this.communityData,
      width: "350px"
    });
  }

  deleteCommunity() {
    this.communityService.deleteCommunity(this.communityData.id).subscribe(response => {
      if (response != undefined) {
        this.snackBar.open("Successfully deleted community", "Ok")
        this.router.navigateByUrl("/home")
      } else {
        this.snackBar.open("Failed to delete community", "Ok")
      }
    })
  }
}