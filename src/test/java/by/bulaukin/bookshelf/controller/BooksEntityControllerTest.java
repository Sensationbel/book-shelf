package by.bulaukin.bookshelf.controller;

import by.bulaukin.bookshelf.AbstractTest;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import by.bulaukin.bookshelf.web.model.response.BooksEntityListResponse;
import by.bulaukin.bookshelf.web.model.response.BooksEntityResponse;
import by.bulaukin.bookshelf.web.model.response.ErrorResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BooksEntityControllerTest extends AbstractTest {

    @Test
    public void whenGetAllEntitiesByCategoryName_thanReturnBooksEntityList() throws Exception{
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/book/category?categoryName=category1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BooksEntityListResponse response = mapper.booksListToBooksEntityListResponse(booksService.getAllByCategory(new EntityFilter("category1")));
        String expectedResponse = objectMapper.writeValueAsString(response);

        assertFalse(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetAllEntitiesByBookNameAndAuthor_thanReturnEntityNotFound() throws Exception{
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/book/byAuthorAndBookName?namedBook=book5&author=bdf"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper
                .writeValueAsString(new ErrorResponse("Book by bookName: book5 and author: bdf not found!"));

        assertTrue(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenGetEntityByBookNameAndAuthor_thanReturnBooksEntityResponse() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/book/byAuthorAndBookName?namedBook=book1&author=bdf"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BooksEntityResponse response = mapper
                .bookToResponse(booksService.getByAuthorAndName(new UpsertAuthorAndBookNameRequest("book1", "bdf")));
        String expectedResponse = objectMapper.writeValueAsString(response);

        assertFalse(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateBookEntity_thanReturnBooksEntityResponseAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(3, bookRepository.count());

        mockMvc.perform(get("/api/v1/book/byAuthorAndBookName?namedBook=book2&author=bdf"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/api/v1/book/category?categoryName=category1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());

        UpsertBooksEntityRequest request = new UpsertBooksEntityRequest("book2", "bdf", "category1");;

        String actualResponse = mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper
                .writeValueAsString(new BooksEntityResponse("book2", "bdf", "category1"));

        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(4, bookRepository.count());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateBooksEntity_thenReturnBookEntityResponseAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        mockMvc.perform(get("/api/v1/book/byAuthorAndBookName?namedBook=book2&author=bdf"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/api/v1/book/category?categoryName=category1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());

        UpsertBooksEntityRequest request = new UpsertBooksEntityRequest("book2", "bdf", "category1");

        String actualResponse = mockMvc.perform(put("/api/v1/book/33")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper
                .writeValueAsString(new BooksEntityResponse("book2", "bdf", "category1"));

        assertTrue(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }

    @Test
    public void whenUpdateBookEntity_thenReturnErrorResponseNotFound() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        UpsertBooksEntityRequest request = new UpsertBooksEntityRequest
                ("book2", "bdf", "category1");

        String actualErrorResponse = mockMvc.perform(put("/api/v1/book/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(new ErrorResponse("Book by id 3 was not found"));

        assertTrue(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualErrorResponse);
    }

    @Test
    public void whenDeleteBookEntityById_thenDeleteBookEntityAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(3, bookRepository.count());

        mockMvc.perform(get("/api/v1/book/byAuthorAndBookName?namedBook=book3&author=bdf"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        mockMvc.perform(get("/api/v1/book/category?categoryName=category1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());
        System.out.println("count: " + bookRepository.count());

        mockMvc.perform(delete("/api/v1/book/33"))
                .andExpect(status().isNoContent());
        System.out.println("count: " + bookRepository.count());

        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(2, bookRepository.count());
    }

    @Test
    public void whenDeleteBooksEntity_thenReturnErrorResponseNotFound() throws Exception {
        String actualResponse = mockMvc.perform(delete("/api/v1/book/40"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(new ErrorResponse("Book by id 40 was not found"));

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

}
