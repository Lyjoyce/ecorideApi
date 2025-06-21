package com.example.api.mappers;

import com.example.api.dto.SeatAvailableRequestDTO;
import com.example.api.entities.SeatAvailable;

public class SeatAvailableMapper {

    public static SeatAvailable fromDTO(SeatAvailableRequestDTO dto) {
        SeatAvailable seat = new SeatAvailable();
        seat.setFromCity(dto.getFromCity());
        seat.setToCity(dto.getToCity());
        seat.setDepartureDate(dto.getDepartureDate());
        seat.setDepartureTime(dto.getDepartureTime());
        seat.setArrivalDate(dto.getArrivalDate());
        seat.setArrivalTime(dto.getArrivalTime());
        seat.setValue(dto.getValue());
        return seat;
    }
}

