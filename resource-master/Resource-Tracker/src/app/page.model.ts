export interface Page<T> {
  content: T[];         // The actual data/content of the page
  pageable: {
    sort: {
      sorted: boolean;  // Whether the results are sorted
      unsorted: boolean;
      empty: boolean;
    };
    pageNumber: number; // Current page number
    pageSize: number;   // Number of elements per page
    offset: number;     // Offset from the beginning
    unpaged: boolean;   // Whether the results are unpaged
    paged: boolean;     // Whether the results are paged
  };
  totalPages: number;    // Total number of pages
  totalElements: number; // Total number of elements across all pages
  last: boolean;         // Whether this is the last page
  size: number;          // Number of elements in the current page
  number: number;        // Current page number (0-based)
  sort: {
    sorted: boolean;    // Whether the results are sorted
    unsorted: boolean;
    empty: boolean;
  };
  numberOfElements: number; // Number of elements currently on this page
  first: boolean;           // Whether this is the first page
  empty: boolean;           // Whether the page is empty
}
