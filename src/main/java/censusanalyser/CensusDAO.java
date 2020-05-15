package censusanalyser;

public class CensusDAO {
    private String stateName;
    private String stateCode;
    private int population;
    private double totalArea;
    private double populationDensity;

    public <T extends CensusCSV> CensusDAO(T censusCsv) {
        this.stateName = censusCsv.getStateName();
        this.stateCode = censusCsv.getStateCode();
        this.population = censusCsv.getPopulation();
        this.totalArea = censusCsv.getTotalArea();
        this.populationDensity = censusCsv.getPopulationDensity();
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










