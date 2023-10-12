package com.aliester.italianenum.web.controller;

import com.aliester.italianenum.exception.MunicipalityNotFoundException;
import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class MunicipalityController {

    private final LocationServiceImpl service;

    @GetMapping("/{municipalityCode}")
    public Municipality getMunicipality(@PathVariable String municipalityCode){
        if(municipalityCode != null){}
        return service.getMunicipality(municipalityCode);
    }

    @ExceptionHandler(MunicipalityNotFoundException.class)
    public ResponseEntity<String> handleMunicipalityNotFoundException(MunicipalityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}