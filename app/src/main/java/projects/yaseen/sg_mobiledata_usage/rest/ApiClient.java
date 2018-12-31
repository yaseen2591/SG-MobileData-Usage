package projects.yaseen.sg_mobiledata_usage.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
        @GET("datastore_search")
        Call<ResponseBody> getMobileDataUsage(@Query("resource_id") String resourceId);
}
