package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<E> implements ICSVBuilder{
    public Iterator<E> getCsvIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try  {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();;
            return csvToBean.iterator();
        }
        catch (IllegalStateException ie) {
            throw new CSVBuilderException(ie.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException re) {
            throw new CSVBuilderException(re.getMessage(),
                    CSVBuilderException.ExceptionType.PROBLEM_IN_FIELDS);
        }
    }
}
