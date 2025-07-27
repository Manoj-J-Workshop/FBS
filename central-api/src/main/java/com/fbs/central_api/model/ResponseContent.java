package com.fbs.central_api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ResponseContent {
    private List<ResponsePart> parts;
    private String role;
}