package com.umkm.ilhamcollection.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.umkm.ilhamcollection.model.ProductData;
import com.umkm.ilhamcollection.model.service.ApiClient;
import com.umkm.ilhamcollection.model.service.GetService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<ProductData>> dataList;

    public HomeViewModel(){
        dataList = new MutableLiveData<>();
    }
    public MutableLiveData<List<ProductData>> getDataListObserver() {
        return dataList;
    }

    public void getAll() {
        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<List<ProductData>> call = service.getAll();
        call.enqueue(new Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                dataList.postValue(response.body());
            }
            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                dataList.postValue(null);
            }
        });
    }
    public void getKemeja() {
        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<List<ProductData>> call = service.getKemeja();
        call.enqueue(new Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                dataList.postValue(null);
            }
        });
    };
    public void getHoodie() {
        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<List<ProductData>> call = service.getHoodie();
        call.enqueue(new Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                dataList.postValue(null);
            }
        });
    }
    public void getSweater() {
        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<List<ProductData>> call = service.getSweater();
        call.enqueue(new Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                dataList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                dataList.postValue(null);
            }
        });
    }
}