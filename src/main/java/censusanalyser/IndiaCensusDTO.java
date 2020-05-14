package censusanalyser;

public class IndiaCensusDTO {
    private String state;
    private String stateCode;
    private int population;
    private int areaInSqKm;
    private int densityPerSqKm;

    public IndiaCensusDTO(IndiaCensusDAO indiaCensusDAO) {
        this.state = indiaCensusDAO.getState();
        this.stateCode = indiaCensusDAO.getStateCode();
        this.population = indiaCensusDAO.getPopulation();
        this.areaInSqKm = indiaCensusDAO.getAreaInSqKm();
        this.densityPerSqKm = indiaCensusDAO.getDensityPerSqKm();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(int areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(int densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    @Override
    public String toString() {
        return "IndiaCensusDTO{" +
                "state='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                '}';
    }
}
