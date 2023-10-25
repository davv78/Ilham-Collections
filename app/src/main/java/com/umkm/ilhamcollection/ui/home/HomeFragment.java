package com.umkm.ilhamcollection.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.umkm.ilhamcollection.DetailProduct;
import com.umkm.ilhamcollection.R;
import com.umkm.ilhamcollection.adapter.CategorytAdapter;
import com.umkm.ilhamcollection.adapter.ProductListAdapter;
import com.umkm.ilhamcollection.adapter.SliderAdapter;
import com.umkm.ilhamcollection.databinding.FragmentHomeBinding;
import com.umkm.ilhamcollection.model.ProductData;
import com.umkm.ilhamcollection.model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<ProductData> ProductList;
    private ProductListAdapter adapter;
    private CategorytAdapter catedapter;
    ProgressDialog progressDoalog;
    SliderView sliderView;
    private SliderAdapter adapterslide;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        View view= inflater.inflate(R.layout.fragment_home,container,false);




        RecyclerView catee = view.findViewById(R.id.btncategory);
        LinearLayoutManager layoutManagercate = new LinearLayoutManager(getContext());
        layoutManagercate.setOrientation(RecyclerView.HORIZONTAL);
        catee.setLayoutManager(layoutManagercate);
        ArrayList<String> catest = new ArrayList<>();
        for (int i=0;i<1;i++){
            catest.add("All");
            catest.add("Kemeja");
            catest.add("Hoodie");
            catest.add("Sweater");
        }
        HomeViewModel viewModel;
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        catedapter = new CategorytAdapter(getContext(), catest, new CategorytAdapter.ItemClickListener() {
            @Override
            public void onClick(String list) {
                if (list.equals("All")){
                    tampildata(view,viewModel);
                    viewModel.getAll();
                }else if(list.equals("Kemeja")){
                    tampildata(view,viewModel);
                    viewModel.getKemeja();
                }else if(list.equals("Hoodie")){
                    tampildata(view,viewModel);
                    viewModel.getHoodie();
                }else if(list.equals("Sweater")){
                    tampildata(view,viewModel);
                    viewModel.getSweater();
                }else {
                    Toast.makeText(getContext(),"Error list",Toast.LENGTH_LONG).show();
                }
            }
        });
        catee.setAdapter(catedapter);

        slide(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void tampildata(View view,HomeViewModel viewModel){
        RecyclerView recyclerView = view.findViewById(R.id.customRecyclerView);
        final TextView noresult = view.findViewById(R.id.noResultTv);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ProductListAdapter(getContext(), ProductList, new ProductListAdapter.ItemClickListener() {
            @Override
            public void onClick(ProductData result) {
                //Toast.makeText(getContext(),result.getTitle(),Toast.LENGTH_SHORT).show();
                String idd = String.valueOf(result.getId_pakaian());
                Intent intent = new Intent(getContext(), DetailProduct.class);
                intent.putExtra("idprod",idd);
                intent.putExtra("titlep",result.getNama());
                intent.putExtra("warna",result.getWarna());
                intent.putExtra("imageurl",result.getGambar());
                intent.putExtra("hargap",result.getHarga());
                intent.putExtra("desp",result.getKeunggulan());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        viewModel.getDataListObserver().observe(getViewLifecycleOwner(), new Observer<List<ProductData>>() {
            @Override
            public void onChanged(List<ProductData> dataList) {
                if(dataList != null) {
                    ProductList = dataList;
                    adapter.setProductList(dataList);
                    noresult.setVisibility(View.GONE);
                    progressDoalog.dismiss();
                } else {
                    noresult.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void slide(View view){
        sliderView = view.findViewById(R.id.image_slider);


        adapterslide = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(adapterslide);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 4; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i == 0) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg1.png");
            }else if (i == 1) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg2.png");
            }else if (i == 2) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg3.png");
            }else if (i == 3) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg4.png");
            }else if (i == 4) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg5.png");
            }else if (i == 5) {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg6.png");
            } else {
                sliderItem.setImageUrl("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/bg1.png");
            }
            sliderItemList.add(sliderItem);
        }
        adapterslide.slide(sliderItemList);
    }

}