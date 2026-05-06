import { Component } from '@angular/core';
import { DocumentEditorComponent } from './components/document-editor/document-editor.component';
import { VersionHistoryComponent } from './components/version-history/version-history.component';
import { SearchComponent } from './components/search/search.component';

@Component({selector:'app-root', standalone:true, imports:[DocumentEditorComponent, VersionHistoryComponent, SearchComponent], template:`
<app-search></app-search>
<app-document-editor [documentId]="docId" [userId]="userId"></app-document-editor>
<app-version-history [documentId]="docId"></app-version-history>`})
export class AppComponent { docId='replace-with-created-doc-id'; userId='user-b'; }
