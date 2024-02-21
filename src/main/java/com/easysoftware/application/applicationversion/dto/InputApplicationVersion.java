package com.easysoftware.application.applicationversion.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputApplicationVersion {
    @Size(max = 255, message = "the length of description can not exceed 255")
    public String version;

    @NotBlank(message = "name can not be null")
    @Size(max = 255, message = "the length of name can not exceed 255")
    public String name;

    @Size(max = 255, message = "the length of arch can not exceed 255")
    public String homepage;

    @Size(max = 255, message = "the length of arch can not exceed 255")
    public String backend;
}
