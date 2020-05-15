package censusanalyser;

public class CensusDAO {
    private String stateName;
    private String stateCode;
    private int population;
    private double totalArea;
    private double populationDensity;

    public CensusDAO(IndiaCensusCSV censusCSV) {
        this.stateName = censusCSV.getStateName();
        this.stateCode = censusCSV.getStateCode();
        this.population = censusCSV.getPopulation();
        this.totalArea = censusCSV.getTotalArea();
        this.populationDensity = censusCSV.getPopulationDensity();
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        this.stateName = usCensusCSV.getStateName();
        this.stateCode = usCensusCSV.getStateCode();
        this.population = usCensusCSV.getPopulation();
        this.totalArea = usCensusCSV.getTotalArea();
        this.populationDensity = usCensusCSV.getPopulationDensity();
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(double populationDensity) {
        this.populationDensity = populationDensity;
    }

    @Override
    public String toString() {
        return "CensusDAO{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }

}










