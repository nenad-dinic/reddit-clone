import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { ApiCommunity } from "../models/community.model";

@Injectable()
export class CommunityService {
    constructor(private http: HttpClient) {

    }

    getByName(name: string) {
        return this.http.get<ApiCommunity>(environment.APIUrl + "community?name=" + name);
    }

    createCommunity(userId:number, name: string|null|undefined, description: string|null|undefined) {
        return this.http.post<ApiCommunity>(environment.APIUrl + "community", {
            name: name,
            description: description,
            userId: userId
        });
    }

    updateCommunity(communityId: number, name: string|null|undefined, description: string|null|undefined) {
        return this.http.put<ApiCommunity>(environment.APIUrl + "community?id=" + communityId, {
            name: name,
            description: description,
        })
    }

}