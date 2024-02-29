# Italian Municipalities and Provinces Providers

Retrieve a list of municipalities within a specific province using the province code.
Fetch all provinces, excluding duplicates and non-relevant entries.
Get details of a specific municipality by its code.

## API References

#### Get All provinces from italy

```http
  GET /api/provinces
```


#### Get All municipalities from a single province with the provinceCode

```http
  GET /api/provinces/${provinceCode}/municipalities
```

| Parameter      | Type     | Description                                   |
|:---------------| :------- |:----------------------------------------------|
| `provinceCode` | `string` | **Required**. Id of item to fetch             |

#### Get All municipalities from a single province with the provinceCode

```http
  GET /api/municipalities/${municipalityCode}
```

| Parameter             | Type     | Description                       |
|:----------------------| :------- | :-------------------------------- |
| `municipalityCode`    | `string` | **Required**. Id of item to fetch |


Dependencies
This service relies on Apache POI to read Excel files, specifically the HSSFWorkbook and HSSFSheet classes for handling HSSF (Horrible Spreadsheet Format) - the file format used by Excel 97-2003. Ensure you include Apache POI in your project's dependencies.


## Deployment with docker

To deploy this project run 

How to Deploy with docker :
1) use mvn clean instal for generate the .jar
2) use  docker build -t italian-enum:ita-enum for build the images
3) use  docker run  -p 8080:8080  --name enum-provider italian-enum:ita-enum for run the container


```bash
  mvn clean install
  docker build -t italian-enum:ita-enum
  docker run  -p 8080:8080  --name enum-provider italian-enum:ita-enum
```


## Authors

- [@alejandro99billi](https://github.com/alejandro99billi)

## Appendix

For make this readme or antother like this you can use this tool : https://readme.so/