package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FIELDS_CENSUS_CSV_FILE_PATH = "./src/test/resources/WrongStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/WrongStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFields_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",WRONG_FIELDS_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.PROBLEM_IN_FIELDS, e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withoutLoadingIndianCensusCSVFile_getIndianCensusData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.getCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void withoutLoadingIndiaStateCodeCSVFile_loadCensusData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA", INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.getCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.EMPTY_FIELDS, e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusAndStateCodeCSVFileReturnsMapWithCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getCensusData();
            Assert.assertEquals(29,indianCensusData.size());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusAndStareCodeCSVFileReturnsSortedStateWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CensusDTO[]sortedData = indianCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Andhra Pradesh",sortedData[0].getStateName());
            Assert.assertEquals("West Bengal",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusAndStateCodeCSVFileReturnsPopulationWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            CensusDTO[]sortedData = indianCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Sikkim",sortedData[0].getStateName());
            Assert.assertEquals("Uttar Pradesh",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusAndStateCodeCSVFileReturnsAreaWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            CensusDTO[]sortedData = indianCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Goa",sortedData[0].getStateName());
            Assert.assertEquals("Rajasthan",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusAndStateCodeCSVFileReturnsDensityWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getDensityWiseSortedCensusData();
            CensusDTO[]sortedData = indianCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Arunachal Pradesh",sortedData[0].getStateName());
            Assert.assertEquals("Bihar",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusAndStateCodeCSVFileReturnsStateCodeWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, CensusDTO> indianCensusData = censusAnalyser.getStateCodeWiseSortedCensusData();
            CensusDTO[]sortedData = indianCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Andhra Pradesh",sortedData[0].getStateName());
            Assert.assertEquals("West Bengal",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsJsonList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            List<String> jsonStrings = censusAnalyser.getCensusDataToJSONList();
            Assert.assertEquals(29,jsonStrings.size());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFields_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH, WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.EMPTY_FIELDS,e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectStateCodeData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("INDIA",INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, StateCodeDTO> stateCodeData = censusAnalyser.getStateCodeData();
            Assert.assertEquals(29, stateCodeData.size());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> censusData = censusAnalyser.getCensusData();
            Assert.assertEquals(51,censusData.size());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US",WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WithWrongFields_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US",WRONG_FIELDS_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.PROBLEM_IN_FIELDS, e.type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withoutLoadingUSCensusCSVFile_getCensusData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.getCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsMapWithCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getCensusData();
            Assert.assertEquals(51,usCensusData.size());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsSortedStateWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CensusDTO[]sortedData = usCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Alabama",sortedData[0].getStateName());
            Assert.assertEquals("Wyoming",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsPopulationWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            CensusDTO[]sortedData = usCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Wyoming",sortedData[0].getStateName());
            Assert.assertEquals("California",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsAreaWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            CensusDTO[]sortedData = usCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("District of Columbia",sortedData[0].getStateName());
            Assert.assertEquals("Alaska",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsDensityWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getDensityWiseSortedCensusData();
            CensusDTO[]sortedData = usCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Alaska",sortedData[0].getStateName());
            Assert.assertEquals("District of Columbia",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsStateCodeWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData("US", US_CENSUS_CSV_FILE_PATH);
            Map<String, CensusDTO> usCensusData = censusAnalyser.getStateCodeWiseSortedCensusData();
            CensusDTO[]sortedData = usCensusData.values().toArray(new CensusDTO[0]);
            Assert.assertEquals("Alaska",sortedData[0].getStateName());
            Assert.assertEquals("Wyoming",sortedData[sortedData.length-1].getStateName());
        } catch (CensusAnalyserException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
