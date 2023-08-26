package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.query.NewsQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/news")
public class NewsController implements BaseRestController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final NewsService newsService;
    private final CommentService commentService;
    private final AuthorService authorService;
    private final TagService tagService;

    @Override
    @GetMapping
    public ResponseEntity<Page<NewsDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(newsService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.readById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDtoResponse>> readByQueryParams(@RequestBody NewsQueryParams newsQueryParams) {
        return new ResponseEntity<>(newsService.readByQueryParams(newsQueryParams), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/comments")
    public ResponseEntity<List<CommentDtoResponse>> readCommentsByNewsId(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.readByNewsId(id), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/author")
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.readByNewsId(id), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/tags")
    public ResponseEntity<List<TagDtoResponse>> readTagsByNewsId(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.readByNewsId(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<NewsDtoResponse> create(@RequestBody NewsDtoRequest createRequest) {
        return new ResponseEntity<>(newsService.create(createRequest), HttpStatus.valueOf(201));
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDtoResponse> update(
            @PathVariable Long id,
            @RequestBody NewsDtoRequest updateRequest) {
        return new ResponseEntity<>(newsService.update(new NewsDtoRequest(id,
                updateRequest.title(),
                updateRequest.content(),
                updateRequest.authorId(),
                updateRequest.tagIds())), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.deleteById(id), HttpStatus.valueOf(204));
    }
}
