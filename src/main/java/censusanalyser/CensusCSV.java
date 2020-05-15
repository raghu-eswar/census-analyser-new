package censusanalyser;

public interface CensusCSV {

    String getStateCode();
    String getStateName();
    int getPopulation();
    double getTotalArea();
    double getPopulationDensity();

}
