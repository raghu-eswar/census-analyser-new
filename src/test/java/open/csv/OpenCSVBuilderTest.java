package open.csv;

import censusanalyser.*;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class OpenCSVBuilderTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FIELDS_CENSUS_CSV_FILE_PATH = "./src/test/resources/WrongStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/WrongStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";

    @Test
    public void givenIndianCensusCSVFile_getCsvIterator_ReturnsIteratorWithCorrectRecords() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(INDIA_CENSUS_CSV_FILE_PATH));
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<CensusCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaCensusCSV.class);
            Iterable<CensusCSV> csvIterable = () -> csvIterator;
            long count = StreamSupport.stream(csvIterable.spliterator(), false).count();
            assertEquals(29, count);
        } catch (IOException | CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCensusCSVFile_getCsvIterator_ReturnsIteratorWithCorrectRecords() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(INDIA_STATE_CODE_CSV_FILE_PATH));
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<StateCodeCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaStateCodeCSV.class);
            Iterable<StateCodeCSV> csvIterable = () -> csvIterator;
            long count = StreamSupport.stream(csvIterable.spliterator(), false).count();
            assertEquals(37, count);
        } catch (IOException | CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(WRONG_CSV_FILE_PATH));
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            csvBuilder.getCsvIterator(reader, IndiaCensusCSV.class);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            assertEquals(NoSuchFileException.class, e.getClass());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFields_ShouldThrowException() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(WRONG_FIELDS_CENSUS_CSV_FILE_PATH));
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            csvBuilder.getCsvIterator(reader, IndiaCensusCSV.class);
        } catch (CSVBuilderException e) {
            assertEquals("PROBLEM_IN_FIELDS", e.type.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeCensusData_WithWrongFields_ShouldThrowException() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(WRONG_FIELDS_STATE_CODE_CSV_FILE_PATH));
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            csvBuilder.getCsvIterator(reader, IndiaStateCodeCSV.class);
        } catch (CSVBuilderException e) {
            assertEquals("PROBLEM_IN_FIELDS", e.type.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
