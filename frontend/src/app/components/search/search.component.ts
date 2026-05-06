import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { DocumentService } from '../../services/document.service';
import { SearchResult } from '../../models/document.model';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule, NgFor],
  template: `
<h4>Search Documents</h4>
<input [formControl]="q" placeholder="keyword"/><button (click)="search()">Search</button>
<ul><li *ngFor="let r of results">{{r.name}} (owner: {{r.ownerId}})</li></ul>`,
})
export class SearchComponent {
  q = new FormControl('', { nonNullable: true });
  results: SearchResult[] = [];

  constructor(private readonly api: DocumentService) {}

  search(): void {
    this.api.search(this.q.value).subscribe((r) => (this.results = r));
  }
}
