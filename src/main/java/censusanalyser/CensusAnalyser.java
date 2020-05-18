package censusanalyser;

import com.google.gson.Gson;

import java.util.*;

public class CensusAnalyser {

    Map<String, CensusDAO> censusDataMap;
    public CensusAnalyser() { }

    public int loadCensusData(String country, String ... censusCsvFilePath) throws CensusAnalyserException, IllegalAccessException {
        this.censusDataMap = new CensusDAOBuilder().loadCensusData(country, censusCsvFilePath);
        return this.censusDataMap.size();
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

    public List<String> getCensusDataToJSONList() throws CensusAnalyserException {
        throwNoDataException();
        return toJSONList(buildCensusDTO(censusDataMap).values());
    }

    private List<String> toJSONList(Collection<CensusDTO> values) {
        List<String> jsonList = new ArrayList<>();
        values.forEach(censusDTO -> jsonList.add(new Gson().toJson(censusDTO)));
        return jsonList;
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
        boolean status = false;
        for (CensusDAO censusDAO : censusDataMap.values()) {
            if (censusDAO.getStateCode() == null) {
                status = true;
                break;
            }
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
