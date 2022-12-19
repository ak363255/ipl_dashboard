package com.example.springBatchExample.data;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequest {
    public static int  PAGE_SIZE = 10;
    private int pageNo;
    private Object data;

    private Boolean nextPageAvailable;

    public PageRequest(Object data, boolean nextPageAvailable,int pageNo){
        this.data = data;
        this.pageNo = pageNo;
        this.nextPageAvailable = nextPageAvailable;
    }
}
