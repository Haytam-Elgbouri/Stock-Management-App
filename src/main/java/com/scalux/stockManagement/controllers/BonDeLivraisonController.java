package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.BLDeliverDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.dtos.CreateBRDTO;
import com.scalux.stockManagement.services.IBonDeLivraisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/bls")
@RequiredArgsConstructor
public class BonDeLivraisonController {
    private final IBonDeLivraisonService blService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<BonDeLivraisonDTO> addBl(@RequestBody CreateBRDTO createBRDTO, @PathVariable Long id){
        return ResponseEntity.ok(blService.addBl(createBRDTO, id));
    }

    @GetMapping
    public List<BonDeLivraisonDTO> getAll(){
        return blService.getAll();
    }

    @GetMapping(path = "/{id}")
    public BonDeLivraisonDTO getById(@PathVariable Long id){
        return blService.getById(id);
    }

    @PostMapping("/deliver")
    public void deliverBL(@RequestBody BLDeliverDTO blDeliverDTO){
        blService.deliverBL(blDeliverDTO);
    }

    @PostMapping("/validate/{id}")
    public void validate(@PathVariable Long id){
        blService.validate(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        blService.delete(id);
    }

}
