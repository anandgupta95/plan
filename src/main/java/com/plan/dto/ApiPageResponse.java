package com.plan.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiPageResponse<T> {
    private boolean success;
    private String message;
    private List<T> data;
    private int pageSize;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public static <T> ApiPageResponse<T> fromPage(Page<T> page, String message) {
        return ApiPageResponse.<T>builder()
                .success(true)
                .message(message)
                .data(page.getContent())
                .pageSize(page.getSize())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
