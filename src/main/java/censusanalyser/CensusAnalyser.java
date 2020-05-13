package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaCensusCSV.class);
            return getCount(csvIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaStateCodeCSV.class);
            return getCount(csvIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> csvIterator) {
        Iterable<E> csvIterable = () -> csvIterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }

    private <T> List<T> loadCSVDataToList(String csvFilePath, Class<T> csvClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            return csvBuilder.getCsvDataToList(reader, csvClass);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public Map<String, IndiaCensusCSV> getIndianCensusData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaCensusCSV> censusCSVList = loadCSVDataToList(csvFilePath, IndiaCensusCSV.class);
        Map<String,IndiaCensusCSV> censusCSVMap = new HashMap<>();
        for (IndiaCensusCSV censusCSV : censusCSVList) {
            censusCSVMap.put(censusCSV.getState(),censusCSV);
        }
        return censusCSVMap;
    }

    public Map<String, IndiaStateCodeCSV> getIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaStateCodeCSV> stateCodeCSVList = loadCSVDataToList(csvFilePath, IndiaStateCodeCSV.class);
        Map<String,IndiaStateCodeCSV> stateCodeCSVMap = new HashMap<>();
        for (IndiaStateCodeCSV censusCSV : stateCodeCSVList) {
            stateCodeCSVMap.put(censusCSV.getState(),censusCSV);
        }
        return stateCodeCSVMap;
    }

    public Map<String, IndiaCensusCSV> getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusCSV> indiaCensusCSVMap = getIndianCensusData(csvFilePath);
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getState);
        return sort(indiaCensusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusCSV> indiaCensusCSVMap = getIndianCensusData(csvFilePath);
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getPopulation);
        return sort(indiaCensusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusCSV> indiaCensusCSVMap = getIndianCensusData(csvFilePath);
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getAreaInSqKm);
        return sort(indiaCensusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusCSV> indiaCensusCSVMap = getIndianCensusData(csvFilePath);
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getDensityPerSqKm);
        return sort(indiaCensusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaStateCodeCSV> getStateWiseSortedStateCodeData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaStateCodeCSV> indiaStateCodeCSVMap = getIndianStateCodeData(csvFilePath);
        Comparator<IndiaStateCodeCSV> censusCSVComparator = Comparator.comparing(IndiaStateCodeCSV::getState);
        return sort(indiaStateCodeCSVMap,censusCSVComparator);
    }

    private <K,V> Map<K,V> sort(Map<K,V> map, Comparator<V> comparator) {
        Map<V, K> newMap = new TreeMap<>(comparator);
        for (K key : map.keySet()) {
            newMap.put(map.get(key), key);
        }
        Map<K, V> newSortedMap = new LinkedHashMap<>();
        for (V key : newMap.keySet()) {
            newSortedMap.put(newMap.get(key), key);
        }
        return newSortedMap;
    }

}
