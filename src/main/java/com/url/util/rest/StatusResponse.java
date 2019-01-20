package com.url.util.rest;

public enum StatusResponse {
    OK("OK"),
    ERROR ("ERROR");

    private String status;

    StatusResponse(String status) {
        this.status = status;
    }
}