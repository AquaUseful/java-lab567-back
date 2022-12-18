package org.psu.lab5.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NewApplicationResponse {
    private final boolean success;
    private final String message;
}
