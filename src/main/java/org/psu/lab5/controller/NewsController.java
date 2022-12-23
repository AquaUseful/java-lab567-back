package org.psu.lab5.controller;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.psu.lab5.exception.ResourceNotFoundException;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.model.News;
import org.psu.lab5.pojo.NewNewsRequest;
import org.psu.lab5.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping(path = "")
    public ResponseEntity<Null> postNews(@ModelAttribute @Valid NewNewsRequest request) throws IOException {
        newsService.newNews(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<News>> getNews() {
        final Collection<News> news = newsService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(news);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") Long id) {
        final News news = newsService.getById(id);
        return ResponseEntity.ok().body(news);
    }

    @GetMapping(path = "/{id}/attachment")
    public ResponseEntity<byte[]> getNewsAttachmentById(@PathVariable("id") Long id) {
        final BinFile attachment = newsService.getById(id).getPicture();
        if (attachment == null) {
            throw new ResourceNotFoundException("Изображение новости не найдено");
        }
        return ResponseEntity.ok().body(attachment.getData());
    }

}
