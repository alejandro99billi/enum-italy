Location Service Implementation
Overview
LocationServiceImpl is a Java service class designed to manage geographical data related to Italy, specifically provinces and municipalities. It provides functionality to retrieve lists of municipalities and provinces, as well as individual municipality details based on provided codes. The service reads data from an Excel file (Elenco-comuni-italiani.xls) at initialization, parsing and storing this information in-memory for quick access.

Features
Retrieve a list of municipalities within a specific province using the province code.
Fetch all provinces, excluding duplicates and non-relevant entries.
Get details of a specific municipality by its code.
Dependencies
This service relies on Apache POI to read Excel files, specifically the HSSFWorkbook and HSSFSheet classes for handling HSSF (Horrible Spreadsheet Format) - the file format used by Excel 97-2003. Ensure you include Apache POI in your project's dependencies.



Get Municipalities by Province Code
java
Copy code
LocationService service = new LocationServiceImpl();
List<Municipality> municipalities = service.getMunicipalities("Province_Code");
Get All Provinces
java
Copy code
LocationService service = new LocationServiceImpl();
List<Province> provinces = service.getProvinces();
Get Municipality Details by Code
java
Copy code
LocationService service = new LocationServiceImpl();
Municipality municipality = service.getMunicipality("Municipality_Code");
Exception Handling
The service throws custom exceptions (MunicipalitiesNotFoundException, MunicipalityNotFoundException) when searches return no results. Catch these exceptions in your application to handle such cases gracefully.

Contributing
Feel free to contribute to the improvement of LocationServiceImpl by submitting pull requests or opening issues for bugs and feature requests.

How to Deploy with docker : 
1) use mvn clean instal for generate the .jar
2) use  docker build -t italian-enum:ita-enum for build the images
3) use  docker run  -p 8080:8080  --name enum-provider italian-enum:ita-enum for run the container