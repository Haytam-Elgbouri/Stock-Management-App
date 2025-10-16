package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.services.IBonDeReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brs")
public class BonDeReceptionController {

    private final IBonDeReceptionService blService;

    @PostMapping("/{id}")
    public ResponseEntity<BonDeReceptionDTO> addBr(@RequestBody CreateBRDTO createBRDTO, @PathVariable Long id){
        return ResponseEntity.ok(blService.addBR(createBRDTO,id));
    }

    @GetMapping
    public List<BonDeReceptionDTO> getAll(){
        return blService.getAll();
    }

    @GetMapping("/{id}")
    public BonDeReceptionDTO getById(@PathVariable Long id){
        return blService.getById(id);
    }

    @PostMapping("/deliver")
    public void deliver(@RequestBody BRRecieveDTO brRecieveDTO){
        blService.deliverBR(brRecieveDTO);
    }

    @PutMapping("/validate/{id}")
    public void validate(@PathVariable Long id){
        blService.validate(id);
    }

}
