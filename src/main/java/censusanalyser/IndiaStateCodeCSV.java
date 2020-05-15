package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV implements StateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    private String stateName;
    @CsvBindByName(column = "StateCode", required = true)
    private String stateCode;

    @Override
    public String getStateName() {
        return stateName;
    }

    @Override
    public String getStateCode() {
        return stateCode;
    }

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }

}
