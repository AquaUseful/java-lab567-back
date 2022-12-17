package org.psu.lab5.pojo;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewApplicationRequest {
    @NotBlank(message = "Пустое ФИО врача")
    private String doctorName;
    @NotBlank(message = "Пустое название услуги")
    private String service;

    private MultipartFile attachment;
}
