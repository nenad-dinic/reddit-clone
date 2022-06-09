export interface ApiCommunity {
    id: number;
    name: string;
    description: string;
    creationDate: Date;
    suspendedReason: string;
    suspended: boolean;
}