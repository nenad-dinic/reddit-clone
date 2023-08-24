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

    getCommunities(search: string, from: number, to: number) {
        return this.http.get<ApiCommunity[]>(environment.APIUrl + "communities?search=" + search + "&from=" + from + "&to=" + to);
    }

    createCommunity(userId:number, name: string|null|undefined, description: string|null|undefined, file: File|null|undefined) {
        const data = new FormData();
        if (name != null) data.append("name", name);
        if (description != null) data.append("description", description);
        if (userId != null) data.append("userId", userId.toString());
        if (file != null) data.append("descPdf", file);
        return this.http.post<ApiCommunity>(environment.APIUrl + "community", data,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        });
    }

    updateCommunity(communityId: string, name: string|null|undefined, description: string|null|undefined) {
        return this.http.put<ApiCommunity>(environment.APIUrl + "community?id=" + communityId, {
            name: name,
            description: description,
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }

    deleteCommunity(communityId: string) {
        return this.http.delete<ApiCommunity>(environment.APIUrl + "community?id=" + communityId,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }

}