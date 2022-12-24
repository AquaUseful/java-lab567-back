package org.psu.lab5.service;

import java.io.IOException;

import javax.validation.Valid;

import org.psu.lab5.exception.ResourceNotFoundException;
import org.psu.lab5.model.Application;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.ApplicationPatchRequest;
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

    public void addApplicationForUser(User user, @Valid NewApplicationRequest request) throws IOException {
        BinFile attachment = null;
        if ((request.getAttachment() != null) && (request.getAttachment().getSize() > 0)) {
            attachment = binfileService.addMultipart(request.getAttachment());
        }
        final Application appl = new Application(null,
                request.getDoctorName(),
                request.getService(),
                attachment, user);
        applicationRepository.save(appl);
    }

    public void patchApplicationById(Long id, ApplicationPatchRequest patch) throws IOException {
        Application application = applicationRepository.getReferenceById(id);

        if (patch.getDoctorName() != null) {
            application.setDoctorName(patch.getDoctorName());
        }
        if (patch.getService() != null) {
            application.setService(patch.getService());
        }
        if (patch.getDeleteAttachment() != null) {
            application.setAttachment(null);
        } else {
            if ((patch.getAttachment() != null) && (patch.getAttachment().getSize() > 0)) {
                final BinFile newAttachment = binfileService.addMultipart(patch.getAttachment());
                application.setAttachment(newAttachment);
            }
        }

        applicationRepository.save(application);
    }

    public void deleteById(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Заявка не существует");
        }
        applicationRepository.deleteById(id);
    }

}
