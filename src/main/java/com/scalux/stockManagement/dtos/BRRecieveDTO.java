package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BRRecieveDTO {
    private Long brId; // ID of the BR
    private List<BRLineReceiveDTO> lines;
}
