import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class DocumentService {
  private base='http://localhost:8080/api';
  constructor(private http:HttpClient){}
  getDocument(id:string){return this.http.get<any>(`${this.base}/documents/${id}`)}
  lock(id:string,userId:string){return this.http.post(`${this.base}/documents/${id}/lock`,{userId})}
  unlock(id:string,userId:string){return this.http.post(`${this.base}/documents/${id}/unlock`,{userId})}
  save(id:string,userId:string,content:string){return this.http.put(`${this.base}/documents/${id}`,{userId,content})}
  versions(id:string):Observable<any[]>{return this.http.get<any[]>(`${this.base}/documents/${id}/versions`)}
  search(q:string):Observable<any[]>{return this.http.get<any[]>(`${this.base}/search?q=${encodeURIComponent(q)}`)}
}
