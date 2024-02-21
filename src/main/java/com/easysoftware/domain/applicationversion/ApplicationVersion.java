package com.easysoftware.domain.applicationversion;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersion {
    @Serial
    private static final long serialVersionUID = 1L;
    public String name;
    public String version;
    public String homepage;
    public String backend;
    private String id;
}
