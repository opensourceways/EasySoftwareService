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
    public String upHomepage;
    public String eulerHomepage;
    public String backend;
    public String upstreamVersion;
    public String openeulerVersion;
    public String ciVersion;
    public String status;
    private String id;
}
