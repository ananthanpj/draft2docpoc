import { Component, Input, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { DocumentService } from '../../services/document.service';
import { WsService } from '../../services/ws.service';

@Component({selector:'app-document-editor', standalone:true, imports:[ReactiveFormsModule], templateUrl:'./document-editor.component.html'})
export class DocumentEditorComponent implements OnInit {
  @Input() documentId=''; @Input() userId='user-b';
  content = new FormControl(''); doc:any; locked=false;
  constructor(private api:DocumentService, private ws:WsService){}
  ngOnInit(){ this.load(); this.ws.connect(this.documentId); this.ws.updates$.subscribe(d=>{this.doc=d; this.content.setValue(d.content);}); }
  load(){ this.api.getDocument(this.documentId).subscribe(d=>{this.doc=d; this.content.setValue(d.content); this.locked=!!d.lockedBy && d.lockedBy!==this.userId;}); }
  lock(){ this.api.lock(this.documentId,this.userId).subscribe(()=>this.load()); }
  save(){ this.api.save(this.documentId,this.userId,this.content.value||'').subscribe(()=>this.load()); }
  unlock(){ this.api.unlock(this.documentId,this.userId).subscribe(()=>this.load()); }
}
