package org.psu.lab5.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private List<ValidationViolation> violations;
}
