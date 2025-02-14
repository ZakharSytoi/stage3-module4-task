package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/comments")
public class CommentController implements BaseRestController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final CommentService commentService;
    @Override
    @GetMapping
    public ResponseEntity<Page<CommentDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0")int pageNo,
            @RequestParam(required = false, defaultValue = "10")int pageSize) {
        return new ResponseEntity<>(commentService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDtoResponse> create(@RequestBody CommentDtoRequest createRequest) {
        return new ResponseEntity<>(commentService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDtoResponse> update(
            @PathVariable Long id,
            @RequestBody CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(new CommentDtoRequest(id, updateRequest.content(), updateRequest.newsId())), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
