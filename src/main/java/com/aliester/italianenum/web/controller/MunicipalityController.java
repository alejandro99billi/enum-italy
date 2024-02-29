package com.aliester.italianenum.web.controller;

import com.aliester.italianenum.exception.MunicipalityNotFoundException;
import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.service.impl.LocationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
@Tag(name = "Municipality", description = "Single municipality")
public class MunicipalityController {

    private final LocationServiceImpl service;


    @Operation(
            summary = "Retrieve a municipality",
            description = "Get a municipality from italy",
            tags = { "Municipality", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Municipality.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{municipalityCode}")
    public Municipality getMunicipality(@PathVariable String municipalityCode){
        return service.getMunicipality(municipalityCode);
    }

    @ExceptionHandler(MunicipalityNotFoundException.class)
    public ResponseEntity<String> handleMunicipalityNotFoundException(MunicipalityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}