package org.psu.lab5.pojo;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;

@Getter
@Setter
@RequiredArgsConstructor
public class NewsPatchRequest {
    @NotBlank(message = "Пустой заголовок новости!")
    private String title;
    @NotBlank(message = "Пустое содержание новости!")
    private String content;
    private String deletePicture;
    private MultipartFile picture;
}
