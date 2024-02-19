package com.enessefacetin.interviewnexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Profession;
import com.enessefacetin.interviewnexus.model.request.InsertProfessionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateProfessionRequest;
import com.enessefacetin.interviewnexus.model.response.ProfessionDetailResponse;
import com.enessefacetin.interviewnexus.model.response.ProfessionResponse;
import com.enessefacetin.interviewnexus.service.ProfessionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profession")
@RequiredArgsConstructor
@Tag(name = "Profession")
@PreAuthorize("hasAnyAuthority('User', 'Admin')")
public class ProfessionController {

    private final ProfessionService professionService;

    @GetMapping
    public List<ProfessionResponse> getAllProfessions() {
        return professionService.getAllProfessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionDetailResponse> getProfessionById(@PathVariable Long id) {
        var professionResponse = professionService.getProfessionById(id);
        return ResponseEntity.ok().body(professionResponse);
    }

    @PostMapping
    public ResponseEntity<String> createProfession(@Valid @RequestBody InsertProfessionRequest profession) {
        Profession createdProfession = professionService.createProfession(profession);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profession created with id: " + createdProfession.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfession(@PathVariable Long id, @Valid @RequestBody UpdateProfessionRequest professionDetails) {
        Profession updatedProfession = professionService.updateProfession(id, professionDetails);
        return ResponseEntity.ok().body("Profession updated with id: " + updatedProfession.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfession(@PathVariable Long id) {
        professionService.deleteProfession(id);
        return ResponseEntity.ok().body("Profession deleted with id: " + id);
    }
}
