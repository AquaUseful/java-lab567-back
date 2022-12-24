package org.psu.lab5.controller;

import javax.print.attribute.standard.Media;

import org.psu.lab5.model.BinFile;
import org.psu.lab5.service.BinfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class BinFileController {

    @Autowired
    private BinfileService binfileService;

    @GetMapping(path = "/{id}")
    ResponseEntity<byte[]> getFile(@PathVariable("id") Long id) {
        final BinFile file = binfileService.getById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, file.getMimeType())
                .body(file.getData());
    }
}
