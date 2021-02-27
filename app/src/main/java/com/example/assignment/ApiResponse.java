package com.example.assignment;

public class ApiResponse {
    private final int status;
    private final String status_verbose;
    private final ProductsEntity product;
    private final String code;

    public ApiResponse(int status, String status_verbose, ProductsEntity product, String code) {
        this.status = status;
        this.status_verbose = status_verbose;
        this.product = product;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public String getStatus_verbose() {
        return status_verbose;
    }

    public ProductsEntity getProduct() {
        return product;
    }

    public String getCode() {
        return code;
    }
}
