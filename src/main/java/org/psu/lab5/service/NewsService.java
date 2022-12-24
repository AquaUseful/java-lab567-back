package org.psu.lab5.service;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.psu.lab5.exception.ResourceNotFoundException;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.model.News;
import org.psu.lab5.pojo.NewNewsRequest;
import org.psu.lab5.pojo.NewsPatchRequest;
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
        if ((request.getPicture() != null) && (request.getPicture().getSize() > 0)) {
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

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    public void patchNewsById(Long id, NewsPatchRequest patch) throws IOException {
        News news = newsRepository.getReferenceById(id);

        if (patch.getTitle() != null) {
            news.setTitle(patch.getTitle());
        }
        if (patch.getContent() != null) {
            news.setContent(patch.getContent());
        }
        if (patch.getDeletePicture() != null) {
            news.setPicture(null);
        } else {
            if ((patch.getPicture() != null) && (patch.getPicture().getSize() > 0)) {
                final BinFile newPicture = binfileService.addMultipart(patch.getPicture());
                news.setPicture(newPicture);
            }
        }

        newsRepository.save(news);
    }

}
