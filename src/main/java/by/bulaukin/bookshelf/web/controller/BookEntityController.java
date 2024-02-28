package by.bulaukin.bookshelf.web.controller;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.mapper.BooksMapper;
import by.bulaukin.bookshelf.servise.BooksService;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import by.bulaukin.bookshelf.web.model.response.BooksEntityListResponse;
import by.bulaukin.bookshelf.web.model.response.BooksEntityResponse;
import by.bulaukin.bookshelf.web.model.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get all books by category.",
            description = "Get all books, by filter(category name). Return a list of books",
            tags = {"Category"}

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BooksEntityListResponse.class),
                            mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/category")
    public ResponseEntity<BooksEntityListResponse> findAllByCategory(@Valid EntityFilter filter) {
        return ResponseEntity.ok(mapper.booksListToBooksEntityListResponse(booksService.getAllByCategory(filter)));
    }

    @Operation(summary = "Find book by Author and category name.",
            description = "Find book book by author name and category name. Return book name, author name and category name.",
            tags = "BOOKS")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = BooksEntityResponse.class),
                            mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/byAuthorAndBookName")
    public ResponseEntity<BooksEntityResponse> getByAuthorAndName(@Valid UpsertAuthorAndBookNameRequest request) {
        return ResponseEntity.ok(mapper.bookToResponse(booksService.getByAuthorAndName(request)));
    }

    @Operation(
            summary = "Create a new book.",
            description = "Create a new book, return book name, author name and category name.",
            tags = "BOOKS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = BooksEntityResponse.class),
                            mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<BooksEntityResponse> create(@RequestBody @Valid UpsertBooksEntityRequest request) {
        BooksEntity book = booksService.createBookEntity(mapper.requestToBooksEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.bookToResponse(book));
    }

    @Operation(
            summary = "Update a book by id.",
            description = "Update a book by id. Return book name, author name and category name.",
            tags ={"ID", "BOOKS"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = BooksEntityResponse.class),
                            mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<BooksEntityResponse> update(@PathVariable(value = "id") Long bookId,
                                                      @RequestBody @Valid UpsertBooksEntityRequest request) {
        BooksEntity updatedBook = booksService.update(mapper.requestToBooksEntity(bookId, request));
        return ResponseEntity.ok(mapper.bookToResponse(updatedBook));
    }

    @Operation(
            summary = "Delete a book by book id",
            description = "Delete a book by book id. Return no content.",
            tags = {"ID", "BOOKS"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        booksService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
