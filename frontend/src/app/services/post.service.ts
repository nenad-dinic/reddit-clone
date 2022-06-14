import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { ApiPost } from "../models/post.model";
import { ApiReaction } from "../models/reaction.model";

@Injectable() 
export class PostService {
    constructor(private http: HttpClient) {

    }

    getAllPosts() {
        return this.http.get<ApiPost[]>(environment.APIUrl + "posts");
    }

    getAllForCommunity(name: string) {
        return this.http.get<ApiPost[]>(environment.APIUrl + "posts/community?name=" + name);
    }

    createPost(title: string|null|undefined, description: string|null|undefined, userId: number, communityId: number) {
        return this.http.post<ApiPost>(environment.APIUrl + "post", {
            title: title,
            text: description,
            userId: userId,
            communityId: communityId
        })
    }

    deletePost(id: number) {
        return this.http.delete<ApiPost>(environment.APIUrl + "post?id=" + id);
    }

    updatePost(id: number, title: string|null|undefined, description: string|null|undefined) {
        return this.http.put<ApiPost>(environment.APIUrl + "post?id=" + id, {
            title: title,
            text: description
        })
    }

    addReaction(reactionType : string, userId: number, reactionToId : number) {
        return this.http.post<ApiReaction>(environment.APIUrl + "reaction", {
            reactionType: reactionType,
            reactionBy: userId,
            reactionTo: "POST",
            reactionToId: reactionToId
        });
    }

}