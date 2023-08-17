export interface ApiCommunity {
    id: string;
    name: string;
    description: string;
    creationDate: Date;
    suspendedReason: string;
    suspended: boolean;
    moderators: number[];
    filePath: string;
    postCount: number;
    averageKarma: number;
}