package projects.yaseen.sg_mobiledata_usage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.yaseen.sg_mobiledata_usage.model.YearDataUsage;

public class MobileUsageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<YearDataUsage> mDataUsageList;
    private Context mContext;

    public MobileUsageAdapter(List<YearDataUsage> dataUsageList, Context context) {
        mContext = context;
        mDataUsageList = dataUsageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_year_data_usage, viewGroup, false);
        return new DataUsageVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DataUsageVH vh = (DataUsageVH) viewHolder;
        YearDataUsage yearDataUsage = mDataUsageList.get(position);

        Double totalUsage = 0.0;

        boolean hasDecreased = false;

        if (yearDataUsage.getDataQ1() != null) {
            vh.mQuarter1.setText("Q1\n" + new DecimalFormat("00.00").format(yearDataUsage.getDataQ1().getUsage()));
            totalUsage = totalUsage + yearDataUsage.getDataQ1().getUsage();

            if (position > 0 && mDataUsageList.get(position - 1) != null) {
                YearDataUsage prevYear = mDataUsageList.get(position - 1);
                if (prevYear.getDataQ4() != null) {
                    if (prevYear.getDataQ4().getUsage() > yearDataUsage.getDataQ1().getUsage()) {
                        vh.mQuarter1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_low));
                        hasDecreased = true;
                    } else {
                        vh.mQuarter1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_high));
                    }

                } else {
                    vh.mQuarter1.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
                }

            } else {
                vh.mQuarter1.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }
        } else {
            vh.mQuarter1.setText("Q1\n-");
            vh.mQuarter1.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));

        }

        if (yearDataUsage.getDataQ2() != null) {
            vh.mQuarter2.setText("Q2\n" + new DecimalFormat("00.00").format(yearDataUsage.getDataQ2().getUsage()));
            totalUsage = totalUsage + yearDataUsage.getDataQ2().getUsage();

            if (yearDataUsage.getDataQ1() != null) {
                if (yearDataUsage.getDataQ1().getUsage() > yearDataUsage.getDataQ2().getUsage()) {
                    vh.mQuarter2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_low));
                    hasDecreased = true;
                } else {
                    vh.mQuarter2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_high));
                }

            } else {
                vh.mQuarter2.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }
        } else {
            vh.mQuarter2.setText("Q2\n-");
            vh.mQuarter2.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        }

        if (yearDataUsage.getDataQ3() != null) {
            vh.mQuarter3.setText("Q3\n" + new DecimalFormat("00.00").format(yearDataUsage.getDataQ3().getUsage()));
            totalUsage = totalUsage + yearDataUsage.getDataQ3().getUsage();

            if (yearDataUsage.getDataQ2() != null) {
                if (yearDataUsage.getDataQ2().getUsage() > yearDataUsage.getDataQ3().getUsage()) {
                    vh.mQuarter3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_low));
                    hasDecreased = true;
                } else {
                    vh.mQuarter3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_high));
                }

            } else {
                vh.mQuarter3.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }
        } else {
            vh.mQuarter3.setText("Q3\n-");
            vh.mQuarter3.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));

        }

        if (yearDataUsage.getDataQ4() != null) {
            vh.mQuarter4.setText("Q4\n" + new DecimalFormat("00.00").format(yearDataUsage.getDataQ4().getUsage()));
            totalUsage = totalUsage + yearDataUsage.getDataQ4().getUsage();

            if (yearDataUsage.getDataQ3() != null) {
                if (yearDataUsage.getDataQ3().getUsage() > yearDataUsage.getDataQ4().getUsage()) {
                    vh.mQuarter4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_low));
                    hasDecreased = true;
                } else {
                    vh.mQuarter4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.data_usage_high));
                }
            } else {
                vh.mQuarter4.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }

        } else {
            vh.mQuarter4.setText("Q4\n-");
            vh.mQuarter4.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        }

        vh.usageYear.setText("Year " + String.valueOf(yearDataUsage.getYear()));
        vh.mTotalUsage.setText(new DecimalFormat("0.00").format(totalUsage) + " (unit) pb");

        if (hasDecreased) {
            vh.mLowUsageImageView.setVisibility(View.VISIBLE);
        } else {
            vh.mLowUsageImageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataUsageList.size();
    }


    public class DataUsageVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.quarter1)
        AppCompatTextView mQuarter1;

        @BindView(R.id.quarter2)
        AppCompatTextView mQuarter2;

        @BindView(R.id.quarter3)
        AppCompatTextView mQuarter3;

        @BindView(R.id.quarter4)
        AppCompatTextView mQuarter4;

        @BindView(R.id.total_usage)
        AppCompatTextView mTotalUsage;

        @BindView(R.id.usage_year)
        AppCompatTextView usageYear;

        @BindView(R.id.low_usage)
        AppCompatImageView mLowUsageImageView;

        public DataUsageVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mLowUsageImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "This Year has decrease in volume data (quarter)", Toast.LENGTH_SHORT).show();

        }
    }

}
