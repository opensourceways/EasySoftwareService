package com.easysoftware.application.epkgpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageSearchCondition {
    /**
     * Name field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String name;

    /**
     * ID field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String id;

    /**
     * Package ID field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String pkgId;

    /**
     * Page number within the specified range defined by PackageConstant.MIN_PAGE_NUM and PackageConstant.MAX_PAGE_NUM.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    /**
     * Page size within the specified range defined by PackageConstant.MIN_PAGE_SIZE and PackageConstant.MAX_PAGE_SIZE.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    /**
     * Version field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String version;

    /**
     * Operating system field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    /**
     * Subpath field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String subPath;

    /**
     * Architecture field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    /**
     * Category field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;

    /**
     * EPKG update timestamp field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String epkgUpdateAt;

    /**
     * Time order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String timeOrder;

    /**
     * Name order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String nameOrder;

}
