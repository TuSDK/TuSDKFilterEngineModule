package org.lasque.tusdkdemohelper.tusdk.filter.newFilterUI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;

import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

/**
 * TuSDK
 * TuSDKFilterEngineModule$
 *
 * @author H.ys
 * @Date 2020/07/07$ 17:29$
 * @Copyright (c) 2019 tusdk.com. All rights reserved.
 */
public class FilterFragment extends Fragment {

    private static final String FILTER_GROUP = "FilterGroup";

    private int mCurrentPosition = -1;

    public static FilterFragment newInstance(FilterGroup group){
        FilterFragment fragment = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FILTER_GROUP,group);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FilterGroup mFilterGroup;
    private FilterOptionRecyclerAdapter mFilterAdapter;
    private RecyclerView mFilterView;

    private OnFilterItemClickListener mListener;

    public void setOnFilterItemClickListener(OnFilterItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnFilterItemClickListener{
        void onFilterItemClick(String code);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(TuSdkContext.getLayoutResId("tusdk_filter_fragment_layout"), null, false);

        Bundle bundle = getArguments();
        if (bundle != null){
            mFilterGroup = (FilterGroup) bundle.getSerializable(FILTER_GROUP);
            getFilterView(view);
        }
        return view;
    }

    private void getFilterView(View view){
        mFilterView = view.findViewById(R.id.lsq_filter_recycler_view);
        mFilterAdapter = new FilterOptionRecyclerAdapter(new ArrayList<FilterOption>(mFilterGroup.filters));
        mFilterAdapter.setItemCilckListener(mFilterClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(HORIZONTAL);
        mFilterView.setLayoutManager(layoutManager);
        mFilterView.setAdapter(mFilterAdapter);
        if (mCurrentPosition != -1)
            mFilterAdapter.setCurrentPos(mCurrentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void removeFilter(){
        mFilterAdapter.setCurrentPos(-1);
    }

    public void setCurrentPos(int position){
        if (mFilterAdapter !=null)
            mFilterAdapter.setCurrentPos(position);
        mCurrentPosition = position;
    }

    private FilterOptionRecyclerAdapter.ItemClickListener mFilterClickListener = new FilterOptionRecyclerAdapter.ItemClickListener() {

        private int previewPosition = -1;
        @Override
        public void onItemClick(int position) {
            if (previewPosition == position){
                mFilterAdapter.changeShowParameterState();
            }
            previewPosition = position;
            String code = mFilterAdapter.getFilterCode(position);
            mFilterAdapter.setCurrentPos(position);
            if (mListener != null) mListener.onFilterItemClick(code);
        }
    };

}
