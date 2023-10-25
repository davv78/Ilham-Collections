package com.umkm.ilhamcollection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.umkm.ilhamcollection.R;
import com.umkm.ilhamcollection.model.TransactionData;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.MyViewHolder> {
    private Context context;
    private List<TransactionData> dataList;
    private MyViewHolder holder;
    private int position;

    public TransactionListAdapter(Context context, List<TransactionData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setTransactionList(List<TransactionData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TransactionData result = dataList.get(position);
        String nama = result.getNama();
        String harga = "Rp "+result.getHarga();
        String jumlah = result.getQuantity()+" Item";
        String status = result.getStatus();
        String waktu = result.getDate();
        if (status.equals("")){
            holder.statuspes.setText("Sedang dimasak");
        }else if (status.equals("in process")){
            holder.statuspes.setText("Dalam perjalanan");
        }else if (status.equals("closed")){
            holder.statuspes.setText("Pesanan telah diterima");
        }else if (status.equals("rejected")){
            holder.statuspes.setText("Pesanan dibatalkan");
            holder.statuspes.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        holder.namaproduk.setText(nama);
        holder.hargapesanan.setText(harga);
        holder.jumlahitemm.setText(jumlah);
        holder.waktupes.setText(waktu);

    }

    @Override
    public int getItemCount() {
        if(this.dataList != null) {
            return this.dataList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView namaproduk,hargapesanan,jumlahitemm,statuspes,waktupes;

        MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            namaproduk= mView.findViewById(R.id.namaprodukk);
            hargapesanan = mView.findViewById(R.id.totalpesan);
            jumlahitemm = mView.findViewById(R.id.jumlahitem);
            statuspes = mView.findViewById(R.id.statuspesanan);
            waktupes = mView.findViewById(R.id.waktupesanan);

        }
    }

}

