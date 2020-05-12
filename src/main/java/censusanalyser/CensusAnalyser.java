package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        int namOfEateries = loadCSVData(csvFilePath, IndiaCensusCSV.class);
        return namOfEateries;
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        int namOfEateries = loadCSVData(csvFilePath, IndiaStateCodeCSV.class);
        return namOfEateries;
    }

    private <T> int loadCSVData(String csvFilePath, Class<T> csvClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();;
            Iterator<T> csvIterator = csvToBean.iterator();
            Iterable<T> csvIterable = () -> csvIterator;
            int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch(IllegalStateException ie){
            throw new CensusAnalyserException(ie.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
        catch (RuntimeException re) {
            throw new CensusAnalyserException(re.getMessage(),
                    CensusAnalyserException.ExceptionType.PROBLEM_IN_FIELDS);
        }
    }

}
