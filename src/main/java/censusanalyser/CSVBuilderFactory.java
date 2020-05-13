package censusanalyser;

public class CSVBuilderFactory {
    public static ICSVBuilder getCsvBuilder() {
        return new OpenCSVBuilder();
    }
}
