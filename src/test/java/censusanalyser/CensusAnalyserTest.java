package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FIELDS_CENSUS_CSV_FILE_PATH = "./src/test/resources/WrongStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/WrongStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFields_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FIELDS_CENSUS_CSV_FILE_PATH, WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.PROBLEM_IN_FIELDS, e.type);
        }
    }

    @Test
    public void withoutGivingIndianCensusCSVFile_getIndianCensusData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, IndiaCensusDAO> indianCensusData = censusAnalyser.getIndianCensusData();
            Assert.assertEquals(29,indianCensusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsMapWithCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDAO> indianCensusData = censusAnalyser.getIndianCensusData();
            Assert.assertEquals(29,indianCensusData.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsSortedStateWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDTO> indianCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusDTO[]sortedData = indianCensusData.values().toArray(new IndiaCensusDTO[0]);
            Assert.assertEquals("Andhra Pradesh",sortedData[0].getState());
            Assert.assertEquals("West Bengal",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsPopulationWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDTO> indianCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusDTO[]sortedData = indianCensusData.values().toArray(new IndiaCensusDTO[0]);
            Assert.assertEquals("Sikkim",sortedData[0].getState());
            Assert.assertEquals("Uttar Pradesh",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsAreaWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDTO> indianCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusDTO []sortedData = indianCensusData.values().toArray(new IndiaCensusDTO[0]);
            Assert.assertEquals("Goa",sortedData[0].getState());
            Assert.assertEquals("Rajasthan",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsDensityWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDTO> indianCensusData = censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusDTO []sortedData = indianCensusData.values().toArray(new IndiaCensusDTO[0]);
            Assert.assertEquals("Arunachal Pradesh",sortedData[0].getState());
            Assert.assertEquals("Bihar",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFileReturnsStateCodeWiseSortedCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Map<String, IndiaCensusDTO> indianCensusData = censusAnalyser.getStateCodeWiseSortedCensusData();
            IndiaCensusDTO []sortedData = indianCensusData.values().toArray(new IndiaCensusDTO[0]);
            Assert.assertEquals("Andhra Pradesh",sortedData[0].getState());
            Assert.assertEquals("West Bengal",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFields_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.PROBLEM_IN_FIELDS,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsMapWithCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, IndiaStateCodeCSV> stateCodeData = censusAnalyser.getIndianStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,stateCodeData.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsStateWiseSortedStateCodeData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, IndiaStateCodeCSV> stateCodeData = censusAnalyser.getStateWiseSortedStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV [] sortedData = stateCodeData.values().toArray(new IndiaStateCodeCSV[0]);
            Assert.assertEquals("Andaman and Nicobar Islands",sortedData[0].getState());
            Assert.assertEquals("West Bengal",sortedData[sortedData.length-1].getState());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}
