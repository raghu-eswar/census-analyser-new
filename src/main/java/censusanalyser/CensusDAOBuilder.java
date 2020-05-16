package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CensusDAOBuilder {
    Map<String, CensusDAO> censusDataMap = new HashMap<>();

    private enum FieldClassesList{
        stateCode(IndiaStateCodeCSV.class);
        private final Class _class;
        FieldClassesList(Class _class) {
            this._class = _class;
        }
    }

    private enum CountryCsvClasses {
        INDIA(IndiaCensusCSV.class), US(USCensusCSV.class);
        private final Class<? extends CensusCSV> _class;
        CountryCsvClasses(Class<? extends CensusCSV> _class) {
            this._class = _class;
        }
    }

    public Map<String, CensusDAO> loadCensusData(String country, String... censusCsvFilePath) throws CensusAnalyserException, IllegalAccessException, NoSuchFieldException {
        Class<? extends CensusCSV> countryClass = CountryCsvClasses.valueOf(country)._class;
        return loadCensusData(countryClass,censusCsvFilePath);
    }

    private   <T extends CensusCSV> Map<String, CensusDAO> loadCensusData(Class<T> csvClass, String... censusCsvFilePath) throws CensusAnalyserException, IllegalAccessException, NoSuchFieldException {
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath[0]))) {
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
        loadEmptyFields(censusCsvFilePath);
        return censusDataMap;
    }

    private void loadEmptyFields(String[] censusCsvFilePath) throws CensusAnalyserException, IllegalAccessException, NoSuchFieldException {
        String emptyField = getEmptyFields();
        String newEmptyField = emptyField;
        if (emptyField != null) {
            for (String path : censusCsvFilePath) {
                try {
                    loadEmptyFieldData(emptyField, path);
                    newEmptyField = getEmptyFields();
                    if (!emptyField.equals(newEmptyField)) {
                        break;
                    }
                } catch (CensusAnalyserException e){
                    if (!(e.type.toString().equals("PROBLEM_IN_FIELDS")))
                        throw e;
                }
            }
            if (emptyField.equals(newEmptyField) ) {
                throw new CensusAnalyserException("given files having empty fields", CensusAnalyserException.ExceptionType.EMPTY_FIELDS);
            }
            loadEmptyFields(censusCsvFilePath);
        }
    }

    private String getEmptyFields() throws IllegalAccessException {
        for (CensusDAO censusDAO: censusDataMap.values()) {
            Field[] fields = censusDAO.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(censusDAO) == null) {
                    return field.getName();
                }
            }
        }
        return null;
    }

    private void loadEmptyFieldData(String fieldName, String path) throws CensusAnalyserException, NoSuchFieldException, IllegalAccessException {
        Class fieldClass = FieldClassesList.valueOf(fieldName)._class;
        loadEmptyFieldData(path, fieldClass,fieldName);
    }

    private  <T> void loadEmptyFieldData(String csvFilePath, Class<T> stateCodeClass, String field) throws CensusAnalyserException, NoSuchFieldException, IllegalAccessException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
            Iterator<T> csvIterator = csvBuilder.getCsvIterator(reader, stateCodeClass);
            while (csvIterator.hasNext()) {
                T fieldCSV = csvIterator.next();
                Field csvField = fieldCSV.getClass().getDeclaredField(field);
                csvField.setAccessible(true);
                String emptyFieldData = (String) csvField.get(fieldCSV);
                Field stateName = fieldCSV.getClass().getDeclaredField("stateName");
                stateName.setAccessible(true);
                CensusDAO censusDAO = censusDataMap.get(stateName.get(fieldCSV));
                if (censusDAO != null) {
                    Field emptyField = censusDAO.getClass().getDeclaredField(field);
                    emptyField.setAccessible(true);
                    emptyField.set(censusDAO,emptyFieldData);
                }
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

}











