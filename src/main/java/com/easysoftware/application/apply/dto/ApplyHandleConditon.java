package com.easysoftware.application.apply.dto;

import com.easysoftware.common.constant.PackageConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyHandleConditon {
    /**
     * Field name with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    private Integer applyStatus;


    /**
     * Field name with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String maintainer;


    /**
     * Package ID with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    private Long applyId;


    /**
     * Page number within the range of PackageConstant.MIN_PAGE_NUM to PackageConstant.
     * MAX_PAGE_NUM, default value is 1.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    @NotNull
    private Integer pageNum = 1;

    /**
     * Page size within the range of PackageConstant.MIN_PAGE_SIZE to PackageConstant.
     * MAX_PAGE_SIZE, default value is 10.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    @NotNull
    private Integer pageSize = 10;


    /**
     * Time order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String timeOrder;

}
