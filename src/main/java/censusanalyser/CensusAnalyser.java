package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String,IndiaCensusCSV> censusCSVMap = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaCensusCSV> censusCSVList = loadCSVDataToList(csvFilePath, IndiaCensusCSV.class);
        censusCSVMap = new HashMap<>();
        for (IndiaCensusCSV censusCSV : censusCSVList) {
            censusCSVMap.put(censusCSV.getState(),censusCSV);
        }
        return censusCSVList.size();
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

    public Map<String, IndiaCensusCSV> getIndianCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        return this.censusCSVMap;
    }

    public Map<String, IndiaStateCodeCSV> getIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaStateCodeCSV> stateCodeCSVList = loadCSVDataToList(csvFilePath, IndiaStateCodeCSV.class);
        Map<String,IndiaStateCodeCSV> stateCodeCSVMap = new HashMap<>();
        for (IndiaStateCodeCSV censusCSV : stateCodeCSVList) {
            stateCodeCSVMap.put(censusCSV.getState(),censusCSV);
        }
        return stateCodeCSVMap;
    }

    public Map<String, IndiaCensusCSV> getStateWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getState);
        return sort(censusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getPopulation);
        return sort(censusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getAreaWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getAreaInSqKm);
        return sort(censusCSVMap,censusCSVComparator);
    }

    public Map<String, IndiaCensusCSV> getDensityWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaCensusCSV::getDensityPerSqKm);
        return sort(censusCSVMap,censusCSVComparator);
    }

    private void throwNoCensusDataException() throws CensusAnalyserException {
        if (censusCSVMap == null || censusCSVMap.size() == 0 )
            throw new CensusAnalyserException("no census data loaded ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
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
