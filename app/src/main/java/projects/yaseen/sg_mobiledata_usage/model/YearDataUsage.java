package projects.yaseen.sg_mobiledata_usage.model;

import java.util.List;

public class YearDataUsage {

    private int Year;

    private QuarterUsage dataQ1;

    private QuarterUsage dataQ2;

    private QuarterUsage dataQ3;

    private QuarterUsage dataQ4;


    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public QuarterUsage getDataQ1() {
        return dataQ1;
    }

    public void setDataQ1(QuarterUsage dataQ1) {
        this.dataQ1 = dataQ1;
    }

    public QuarterUsage getDataQ2() {
        return dataQ2;
    }

    public void setDataQ2(QuarterUsage dataQ2) {
        this.dataQ2 = dataQ2;
    }

    public QuarterUsage getDataQ3() {
        return dataQ3;
    }

    public void setDataQ3(QuarterUsage dataQ3) {
        this.dataQ3 = dataQ3;
    }

    public QuarterUsage getDataQ4() {
        return dataQ4;
    }

    public void setDataQ4(QuarterUsage dataQ4) {
        this.dataQ4 = dataQ4;
    }


    public void setDataForQuarter(String quarter,QuarterUsage quarterUsage){
        if (quarter.equals("Q1")){
            setDataQ1(quarterUsage);
        }else if (quarter.equals("Q2")){
            setDataQ2(quarterUsage);
        }else if (quarter.equals("Q3")){
            setDataQ3(quarterUsage);
        }else if (quarter.equals("Q4")){
            setDataQ4(quarterUsage);
        }
    }



}
