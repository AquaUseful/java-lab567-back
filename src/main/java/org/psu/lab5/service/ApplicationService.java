package org.psu.lab5.service;

import java.io.IOException;

import javax.validation.Valid;

import org.psu.lab5.model.Application;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.NewApplicationRequest;
import org.psu.lab5.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private BinfileService binfileService;

    public void save(Application appl) {
        applicationRepository.save(appl);
    }

    public void addApplicationForUser(User user, @Valid NewApplicationRequest request) throws IOException {

        final BinFile attachment = binfileService.addMultipart(request.getAttachment());

        final Application appl = new Application(null,
                request.getDoctorName(),
                request.getService(),
                attachment, user);

        applicationRepository.save(appl);
    }
}
