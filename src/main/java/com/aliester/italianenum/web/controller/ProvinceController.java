package com.aliester.italianenum.web.controller;

import com.aliester.italianenum.exception.MunicipalitiesNotFoundException;
import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.model.Province;
import com.aliester.italianenum.service.impl.LocationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/provinces")
@RequiredArgsConstructor
@Tag(name = "Provinces and Municipalities", description = "Return all provinces and all municipalities")
public class ProvinceController {

    private final LocationServiceImpl service;
    @GetMapping()
    @Operation(
            summary = "Retrieve all provinces",
            description = "Get a the provinces from italy",
            tags = { "Provinces and Municipalities", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = Province.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public List<Province> getProvinces(){
        return service.getProvinces();
    }

    @GetMapping("/{provinceCode}/municipalities")
    @Operation(
            summary = "Retrieve all municipalities from a provinces",
            description = "Get a the municipalities from a province",
            tags = { "Provinces and Municipalities", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Municipality.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public List<Municipality> getMunicipalities(@PathVariable String provinceCode){
        return service.getMunicipalities(provinceCode);
    }

    @ExceptionHandler(MunicipalitiesNotFoundException.class)
    public ResponseEntity<String> handleMunicipalityNotFoundException(MunicipalitiesNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
