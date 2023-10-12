package com.aliester.italianenum.service.impl;

import com.aliester.italianenum.exception.MunicipalitiesNotFoundException;
import com.aliester.italianenum.exception.MunicipalityNotFoundException;
import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.model.Province;
import com.aliester.italianenum.service.LocationService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private Set<Province> provinces;
    private List<Municipality> municipalities;

    private static final  String FILE_NAME  = "support/Elenco-comuni-italiani.xls";


    public LocationServiceImpl() {
        provinces = new HashSet<>();
        municipalities = new ArrayList<>();
        exelReader();
    }
    @Override
    public List<Municipality> getMunicipalities(String provinceCode) {
        List<Municipality> filteredMunicipalities = municipalities.stream()
                .filter(municipality -> provinceCode.equals(municipality.getProvinceCode()))
                .collect(Collectors.toList());
        if (filteredMunicipalities.isEmpty()) {
            throw new MunicipalitiesNotFoundException("Non si hanno trovato comuni con questo codice: " + provinceCode);
        }
        return filteredMunicipalities;
    }

    @Override
    public List<Province> getProvinces() {
        return provinces.stream()
                .filter(Objects::nonNull)
                .filter(province -> province.getProvinceName() != null && !province.getProvinceName().trim().isEmpty())
                .filter(province -> !("Denominazione provincia".equals(province.getProvinceName())))
                .sorted(Comparator.comparing(province -> Optional.ofNullable(province.getProvinceName()).orElse("")))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Municipality getMunicipality(String municipalityCode) {
        return municipalities.stream()
                .filter(municipality -> municipalityCode.equals(municipality.getMunicipalityCode()))
                .findFirst()
                .orElseThrow(() -> new MunicipalityNotFoundException("Municipio con codice " + municipalityCode + " non è stato trovato."));
    }

    private void exelReader() {
        try (InputStream file = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
             HSSFWorkbook workbook = new HSSFWorkbook(file)) {

            HSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Province province = new Province();

                if(row.getCell(11) != null) {
                    String cellValue = row.getCell(11).getStringCellValue();
                    if(!"-".equals(cellValue)) {
                        province.setProvinceName(cellValue);
                    } else if (row.getCell(10) != null) {
                        province.setProvinceName(row.getCell(10).getStringCellValue());
                    }
                }

                if(row.getCell(2) != null) {
                    province.setProvinceCode(row.getCell(2).getStringCellValue());
                }

                if(row.getCell(13) != null) {
                    province.setProvinceAbbreviate(row.getCell(13).getStringCellValue());
                }

                provinces.add(province);


                Municipality municipality = new Municipality();

                Cell cell = row.getCell(4);
                if(cell != null) {
                    if(cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        municipality.setMunicipalityCode(cellValue);
                    } else if(cell.getCellType() == CellType.NUMERIC) {
                        double numericValue = cell.getNumericCellValue();
                        String cellValue;
                        //faccio il controllo della cella nel file sono presenti campi numerici
                        if (numericValue % 1 == 0) {
                            cellValue = String.format("%.0f", numericValue);
                        } else {
                            cellValue = String.valueOf(numericValue);
                        }

                        // compilo manualmente in caso trovo una cella numerica
                        while (cellValue.length() < 6) {
                            cellValue = "0" + cellValue;
                        }

                        municipality.setMunicipalityCode(cellValue);
                    }
                }


                if(row.getCell(5) != null) {
                    municipality.setMunicipalityName(row.getCell(5).getStringCellValue());
                }

                if(row.getCell(11) != null) {
                    String cellValue = row.getCell(11).getStringCellValue();
                    if(!"-".equals(cellValue)) {
                        municipality.setProvinceName(cellValue);
                    } else if (row.getCell(10) != null) {
                        municipality.setProvinceName(row.getCell(10).getStringCellValue());
                    }
                }

                if(row.getCell(2) != null) {
                    municipality.setProvinceCode(row.getCell(2).getStringCellValue());
                }

                if(row.getCell(13) != null) {
                    municipality.setProvinceAbbreviate(row.getCell(13).getStringCellValue());
                }

                municipalities.add(municipality);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

