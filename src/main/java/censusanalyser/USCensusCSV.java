package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    private String stateCode;

    @CsvBindByName(column = "State", required = true)
    private String stateName;

    @CsvBindByName(column = "Population", required = true)
    private int population;

    @CsvBindByName(column = "Total area", required = true)
    private double totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    private double populationDensity;

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
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
