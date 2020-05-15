package censusanalyser;

public class CensusDTO {
    private String stateName;
    private String stateCode;
    private int population;
    private double totalArea;
    private double populationDensity;

    public CensusDTO(CensusDAO censusDAO) {
        this.stateName = censusDAO.getStateName();
        this.stateCode = censusDAO.getStateCode();
        this.population = censusDAO.getPopulation();
        this.totalArea = censusDAO.getTotalArea();
        this.populationDensity = censusDAO.getPopulationDensity();
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
        return "CensusDTO{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
