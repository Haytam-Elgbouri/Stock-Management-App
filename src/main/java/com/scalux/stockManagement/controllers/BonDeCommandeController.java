package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.DeliverDTO;
import com.scalux.stockManagement.services.IBonDeCommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/bcs")
@RequiredArgsConstructor
public class BonDeCommandeController {

    private final IBonDeCommandeService bcService;

    @PostMapping
    public ResponseEntity<BonDeCommandeDTO> add(@RequestBody @Valid BonDeCommandeDTO dto) {
        return ResponseEntity.ok(bcService.addBC(dto));
    }

    @GetMapping
    public List<BonDeCommandeDTO> getAll() {
        return bcService.getAll();
    }

    @GetMapping("/{id}")
    public BonDeCommandeDTO getById(@PathVariable Long id) {
        return bcService.getById(id);
    }

    @PutMapping("/{id}")
    public BonDeCommandeDTO update(@PathVariable Long id, @RequestBody BonDeCommandeDTO dto) {
        return bcService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bcService.delete(id);
    }

//    @PutMapping("/{id}/validate")
//    public ResponseEntity<BonDeCommandeDTO> validateBC(@PathVariable Long id) {
//        return ResponseEntity.ok(bcService.validateBC(id));
//    }

    @PostMapping("/deliver")
    public void deliver(@RequestBody DeliverDTO deliverDTO){
        bcService.deliver(deliverDTO);
    }
}
