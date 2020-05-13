package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder {
    public <E> Iterator<E> getCsvIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
        try  {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();;
            return csvToBean.iterator();
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
