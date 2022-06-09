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

}