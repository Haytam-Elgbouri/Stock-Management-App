package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.ColorDTO;
import com.scalux.stockManagement.services.IColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/colors")
public class ColorController {
    private final IColorService colorService;

    @PostMapping
    public ResponseEntity<ColorDTO> addColor(@RequestBody ColorDTO colorDTO){
        return ResponseEntity.ok(colorService.addColor(colorDTO));
    }

    @GetMapping
    public ResponseEntity<List<ColorDTO>> getAll() {
        return ResponseEntity.ok(colorService.getAllColors());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteColor(@PathVariable Long id){
        colorService.deleteColor(id);
    }
}
