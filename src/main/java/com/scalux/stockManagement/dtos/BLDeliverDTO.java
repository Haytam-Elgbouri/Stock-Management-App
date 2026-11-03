package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BLDeliverDTO {
    private Long blId;
    private List<BLLineDeliverDTO> lines;
}
