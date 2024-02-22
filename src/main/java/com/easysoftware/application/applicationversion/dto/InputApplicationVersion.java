package com.easysoftware.application.applicationversion.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputApplicationVersion {
    @Size(max = 255, message = "the length of version can not exceed 255")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_ ]+$", message = "Include only letters, digits, and special characters(_-()$.)")
    public String version;

    @NotBlank(message = "name can not be null")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_ ]+$", message = "Include only letters, digits, and special characters(_-()$.)")
    @Size(max = 255, message = "the length of name can not exceed 255")
    public String name;

    @Size(max = 255, message = "the length of homepage can not exceed 255")
    @Pattern(regexp = "^[A-Za-z0-9.()/\\-_ ]+$", message = "Include only letters, digits, and special characters(_-()$.)")
    public String homepage;

    @Size(max = 255, message = "the length of backend can not exceed 255")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_ ]+$", message = "Include only letters, digits, and special characters(_-()$.)")
    public String backend;
}
