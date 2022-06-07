import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { ApiPost } from "../models/post.model";

@Injectable() 
export class PostService {
    constructor(private http: HttpClient) {

    }
    getAllPosts() {
        return this.http.get<ApiPost[]>(environment.APIUrl + "posts");
    }

    getAllForCommunity(name: string) {
        return this.http.get<ApiPost[]>(environment.APIUrl + "community/posts?name=" + name);
    }
}