package priv.zxy.moonstep.framework.race;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:种族基类
 **/
public class Race {

    private String racePathMan;
    private String racePathWoMan;
    private String raceDescription;
    private String raceName;
    private int racePossibility;
    private String raceIcon;
    private int raceCode;

    public String getRacePathMan() {
        return racePathMan;
    }

    public void setRacePathMan(String racePathMan) {
        this.racePathMan = racePathMan;
    }

    public String getRacePathWoMan() {
        return racePathWoMan;
    }

    public void setRacePathWoMan(String racePathWoMan) {
        this.racePathWoMan = racePathWoMan;
    }

    public String getRaceDescription() {
        return raceDescription;
    }

    public void setRaceDescription(String raceDescription) {
        this.raceDescription = raceDescription;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public int getRacePossibility() {
        return racePossibility;
    }

    public void setRacePossibility(int racePossibility) {
        this.racePossibility = racePossibility;
    }

    public String getRaceIcon() {
        return raceIcon;
    }

    public void setRaceIcon(String raceIcon) {
        this.raceIcon = raceIcon;
    }

    public int getRaceCode() {
        return raceCode;
    }

    public void setRaceCode(int raceCode) {
        this.raceCode = raceCode;
    }
}
