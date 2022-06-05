import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ApiJwt } from "../models/jwt.model";
import { environment } from "src/environments/environment";

@Injectable() 
export class UserService {
    constructor(private http: HttpClient) {

    }

    login(username: string|null|undefined, password: string|null|undefined) {
        return this.http.post<ApiJwt>(environment.APIUrl + "user/login", {
            username: username, password: password
        });
    }
}