import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CollabDocument, DocVersion, SearchResult } from '../models/document.model';

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private readonly base = 'http://localhost:8080/api';

  constructor(private readonly http: HttpClient) {}

  getDocument(id: string): Observable<CollabDocument> {
    return this.http.get<CollabDocument>(`${this.base}/documents/${id}`);
  }

  lock(id: string, userId: string): Observable<CollabDocument> {
    return this.http.post<CollabDocument>(`${this.base}/documents/${id}/lock`, { userId });
  }

  unlock(id: string, userId: string): Observable<CollabDocument> {
    return this.http.post<CollabDocument>(`${this.base}/documents/${id}/unlock`, { userId });
  }

  save(id: string, userId: string, content: string): Observable<CollabDocument> {
    return this.http.put<CollabDocument>(`${this.base}/documents/${id}`, { userId, content });
  }

  versions(id: string): Observable<DocVersion[]> {
    return this.http.get<DocVersion[]>(`${this.base}/documents/${id}/versions`);
  }

  search(q: string): Observable<SearchResult[]> {
    return this.http.get<SearchResult[]>(`${this.base}/search?q=${encodeURIComponent(q)}`);
  }
}
