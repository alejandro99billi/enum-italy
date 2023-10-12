package com.aliester.italianenum.service;

import com.aliester.italianenum.model.Municipality;
import com.aliester.italianenum.model.Province;

import java.util.List;

public interface LocationService {
    List<Municipality> getMunicipalities(String provinceCode);

    List<Province> getProvinces();

    Municipality getMunicipality(String municipalityCode);
}
