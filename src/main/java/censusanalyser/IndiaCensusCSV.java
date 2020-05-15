package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    @CsvBindByName(column = "State", required = true)
    private String stateName;

    @CsvBindByName(column = "StateCode")
    private String stateCode;

    @CsvBindByName(column = "Population", required = true)
    private int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    private double totalArea;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    private double populationDensity;

    public String getStateName() {
        return stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public int getPopulation() {
        return population;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }
}
