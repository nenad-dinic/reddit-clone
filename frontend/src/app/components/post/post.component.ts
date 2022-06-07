import { Component, Input, OnInit } from '@angular/core';
import { ApiPost } from 'src/app/models/post.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input()
  data!: ApiPost;

  constructor() { }

  ngOnInit(): void {
  }

}
