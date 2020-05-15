package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {

    <E> Iterator<E> getCsvIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    <E> List<E> getCsvDataToList(Reader reader, Class csvClass) throws CSVBuilderException;

}
