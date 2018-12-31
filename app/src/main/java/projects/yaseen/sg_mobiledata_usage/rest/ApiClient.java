package projects.yaseen.sg_mobiledata_usage.rest;

import okhttp3.ResponseBody;
import projects.yaseen.sg_mobiledata_usage.model.DataUsageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
        @GET("datastore_search")
        Call<DataUsageResponse> getMobileDataUsage(@Query("resource_id") String resourceId);
}
