package com.tecnologiasintech.argussonora.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.guardias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 8/16/17.
 */
public class GuardiaAdapter extends RecyclerView.Adapter<GuardiaViewHolder>{

    private Context mContext;
    private List<guardias> mGuardias = new ArrayList<>();

    public GuardiaAdapter(Context context){
        mContext = context;
    }

    @Override
    public GuardiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.list_item_guardia, parent, false);
        return new GuardiaViewHolder(messageView);
    }

    @Override
    public void onBindViewHolder(GuardiaViewHolder holder, int position) {
        guardias guardia = mGuardias.get(position);
        holder.bindGuardia(guardia);
    }

    @Override
    public int getItemCount() {
        return mGuardias.size();
    }

    public void addGuardia(guardias guardia){
        boolean bandera = false;

        if (mGuardias.size()>0){

            for (guardias currentGuardia : mGuardias){

                if (currentGuardia.getUsuarioNombre().equals(guardia.getUsuarioNombre())){
                    bandera = true;
                }
            }
        }

        if (bandera == false){
            mGuardias.add(guardia);
        }

        notifyItemChanged(0);

    }

    public void removeGuardia(guardias guardiaremoved){

        int i = 0;
        int position = 0;
        final List<guardias> mGuardiasRemovalList = mGuardias;


        for (guardias currentGuardia : mGuardiasRemovalList){
            if (mGuardiasRemovalList.get(i).getUsuarioNombre().equals(guardiaremoved.getUsuarioNombre())){
                position = i;
            }
            i++;
        }

        mGuardias.remove(position);

        notifyItemRemoved(position);
    }
}
