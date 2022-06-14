export interface ApiReaction {
    id : number;
    type : "UPVOTE" | "DOWNVOTE";
    timestamp : Date;
    reactionBy: number;
    reactionTo: "POST" | "COMMENT";
    reactionToPostId : number;
    reactionToCommentId: number;
}