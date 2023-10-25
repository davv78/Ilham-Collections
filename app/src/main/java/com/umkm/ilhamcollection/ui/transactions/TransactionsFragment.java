package com.umkm.ilhamcollection.ui.transactions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umkm.ilhamcollection.R;
import com.umkm.ilhamcollection.adapter.TransactionListAdapter;
import com.umkm.ilhamcollection.databinding.FragmentTransactionsBinding;
import com.umkm.ilhamcollection.model.TransactionData;

import java.util.List;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    private TransactionListAdapter adapter;
    private TransactionsViewModel viewModel;
    private List<TransactionData> transactionDataList;
    ProgressDialog progressDoalog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        View view = inflater.inflate(R.layout.fragment_transactions,container,false);

        getTransactiondata(view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void getTransactiondata(View view){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("id_user","").toString();

        RecyclerView recyclerView = view.findViewById(R.id.customRecyclerViewtr);
        final TextView noresult = view.findViewById(R.id.noResultTv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new TransactionListAdapter(getContext(),transactionDataList);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        viewModel.getDataListObserver().observe(getViewLifecycleOwner(), new Observer<List<TransactionData>>() {
            @Override
            public void onChanged(List<TransactionData> dataList) {
                if(dataList != null) {
                    transactionDataList = dataList;
                    adapter.setTransactionList(dataList);
                    noresult.setVisibility(View.GONE);
                    progressDoalog.dismiss();
                } else {
                    noresult.setVisibility(View.VISIBLE);
                    progressDoalog.dismiss();
                }
            }
        });
        viewModel.getDataTransaction(id_user,getContext());
    }
}