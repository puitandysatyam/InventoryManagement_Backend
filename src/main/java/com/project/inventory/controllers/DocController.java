package com.project.inventory.controllers;

import com.project.inventory.entity.Consumables;
import com.project.inventory.entity.Electronics;
import com.project.inventory.entity.Scrap;
import com.project.inventory.repository.ConsumablesRepository;
import com.project.inventory.repository.ElectronicsRepository;
import com.project.inventory.repository.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class DocController {

    @Autowired
    ElectronicsRepository electronicsRepository;
    @Autowired
    ConsumablesRepository consumablesRepository;
    @Autowired
    ScrapRepository scrapRepository;

    @GetMapping("/electronics/view")
    public ResponseEntity<List<Electronics>> getElectronicsItems(){
        return ResponseEntity.ok(electronicsRepository.findAll());
    }

    @GetMapping("/consumables/view")
    public ResponseEntity<List<Consumables>> getConsumablesItems(){
        return ResponseEntity.ok(consumablesRepository.findAll());
    }

    @GetMapping("/scrap/view")
    public ResponseEntity<List<Scrap>> getScrapItems(){
        return ResponseEntity.ok(scrapRepository.findAll());
    }

    @PostMapping("/electronics/save")
    public ResponseEntity<String > saveElectronics(@RequestBody Electronics electronics){
        try {
            electronicsRepository.save(electronics);
            return ResponseEntity.status(200).body("Electronics saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving Electronics");
        }
    }

    @PostMapping("/consumables/save")
    public ResponseEntity<String> saveConsumables(@RequestBody Consumables consumables){
        try {
            consumablesRepository.save(consumables);
            return ResponseEntity.status(200).body("Saved Consumables Succesfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving Consumables");
        }
    }
}
