export interface ApiPost {
        id: number,
        title: string,
        text: string,
        creationDate: Date,
        imagePath: string,
        postedBy: number,
        communityId: number,
        counter: number
    
}