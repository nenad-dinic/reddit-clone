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

    getAllForCommunity(communityId: string) {
        return this.http.get<ApiPost[]>(environment.APIUrl + "posts/community?communityId=" + communityId);
    }

    createPost(title: string|null|undefined, description: string|null|undefined, userId: number, communityId: string, file: File|null|undefined) {
        const data = new FormData();
        if (title != null) data.append("title", title);
        if (description != null) data.append("text", description);
        if (file != null) data.append("filePdf", file);
        if (userId != null) data.append("userId", userId.toString());
        if (communityId != null) data.append("communityId", communityId);
        return this.http.post<ApiPost>(environment.APIUrl + "post", data,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }

    deletePost(id: number) {
        return this.http.delete<ApiPost>(environment.APIUrl + "post?id=" + id,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        });
    }

    updatePost(id: number, title: string|null|undefined, description: string|null|undefined,) {
        
        return this.http.put<ApiPost>(environment.APIUrl + "post?id=" + id, {
            title: title,
            text: description
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }

    addReaction(reactionType : string, userId: number, reactionToId : number) {
        return this.http.post<ApiReaction>(environment.APIUrl + "reaction", {
            reactionType: reactionType,
            reactionBy: userId,
            reactionTo: "POST",
            reactionToId: reactionToId
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        });
    }

}