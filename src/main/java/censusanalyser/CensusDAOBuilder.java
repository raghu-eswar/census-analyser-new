package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CensusDAOBuilder {

    public  <T extends CensusCSV> Map<String, CensusDAO> loadCensusData(String censusCsvFilePath, Class<T> csvClass) throws CensusAnalyserException {
        Map<String, CensusDAO> censusDataMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<T> csvIterator = csvBuilder.getCsvIterator(reader, csvClass);
            while (csvIterator.hasNext()) {
                CensusDAO censusDAO = new CensusDAO(csvIterator.next());
                censusDataMap.put(censusDAO.getStateName(), censusDAO);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
        return censusDataMap;
    }

}
