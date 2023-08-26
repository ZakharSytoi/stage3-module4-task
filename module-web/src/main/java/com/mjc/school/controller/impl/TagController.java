package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/tags")
public class TagController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;

    @Override
    @GetMapping
    public ResponseEntity<Page<TagDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(tagService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.readById(id), HttpStatus.OK);
    }



    @Override
    @PostMapping
    public ResponseEntity<TagDtoResponse> create(@RequestBody TagDtoRequest createRequest) {
        return new ResponseEntity<>(tagService.create(createRequest), HttpStatus.valueOf(201));
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDtoResponse> update(
            @PathVariable Long id,
            @RequestBody TagDtoRequest updateRequest) {
        return new ResponseEntity<>(tagService.update(new TagDtoRequest(id,
                updateRequest.name())
        ), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), HttpStatus.valueOf(204));
    }
}
