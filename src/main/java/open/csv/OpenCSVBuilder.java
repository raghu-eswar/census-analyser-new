package open.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder{
    @Override
    public Iterator<E> getCsvIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = getCsvToBeanBuilder(reader, csvClass);
            return csvToBean.iterator();
        } catch (IllegalStateException ie) {
            throw new CSVBuilderException(ie.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
        catch (RuntimeException re) {
            throw new CSVBuilderException(re.getMessage(),
                    CSVBuilderException.ExceptionType.PROBLEM_IN_FIELDS);
        }
    }

    @Override
    public List<E> getCsvDataToList(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = getCsvToBeanBuilder(reader, csvClass);
            return csvToBean.parse();
        } catch (IllegalStateException ie) {
            throw new CSVBuilderException(ie.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException re) {
            throw new CSVBuilderException(re.getMessage(),
                    CSVBuilderException.ExceptionType.PROBLEM_IN_FIELDS);
        }
    }

    private CsvToBean<E> getCsvToBeanBuilder(Reader reader,Class<E> csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        return csvToBeanBuilder.build();
    }

}
