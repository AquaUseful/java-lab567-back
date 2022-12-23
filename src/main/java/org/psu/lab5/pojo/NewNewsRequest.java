package org.psu.lab5.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewNewsRequest {
    @NotBlank(message = "Пустой заголовок новости!")
    private String title;
    @NotBlank(message = "Пустое содержание новости!")
    private String content;
    private MultipartFile picture;
}
