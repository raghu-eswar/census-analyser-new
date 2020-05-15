package censusanalyser;

public class StateCodeDTO {

    private String stateName;
    private String stateCode;

    public StateCodeDTO(CensusDAO censusDAO) {
        this.stateName = censusDAO.getStateName();
        this.stateCode = censusDAO.getStateCode();
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

    @Override
    public String toString() {
        return "StateCodeDTO{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }

}
