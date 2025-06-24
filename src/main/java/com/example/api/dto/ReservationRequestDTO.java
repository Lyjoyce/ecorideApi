package com.example.api.dto;

import lombok.Data;
@Data

public class ReservationRequestDTO {
    private Long carpoolingId;

    public Long getCarpoolingId() {
        return carpoolingId;
    }

    public void setCarpoolingId(Long carpoolingId) {
        this.carpoolingId = carpoolingId;
    }
    
}

