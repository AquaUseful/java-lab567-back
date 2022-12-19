package org.psu.lab5.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.NewApplicationRequest;
import org.psu.lab5.pojo.NewUserRequest;
import org.psu.lab5.pojo.RegisterResponse;
import org.psu.lab5.service.UserService;
import org.psu.lab5.service.ApplicationService;
import org.psu.lab5.service.BinfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.psu.lab5.model.BinFile;
import org.psu.lab5.exception.UserExistsException;
import org.psu.lab5.model.Application;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BinfileService binfileService;

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/self")
    public ResponseEntity<User> selfInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getByUsername(userService.authInfo().getUsername()));
    }

    @PostMapping(path = "/self/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Null> uploadAvatar(@RequestPart("file") MultipartFile request) throws IOException {
        final String username = userService.authInfo().getUsername();
        User user = userService.getByUsername(username);
        if (user.getFile() != null) {
            user.getFile().setData(request.getBytes());
            user.getFile().setMimeType(request.getContentType());
        } else {
            BinFile binfile = binfileService.addMultipart(request);
            user.setFile(binfile);
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/self/file")
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

    @GetMapping("/self/application")
    public ResponseEntity<Collection<Application>> getApplications() {
        final String username = userService.authInfo().getUsername();
        final User user = userService.getByUsername(username);
        final Collection<Application> applications = user.getApplications();
        return ResponseEntity.ok().body(applications);
    }

    @PostMapping(path = "/self/application")
    public ResponseEntity<Null> postApplication(@ModelAttribute @Valid NewApplicationRequest request)
            throws IOException {
        final String username = userService.authInfo().getUsername();
        final User user = userService.getByUsername(username);
        applicationService.addApplicationForUser(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        final User user = userService.getByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{username}")
    public ResponseEntity<Null> patchUser(@PathVariable("username") String username,
            @RequestBody NewUserRequest patch) {
        userService.applyPatchByUsername(username, patch);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<Collection<User>> getUsers() {
        final Collection<User> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "")
    public ResponseEntity<RegisterResponse> postUser(@RequestBody @Valid NewUserRequest request)
            throws UserExistsException {
        userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(true, ""));
    }

}
