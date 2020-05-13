package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getCsvIterator(Reader reader, Class csvClass) throws CensusAnalyserException;
}
