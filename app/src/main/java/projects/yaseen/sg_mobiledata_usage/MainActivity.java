package projects.yaseen.sg_mobiledata_usage;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import projects.yaseen.sg_mobiledata_usage.model.DataUsageResponse;
import projects.yaseen.sg_mobiledata_usage.model.QuarterUsage;
import projects.yaseen.sg_mobiledata_usage.model.YearDataUsage;
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

    private MobileUsageAdapter mAdapter;

    private List<YearDataUsage> mDataUsageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiService = ServiceGenerator.createService(ApiClient.class);

        mRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MobileUsageAdapter(mDataUsageList, this);
        mRecylcerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecylcerView.setAdapter(mAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMobileUsageData();
            }
        }, 2000);

    }


    private void getMobileUsageData() {
        Call<DataUsageResponse> call = apiService.getMobileDataUsage(Constants.RESOURCE_ID);
        call.enqueue(new Callback<DataUsageResponse>() {
            @Override
            public void onResponse(Call<DataUsageResponse> call, Response<DataUsageResponse> response) {

                List<DataUsageResponse.Results.QuarterUsageRecord> resultList = response.body().getData().getRecordList();

                for (DataUsageResponse.Results.QuarterUsageRecord record : resultList) {

                    String[] parts = record.getQuarter().split("-");

                    try {
                        int year = Integer.parseInt(parts[0]);
                        String quarter = parts[1];

                        if (year > 2007) {

                            YearDataUsage yearDataUsage = new YearDataUsage();
                            yearDataUsage.setYear(year);

                            QuarterUsage quarterUsage = new QuarterUsage();
                            quarterUsage.setQuarter(quarter);
                            quarterUsage.setUsage(Double.parseDouble(record.getDataVolume()));
                            yearDataUsage.setDataForQuarter(quarter, quarterUsage);

                            boolean isExists = false;
                            for (YearDataUsage yearRecord : mDataUsageList) {
                                if (yearRecord.getYear() == year) {
                                    isExists = true;
                                    yearRecord.setDataForQuarter(quarter, quarterUsage);
                                }
                            }

                            if (!isExists) {
                                mDataUsageList.add(yearDataUsage);
                            }

                        }

                    } catch (NumberFormatException e) {

                    } catch (IllegalArgumentException e) {

                    }

                }

                mAdapter.notifyDataSetChanged();


                toggleanimation();

            }

            @Override
            public void onFailure(Call<DataUsageResponse> call, Throwable t) {

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


