package com.aliester.italianenum.web.controller;

import com.aliester.italianenum.exception.MunicipalitiesNotFoundException;
import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.model.Province;
import com.aliester.italianenum.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final LocationServiceImpl service;
    @GetMapping()
    public List<Province> getProvinces(){
        return service.getProvinces();
    }

    @GetMapping("/{provinceCode}/municipalities")
    public List<Municipality> getMunicipalities(@PathVariable String provinceCode){
        return service.getMunicipalities(provinceCode);
    }

    @ExceptionHandler(MunicipalitiesNotFoundException.class)
    public ResponseEntity<String> handleMunicipalityNotFoundException(MunicipalitiesNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
