package by.bulaukin.bookshelf.web.controller;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.mapper.BooksMapper;
import by.bulaukin.bookshelf.servise.BooksService;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import by.bulaukin.bookshelf.web.model.response.BooksEntityListResponse;
import by.bulaukin.bookshelf.web.model.response.BooksEntityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookEntityController {

    private final BooksMapper mapper;
    private final BooksService booksService;

    @GetMapping("/category")
    public ResponseEntity<BooksEntityListResponse> findAllByCategory(@RequestBody @Valid EntityFilter filter) {
        return ResponseEntity.ok(mapper.booksListToBooksEntityListResponse(booksService.getAllByCategory(filter)));
    }

    @GetMapping("/byAuthorAndBookName")
    public ResponseEntity<BooksEntityResponse> getByAuthorAndName(@RequestBody @Valid UpsertAuthorAndBookNameRequest request) {
        return ResponseEntity.ok(mapper.bookToResponse(booksService.getByAuthorAndName(request)));
    }

    @PostMapping
    public ResponseEntity<BooksEntityResponse> create(@RequestBody @Valid UpsertBooksEntityRequest request) {
        BooksEntity book = booksService.createBookEntity(mapper.requestToBooksEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.bookToResponse(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BooksEntityResponse> update(@PathVariable(value = "id") Long bookId,
                                                      @RequestBody @Valid UpsertBooksEntityRequest request) {
        BooksEntity updatedBook = booksService.update(mapper.requestToBooksEntity(bookId, request));
        return ResponseEntity.ok(mapper.bookToResponse(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        booksService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
