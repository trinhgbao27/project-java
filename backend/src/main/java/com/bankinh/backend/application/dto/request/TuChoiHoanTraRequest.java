package com.bankinh.backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TuChoiHoanTraRequest {

    @NotBlank
    private String lyDoTuChoi;
}