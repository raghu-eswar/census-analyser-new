package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV implements CensusCSV {
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

    @Override
    public String getStateCode() {
        return stateCode;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public double getTotalArea() {
        return totalArea;
    }

    @Override
    public double getPopulationDensity() {
        return populationDensity;
    }

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "stateCode='" + stateCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }

}
