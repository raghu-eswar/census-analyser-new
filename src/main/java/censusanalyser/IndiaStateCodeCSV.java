package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    private String stateName;
    @CsvBindByName(column = "StateCode", required = true)
    private String stateCode;

    public String getStateName() {
        return stateName;
    }

    public String getStateCode() {
        return stateCode;
    }
}
