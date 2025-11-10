package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.BonDeCommandeClientDTO;
import com.scalux.stockManagement.services.IBonDeCommandeClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/bccs")
@RequiredArgsConstructor
public class BonDeCommandeClientController {

    private final IBonDeCommandeClientService bccService;

    @PostMapping
    public ResponseEntity<BonDeCommandeClientDTO> AddBccs(@RequestBody BonDeCommandeClientDTO dto){
        return ResponseEntity.ok(bccService.addBcc(dto));
    }

    @GetMapping
    public List<BonDeCommandeClientDTO> GetAllBccs(){
        return bccService.getAll();
    }

    @GetMapping(path = "/{id}")
    public BonDeCommandeClientDTO GetBccById(@PathVariable Long id){
        return bccService.getById(id);
    }
}
