import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject } from 'rxjs';

@Injectable({providedIn:'root'})
export class WsService {
  private client?: Client;
  updates$ = new Subject<any>();
  connect(docId:string){
    this.client = new Client({
      webSocketFactory:()=>new SockJS('http://localhost:8080/ws-document'), reconnectDelay:5000
    });
    this.client.onConnect = ()=> this.client?.subscribe(`/topic/document/${docId}`, m => this.updates$.next(JSON.parse(m.body)));
    this.client.activate();
  }
  edit(docId:string,payload:any){ this.client?.publish({destination:`/app/document/${docId}/edit`,body:JSON.stringify(payload)}); }
}
