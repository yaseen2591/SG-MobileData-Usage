package projects.yaseen.sg_mobiledata_usage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUsageResponse {
    @SerializedName("result")
    @Expose
    private Results data;

    public DataUsageResponse() {

    }


    public Results getData() {
        return data;
    }

    public void setData(Results data) {
        this.data = data;
    }

    public class Results {

        @SerializedName("records")
        @Expose
        private List<QuarterUsageRecord> recordList;

        public List<QuarterUsageRecord> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<QuarterUsageRecord> recordList) {
            this.recordList = recordList;
        }

        public class QuarterUsageRecord{
            @SerializedName("volume_of_mobile_data")
            @Expose
            private String dataVolume;

            @SerializedName("quarter")
            @Expose
            private String quarter;

            public String getDataVolume() {
                return dataVolume;
            }

            public void setDataVolume(String dataVolume) {
                this.dataVolume = dataVolume;
            }

            public String getQuarter() {
                return quarter;
            }

            public void setQuarter(String quarter) {
                this.quarter = quarter;
            }

        }

    }

}
