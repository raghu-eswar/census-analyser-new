package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV implements CensusCSV {

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

    @Override
    public String getStateName() {
        return stateName;
    }

    @Override
    public String getStateCode() {
        return stateCode;
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
        return "IndiaCensusCSV{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }

}
