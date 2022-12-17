package org.psu.lab5.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import org.psu.lab5.model.User;
import org.psu.lab5.service.UserService;
import org.psu.lab5.service.BinfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.psu.lab5.model.BinFile;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BinfileService binfileService;

    @GetMapping("")
    public ResponseEntity<User> selfInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getByUsername(userService.authInfo().getUsername()));
    }

    @PostMapping(value = "file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Null> uploadAvatar(@RequestPart("file") MultipartFile request) throws IOException {
        final String username = userService.authInfo().getUsername();
        User user = userService.getByUsername(username);
        if (user.getFile() != null) {
            user.getFile().setData(request.getBytes());
            user.getFile().setMimeType(request.getContentType());
        } else {
            BinFile binfile = binfileService.addMultipart(request, user);
            System.out.println("Binfile id: " + binfile.getId());
            user.setFile(binfile);
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("file")
    public ResponseEntity<byte[]> downloadAvatar() {
        final String username = userService.authInfo().getUsername();
        final User user = userService.getByUsername(username);
        final BinFile file = user.getFile();
        if (file != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, file.getMimeType())
                    .body(file.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
