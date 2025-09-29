package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.services.IBonDeLivraisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bls")
public class BonDeLivraisonController {

    private final IBonDeLivraisonService blService;

    @PostMapping("/{id}")
    public ResponseEntity<BonDeLivraisonDTO> addBl(@RequestBody BonDeLivraisonDTO bonDeLivraisonDTO, @PathVariable Long id){
        return ResponseEntity.ok(blService.addBL(bonDeLivraisonDTO,id));
    }

}
