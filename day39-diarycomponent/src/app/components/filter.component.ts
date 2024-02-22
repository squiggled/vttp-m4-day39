import { Component, inject } from '@angular/core';
// import { DiaryService } from '../diary-service.service';
import { DiaryStore } from '../diary-store';
import { Subject } from 'rxjs';
import { FilterService } from '../filter-service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {

  private filterSvc = inject(FilterService);
  private diaryStore = inject(DiaryStore)

  filter(elem: any) {
    console.info('elem: ', elem.target.value)
    const category: string = elem.target.value;
    // this.diarySvc.getEntryByType(category);
    // this.diaryStore.setFilter(category);
    this.filterSvc.setFilter(category);
  }

}
