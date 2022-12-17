package org.psu.lab5.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationViolation {
    private String fieldName;
    private String message;
}
