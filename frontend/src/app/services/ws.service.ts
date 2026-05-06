import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject } from 'rxjs';
import { CollabDocument, EditPayload } from '../models/document.model';

@Injectable({ providedIn: 'root' })
export class WsService {
  private client?: Client;
  readonly updates$ = new Subject<CollabDocument>();

  connect(docId: string): void {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws-document'),
      reconnectDelay: 5000,
    });

    this.client.onConnect = () => {
      this.client?.subscribe(`/topic/document/${docId}`, (message: IMessage) => {
        const payload = JSON.parse(message.body) as CollabDocument;
        this.updates$.next(payload);
      });
    };

    this.client.activate();
  }

  edit(docId: string, payload: EditPayload): void {
    this.client?.publish({
      destination: `/app/document/${docId}/edit`,
      body: JSON.stringify(payload),
    });
  }
}
