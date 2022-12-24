package org.psu.lab5.pojo;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApplicationPatchRequest {

    @NotBlank(message = "Пустое имя врача!")
    private final String doctorName;

    @NotBlank(message = "Пустое название услуги!")
    private final String service;

    private final String deleteAttachment;

    private final MultipartFile attachment;
}
