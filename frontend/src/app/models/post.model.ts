import { ApiCommunity } from "./community.model";
import { ApiUser } from "./user.model";

export interface ApiPost {
        id: number,
        title: string,
        text: string,
        creationDate: Date,
        imagePath: string,
        postedBy: number,
        communityId: number,
        karma: number,
        user: ApiUser,
        community: ApiCommunity,
        filePath: string

}