import { Component, Input, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { DocumentService } from '../../services/document.service';
import { WsService } from '../../services/ws.service';
import { CollabDocument } from '../../models/document.model';

@Component({
  selector: 'app-document-editor',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './document-editor.component.html',
})
export class DocumentEditorComponent implements OnInit {
  @Input() documentId = '';
  @Input() userId = 'user-b';

  content = new FormControl('', { nonNullable: true });
  doc?: CollabDocument;
  locked = false;

  constructor(private readonly api: DocumentService, private readonly ws: WsService) {}

  ngOnInit(): void {
    this.load();
    this.ws.connect(this.documentId);
    this.ws.updates$.subscribe((d) => {
      this.doc = d;
      this.content.setValue(d.content);
    });
  }

  load(): void {
    this.api.getDocument(this.documentId).subscribe((d) => {
      this.doc = d;
      this.content.setValue(d.content);
      this.locked = !!d.lockedBy && d.lockedBy !== this.userId;
    });
  }

  lock(): void { this.api.lock(this.documentId, this.userId).subscribe(() => this.load()); }
  save(): void { this.api.save(this.documentId, this.userId, this.content.value).subscribe(() => this.load()); }
  unlock(): void { this.api.unlock(this.documentId, this.userId).subscribe(() => this.load()); }
}
