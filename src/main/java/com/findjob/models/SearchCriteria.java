package com.findjob.models;

import lombok.Data;

@Data
public class SearchCriteria {
    private int categoryId;
    private String offerType;
    private int minSalary;
    private int maxSalary;
}
