package org.psu.lab5.pojo;

import java.util.Collection;

import org.psu.lab5.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatch {
    private String username;
    private String email;
    private String password;
    private Collection<Role> roles;
}
