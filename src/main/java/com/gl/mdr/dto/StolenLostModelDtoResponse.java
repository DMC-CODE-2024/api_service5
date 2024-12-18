package com.gl.mdr.dto;

import java.util.List;

public class StolenLostModelDtoResponse {

    private List<StolenLostModelDto> content = null;
    //private Pageable pageable;
    private Integer totalPages;
    private long totalElements;

    public List<StolenLostModelDto> getContent() {
        return content;
    }

    public void setContent(List<StolenLostModelDto> content) {
        this.content = content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public String toString() {
        return "StolenLostModelDtoResponse{" +
                "content=" + content +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}
