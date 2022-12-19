package com.example.springBatchExample.data;

import lombok.Data;

@Data
public class ResponseModel {
    private Object data;
    private int status;
    private String message;
    public ResponseModel(Object data,String message){
        if(data==null){
            this.data = data;
            this.message = message;
            this.status = 0;
        }else{
            this.data = data;
            this.status = 1;
            this.message = message;
        }
    }
}
