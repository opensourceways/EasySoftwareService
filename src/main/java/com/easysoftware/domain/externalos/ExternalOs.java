package com.easysoftware.domain.externalos;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalOs {
    @Serial
    private static final long serialVersionUID = 1L;

    private String originOsName;

    private String originOsVer;

    private String originPkg;

    private String targetOsName;

    private String targetOsVer;

    private String targetPkg;
}
