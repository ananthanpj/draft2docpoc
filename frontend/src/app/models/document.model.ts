export interface CollabDocument { id:string; name:string; content:string; ownerId:string; lockedBy?:string; lockedAt?:string; }
export interface DocVersion { id:string; documentId:string; content:string; editedBy:string; timestamp:string; }
