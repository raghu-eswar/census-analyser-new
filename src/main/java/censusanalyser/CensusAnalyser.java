package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CensusAnalyser {

    Map<String, CensusDAO> censusDataMap;
    public CensusAnalyser() {
        //this.censusDataMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        this.censusDataMap = new CensusDAOBuilder().loadCensusData(censusCsvFilePath, IndiaCensusCSV.class);
        return this.censusDataMap.size();
    }

    public int loadUSCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        this.censusDataMap = new CensusDAOBuilder().loadCensusData(censusCsvFilePath, USCensusCSV.class);
        return this.censusDataMap.size();
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        loadStateCodeData(csvFilePath, IndiaStateCodeCSV.class);
        return censusDataMap.size();
    }

    private  <T extends StateCodeCSV> void loadStateCodeData(String csvFilePath, Class<T> stateCodeClass) throws CensusAnalyserException {
        throwNoCensusDataException();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<T> csvIterator = csvBuilder.getCsvIterator(reader, stateCodeClass);
            while (csvIterator.hasNext()) {
                T stateCodeCSV = csvIterator.next();
                CensusDAO censusDAO = censusDataMap.get(stateCodeCSV.getStateName());
                if (censusDAO != null)  censusDAO.setStateCode(stateCodeCSV.getStateCode());
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private List<CensusDTO> loadCensusDataToList() throws CensusAnalyserException {
        throwNoDataException();
        return new ArrayList<>(buildCensusDTO(censusDataMap).values());
    }

    public Map<String, CensusDTO> getCensusData() throws CensusAnalyserException {
        throwNoDataException();
        return buildCensusDTO(this.censusDataMap);
    }

    public Map<String, StateCodeDTO> getStateCodeData() throws CensusAnalyserException {
        throwNoDataException();
        return buildStateCodeDTO(censusDataMap);
    }

    public Map<String, CensusDTO> getStateWiseSortedCensusData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getStateName);
        return buildCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getPopulation);
        return buildCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getAreaWiseSortedCensusData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getTotalArea);
        return buildCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getDensityWiseSortedCensusData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getPopulationDensity);
        return buildCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getStateCode);
        return buildCensusDTO(sort(censusDataMap,censusCSVComparator));
    }

    public Map<String, StateCodeDTO> getStateWiseSortedStateCodeData() throws CensusAnalyserException {
        throwNoDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getStateCode);
        return buildStateCodeDTO(sort(censusDataMap,censusCSVComparator));
    }


    private void throwNoDataException() throws CensusAnalyserException {
        throwNoCensusDataException();
        throwNoStateCodeDataException();
    }

    private void throwNoCensusDataException() throws CensusAnalyserException {
        if (censusDataMap == null || censusDataMap.size() == 0 )
            throw new CensusAnalyserException("no census data loaded ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
    }

    private void throwNoStateCodeDataException() throws CensusAnalyserException {
        boolean status = true;
        for (CensusDAO censusDAO : censusDataMap.values()) {
            if (censusDAO.getStateCode() != null) status = false;
        }
        if (status)
            throw new CensusAnalyserException("no state code data loaded ",CensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
    }

    private Map<String, CensusDTO> buildCensusDTO(Map<String, CensusDAO> daoMap) {
        Map<String, CensusDTO> censusDTOMap = new LinkedHashMap<>();
        for (String key : daoMap.keySet()) {
            censusDTOMap.put(key, new CensusDTO(daoMap.get(key)));
        }
        return censusDTOMap;
    }

    private Map<String, StateCodeDTO> buildStateCodeDTO(Map<String, CensusDAO> daoMap) {
        Map<String, StateCodeDTO> stateCodeDTOMap = new LinkedHashMap<>();
        for (String key : daoMap.keySet()) {
            stateCodeDTOMap.put(key, new StateCodeDTO(daoMap.get(key)));
        }
        return stateCodeDTOMap;
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
