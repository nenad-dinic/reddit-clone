import { Component, Input, OnInit } from '@angular/core';
import { ApiCommunity } from 'src/app/models/community.model';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.css']
})
export class CommunityComponent implements OnInit {

    @Input()
  data!: ApiCommunity;
  

  constructor() { }

  ngOnInit(): void {
  }

  downloadPdf(path: string){
    window.open(environment.APIUrl + path.substring(1), "_blank");
  }

}
