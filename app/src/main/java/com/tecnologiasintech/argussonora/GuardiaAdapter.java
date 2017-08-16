package com.tecnologiasintech.argussonora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sergiosilva on 8/16/17.
 */
public class GuardiaAdapter extends RecyclerView.Adapter<GuardiaAdapter.GuardiaViewHolder>{

    private Context mContext;

    public GuardiaAdapter(Context context){
        mContext = context;
    }

    @Override
    public GuardiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(GuardiaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GuardiaViewHolder extends RecyclerView.ViewHolder {
        public GuardiaViewHolder(View itemView) {
            super(itemView);
        }
    }
}
