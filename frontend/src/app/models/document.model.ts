export interface CollabDocument {
  id: string;
  name: string;
  content: string;
  ownerId: string;
  lockedBy?: string | null;
  lockedAt?: string | null;
}

export interface DocVersion {
  id: string;
  documentId: string;
  content: string;
  editedBy: string;
  timestamp: string;
}

export interface SearchResult {
  id: string;
  name: string;
  content: string;
  ownerId: string;
}

export interface EditPayload {
  userId: string;
  content: string;
}
