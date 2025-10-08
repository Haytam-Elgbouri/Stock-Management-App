package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.services.IBonDeLivraisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bls")
public class BonDeLivraisonController {

    private final IBonDeLivraisonService blService;

    @PostMapping("/{id}")
    public ResponseEntity<BonDeLivraisonDTO> addBl(@RequestBody CreateBLDTO createBLDTO, @PathVariable Long id){
        return ResponseEntity.ok(blService.addBL(createBLDTO,id));
    }

    @GetMapping
    public List<BonDeLivraisonDTO> getAll(){
        return blService.getAll();
    }

    @GetMapping("/{id}")
    public BonDeLivraisonDTO getById(@PathVariable Long id){
        return blService.getById(id);
    }

    @PostMapping("/deliver")
    public void deliver(@RequestBody DeliverDTO deliverDTO){
        blService.deliver(deliverDTO);
    }

    @PutMapping("/validate/{id}")
    public void validate(@PathVariable Long id){
        blService.validate(id);
    }

}
