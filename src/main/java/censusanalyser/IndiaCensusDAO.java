package censusanalyser;

import java.util.Iterator;

public class IndiaCensusDAO {
    private String state;
    private String stateCode;
    private int population;
    private int areaInSqKm;
    private int densityPerSqKm;

    public IndiaCensusDAO(String state, String stateCode) {
        this.state = state;
        this.stateCode = stateCode;
    }

    public static IndiaCensusDAO getInstance(IndiaStateCodeCSV stateCodeCSV, Iterator<IndiaCensusCSV> censusIterator) {

        IndiaCensusDAO indiaCensusDAO = new IndiaCensusDAO(stateCodeCSV.getState(), stateCodeCSV.getStateCode());
        if (indiaCensusDAO.assignCensusData(indiaCensusDAO.state, censusIterator))
            return indiaCensusDAO;
        return null;
    }

    private boolean assignCensusData(String state, Iterator<IndiaCensusCSV> censusIterator) {
        while (censusIterator.hasNext()) {
            IndiaCensusCSV censusCSV = censusIterator.next();
            if (state.equalsIgnoreCase(censusCSV.getState())) {
                this.areaInSqKm = censusCSV.getAreaInSqKm();
                this.densityPerSqKm = censusCSV.getDensityPerSqKm();
                this.population = censusCSV.getPopulation();
                return true;
            }
        }
        return false;
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

//    public void lodeStateCensusDAO() throws IOException, CSVBuilderException {
//        Reader censusReader = Files.newBufferedReader(Paths.get("./src/test/resources/IndiaStateCensusData.csv"));
//        Reader stateCodeReader = Files.newBufferedReader(Paths.get("./src/test/resources/IndiaStateCode.csv"));
//        ICSVBuilder csvBuilder = CSVBuilderFactory.getCsvBuilder();
//        Iterator<IndiaCensusCSV> censusIterator = csvBuilder.getCsvIterator(censusReader, IndiaCensusCSV.class);
//        Iterator<IndiaStateCodeCSV> stateCodeIterator = csvBuilder.getCsvIterator(stateCodeReader, IndiaStateCodeCSV.class);
//        Map<String, IndiaCensusDAO> censusDAOMap = new HashMap<>();
//        while (stateCodeIterator.hasNext()) {
//            IndiaCensusDAO censusDAO = new IndiaCensusDAO();
//            IndiaStateCodeCSV stateCodeCSV = stateCodeIterator.next();
//            censusDAO.setState(stateCodeCSV.getState());
//            censusDAO.setStateCode(stateCodeCSV.getStateCode());
//            while (censusIterator.hasNext()) {
//                IndiaCensusCSV censusCSV = censusIterator.next();
//                if (censusDAO.getState().equals(censusCSV.getState())){
//                    censusDAO.setPopulation(censusCSV.getPopulation());
//                    censusDAO.setAreaInSqKm(censusCSV.getAreaInSqKm());
//                    censusDAO.setDensityPerSqKm(censusCSV.getDensityPerSqKm());
//                    censusReader.close();
//                    break;
//                }
//            }
//            censusReader = Files.newBufferedReader(Paths.get("./src/test/resources/IndiaStateCensusData.csv"));
//            censusIterator = csvBuilder.getCsvIterator(censusReader, IndiaCensusCSV.class);
//            censusDAOMap.put(censusDAO.getState(), censusDAO);
//        }
//        System.out.println(censusDAOMap);
//    }
//
//    public static void main(String[] args) throws IOException {
//        try {
//            new IndiaCensusDAO().lodeStateCensusDAO();
//        } catch (CSVBuilderException e) {
//            System.out.println(e.getCause());
//            e.printStackTrace();
//        }
//    }

    @Override
    public String toString() {
        return "IndiaCensusDAO{" +
                "state='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                '}';
    }
}










