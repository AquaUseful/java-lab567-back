package org.psu.lab5.service;

import java.io.IOException;

import org.psu.lab5.model.User;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.repository.BinFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BinfileService {
    @Autowired
    BinFileRepository binFileRepository;

    public void add(BinFile newFile) {
        binFileRepository.save(newFile);
    }

    public BinFile addMultipart(MultipartFile file, User user) throws IOException {
        final BinFile newBinFile = new BinFile(null, file.getContentType(), file.getBytes());
        this.add(newBinFile);
        return newBinFile;
    };
}
