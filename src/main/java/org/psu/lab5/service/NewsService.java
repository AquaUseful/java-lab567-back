package org.psu.lab5.service;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.psu.lab5.exception.ResourceNotFoundException;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.model.News;
import org.psu.lab5.pojo.NewNewsRequest;
import org.psu.lab5.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    BinfileService binfileService;

    public void save(News news) {
        newsRepository.save(news);
    }

    public void newNews(@Valid NewNewsRequest request) throws IOException {
        BinFile picture = null;
        if (request.getPicture() != null) {
            picture = binfileService.addMultipart(request.getPicture());
        }
        final News news = new News(null, request.getTitle(), request.getContent(), 0L, picture);
        this.save(news);
    }

    public Collection<News> getAll() {
        final Collection<News> allNews = newsRepository.findAll();
        return allNews;
    }

    public News getById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News not found"));
    }

}
