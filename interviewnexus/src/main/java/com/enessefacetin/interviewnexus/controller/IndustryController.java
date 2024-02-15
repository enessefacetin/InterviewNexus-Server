package com.enessefacetin.interviewnexus.controller;

import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Industry;
import com.enessefacetin.interviewnexus.model.request.InsertIndustryRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateIndustryRequest;
import com.enessefacetin.interviewnexus.model.response.IndustryResponse;
import com.enessefacetin.interviewnexus.service.IndustryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/industry")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    @GetMapping
    public List<IndustryResponse> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryResponse> getIndustryById(@PathVariable Long id) {
        var industryResponse = industryService.getIndustryById(id);
        return ResponseEntity.ok().body(industryResponse);
    }

    @PostMapping
    public ResponseEntity<String> createIndustry(@Valid @RequestBody InsertIndustryRequest industry) {
        Industry createdIndustry = industryService.createIndustry(industry);
        return ResponseEntity.status(HttpStatus.CREATED).body("Industry created with id: " + createdIndustry.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateIndustry(@PathVariable Long id, @Valid @RequestBody UpdateIndustryRequest industryDetails) {
        Industry updatedIndustry = industryService.updateIndustry(id, industryDetails);
        return ResponseEntity.ok().body("Industry updated with id: " + updatedIndustry.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
        return ResponseEntity.ok().body("Industry deleted with id: " + id);
    }
    

}
