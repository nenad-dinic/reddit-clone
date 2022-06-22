import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ApiJwt } from "../models/jwt.model";
import { environment } from "src/environments/environment";
import { ApiUser } from "../models/user.model";

@Injectable() 
export class UserService {

    loggedInUser?: ApiUser;

    constructor(private http: HttpClient) { 
            let token = localStorage.getItem("token")
            if (token != undefined) {
                this.getTokenId(token);
            }
    }

    login(username: string|null|undefined, password: string|null|undefined) {
        return this.http.post<ApiJwt>(environment.APIUrl + "user/login", {
            username: username, password: password
        });
    }

    register(username: string|null|undefined, password: string|null|undefined, email: string|null|undefined) {
        return this.http.post<ApiUser>(environment.APIUrl + "user/register", {
            username: username, 
            password: password, 
            email: email,
            avatar: null,
            description: null,
            displayName: username
        })
    }

    getTokenId(token: string) {
        this.http.post<ApiUser>(environment.APIUrl + "user/tokenId", {
            token: token
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        }
        ).subscribe(response => {
            console.log(response)
            if (response != undefined) {
                
                this.loggedInUser = response;
            } else {
                this.logOut();
            }
        })
    }

    logOut() {
        localStorage.removeItem("token");
        this.loggedInUser = undefined;
    }

    changePassword(username: string|null|undefined, password: string|null|undefined, newPassword: string|null|undefined) {
        return this.http.put<ApiUser>(environment.APIUrl + "user/changePassword", {
            username: username,
            password: password,
            newPassword: newPassword
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }

    editProfile(id: number, displayName: string|null|undefined, email: string|null|undefined, description: string|null|undefined) {
        return this.http.put<ApiUser>(environment.APIUrl + "user/edit?id=" + id, {
            displayName: displayName,
            email: email,
            description: description
        }
        ,
        {
            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
        })
    }
}