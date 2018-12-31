package projects.yaseen.sg_mobiledata_usage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import projects.yaseen.sg_mobiledata_usage.rest.ApiClient;
import projects.yaseen.sg_mobiledata_usage.rest.ServiceGenerator;
import projects.yaseen.sg_mobiledata_usage.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.animation_view)
    LottieAnimationView mAnimationView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecylcerView;

    ApiClient apiService;

    boolean isAnimating = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiService = ServiceGenerator.createService(ApiClient.class);
        getMobileUsageData();

    }


    private void getMobileUsageData() {
        Call<ResponseBody> call = apiService.getMobileDataUsage(Constants.RESOURCE_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                toggleanimation();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toggleanimation();
            }
        });
    }


    private void toggleanimation() {
        if (isAnimating) {
            mAnimationView.cancelAnimation();
            mAnimationView.setVisibility(View.GONE);
            mRecylcerView.setVisibility(View.VISIBLE);
        } else {
            mAnimationView.playAnimation();
            mAnimationView.setVisibility(View.VISIBLE);
            mRecylcerView.setVisibility(View.GONE);
        }

    }


}


