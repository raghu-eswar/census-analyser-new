package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CensusAnalyser {

    Map<String, CensusDAO> indiaCensusDataMap;
    public CensusAnalyser() {
        this.indiaCensusDataMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaCensusCSV> censusIterator = csvBuilder.getCsvIterator(reader, IndiaCensusCSV.class);
            while (censusIterator.hasNext()) {
                CensusDAO censusDAO = new CensusDAO(censusIterator.next());
                indiaCensusDataMap.put(censusDAO.getStateName(), censusDAO);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

        return this.indiaCensusDataMap.size();
    }

    public int loadUSCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<USCensusCSV> usIterator = csvBuilder.getCsvIterator(reader, USCensusCSV.class);
            while (usIterator.hasNext()) {
                CensusDAO censusDAO = new CensusDAO(usIterator.next());
                indiaCensusDataMap.put(censusDAO.getStateName(), censusDAO);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

        return this.indiaCensusDataMap.size();
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        throwNoCensusDataException();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> csvIterator = csvBuilder.getCsvIterator(reader, IndiaStateCodeCSV.class);
            while (csvIterator.hasNext()) {
                IndiaStateCodeCSV stateCodeCSV = csvIterator.next();
                CensusDAO censusDAO = indiaCensusDataMap.get(stateCodeCSV.getStateName());
                if (censusDAO != null)  censusDAO.setStateCode(stateCodeCSV.getStateCode());
            }
            return indiaCensusDataMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
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

    public Map<String, CensusDTO> getIndianCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        return getCensusDTO(this.indiaCensusDataMap);
    }

    public Map<String, IndiaStateCodeCSV> getIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        List<IndiaStateCodeCSV> stateCodeCSVList = loadCSVDataToList(csvFilePath, IndiaStateCodeCSV.class);
        Map<String,IndiaStateCodeCSV> stateCodeCSVMap = new HashMap<>();
        for (IndiaStateCodeCSV censusCSV : stateCodeCSVList) {
            stateCodeCSVMap.put(censusCSV.getStateName(),censusCSV);
        }
        return stateCodeCSVMap;
    }

    public Map<String, CensusDTO> getStateWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getStateName);
        return getCensusDTO(sort(indiaCensusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getPopulation);
        return getCensusDTO(sort(indiaCensusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getAreaWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getTotalArea);
        return getCensusDTO(sort(indiaCensusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getDensityWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getPopulationDensity);
        return getCensusDTO(sort(indiaCensusDataMap,censusCSVComparator));
    }

    public Map<String, CensusDTO> getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        throwNoCensusDataException();
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(CensusDAO::getStateCode);
        return getCensusDTO(sort(indiaCensusDataMap,censusCSVComparator));
    }

    private void throwNoCensusDataException() throws CensusAnalyserException {
        if (indiaCensusDataMap == null || indiaCensusDataMap.size() == 0 )
            throw new CensusAnalyserException("no census data loaded ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
    }

    public Map<String, IndiaStateCodeCSV> getStateWiseSortedStateCodeData(String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaStateCodeCSV> indiaStateCodeCSVMap = getIndianStateCodeData(csvFilePath);
        Comparator<IndiaStateCodeCSV> censusCSVComparator = Comparator.comparing(IndiaStateCodeCSV::getStateName);
        return sort(indiaStateCodeCSVMap,censusCSVComparator);
    }

    private Map<String, CensusDTO> getCensusDTO(Map<String, CensusDAO> daoMap) {
        Map<String, CensusDTO> indiaCensusDTOMap = new LinkedHashMap<>();
        for (String key : daoMap.keySet()) {
            indiaCensusDTOMap.put(key, new CensusDTO(daoMap.get(key)));
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
