import { Component, Input, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { DocumentService } from '../../services/document.service';

@Component({selector:'app-version-history', standalone:true, imports:[NgFor], template:`<h4>Version History</h4><ul><li *ngFor="let v of versions">{{v.timestamp}} - {{v.editedBy}}</li></ul>`})
export class VersionHistoryComponent implements OnInit {
  @Input() documentId=''; versions:any[]=[];
  constructor(private api:DocumentService){}
  ngOnInit(){ this.api.versions(this.documentId).subscribe(v=>this.versions=v); }
}
