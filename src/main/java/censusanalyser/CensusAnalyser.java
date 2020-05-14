package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, IndiaCensusDAO> censusDataMap;

    public CensusAnalyser() {
        this.censusDataMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String censusCsvFilePath, String stateCodeCsvFilePath) throws CensusAnalyserException {
        try (Reader stateCodeReader = Files.newBufferedReader(Paths.get(stateCodeCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeIterator = csvBuilder.getCsvIterator(stateCodeReader, IndiaStateCodeCSV.class);
            while (stateCodeIterator.hasNext()) {
                Reader censusReader = Files.newBufferedReader(Paths.get(censusCsvFilePath));
                Iterator<IndiaCensusCSV> censusIterator = csvBuilder.getCsvIterator(censusReader, IndiaCensusCSV.class);
                IndiaCensusDAO indiaCensusDAO = IndiaCensusDAO.getInstance(stateCodeIterator.next(), censusIterator);
                if (indiaCensusDAO != null)  censusDataMap.put(indiaCensusDAO.getState(), indiaCensusDAO);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

        return this.censusDataMap.size();
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaStateCodeCSV.class);
            return getCount(csvIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
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
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public Map<String, IndiaCensusDAO> getIndianCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        return this.censusDataMap;
    }

    public Map<String, IndiaStateCodeCSV> getIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaStateCodeCSV> stateCodeCSVList = loadCSVDataToList(csvFilePath, IndiaStateCodeCSV.class);
        Map<String,IndiaStateCodeCSV> stateCodeCSVMap = new HashMap<>();
        for (IndiaStateCodeCSV censusCSV : stateCodeCSVList) {
            stateCodeCSVMap.put(censusCSV.getState(),censusCSV);
        }
        return stateCodeCSVMap;
    }

    public Map<String, IndiaCensusDTO> getStateWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(IndiaCensusDAO::getState);
        return getCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, IndiaCensusDTO> getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(IndiaCensusDAO::getPopulation);
        return getCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, IndiaCensusDTO> getAreaWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(IndiaCensusDAO::getAreaInSqKm);
        return getCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, IndiaCensusDTO> getDensityWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(IndiaCensusDAO::getDensityPerSqKm);
        return getCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, IndiaCensusDTO> getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(IndiaCensusDAO::getStateCode);
        return getCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    private void throwNoCensusDataException() throws CensusAnalyserException {
        if (censusDataMap == null || censusDataMap.size() == 0 )
            throw new CensusAnalyserException("no census data loaded ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
    }

    public Map<String, IndiaStateCodeCSV> getStateWiseSortedStateCodeData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaStateCodeCSV> indiaStateCodeCSVMap = getIndianStateCodeData(csvFilePath);
        Comparator<IndiaStateCodeCSV> censusCSVComparator = Comparator.comparing(IndiaStateCodeCSV::getState);
        return sort(indiaStateCodeCSVMap,censusCSVComparator);
    }

    private Map<String, IndiaCensusDTO> getCensusDTO(Map<String, IndiaCensusDAO> daoMap) {
        Map<String, IndiaCensusDTO> indiaCensusDTOMap = new LinkedHashMap<>();
        for (String key : daoMap.keySet()) {
            indiaCensusDTOMap.put(key, new IndiaCensusDTO(daoMap.get(key)));
        }
        return indiaCensusDTOMap;
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
