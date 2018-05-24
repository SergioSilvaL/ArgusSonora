package com.tecnologiasintech.argussonora.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;
import com.tecnologiasintech.argussonora.presentation.activity.GuardiaActivity;

import java.util.List;

/**
 * Created by sergiosilva on 8/16/17.
 */
public class GuardiaAdapter extends RecyclerView.Adapter<GuardiaAdapter.GuardiaViewHolder>{


    private Context mContext;
    private Client mClient;
    private List<GuardiaBitacora> mGuardiaBitacoraList;
    public static final String TAG = GuardiaAdapter.class.getSimpleName();


    public GuardiaAdapter(Context context, Client client, List<GuardiaBitacora> guardiaBitacora){
        mContext = context;
        mClient = client;
        mGuardiaBitacoraList = guardiaBitacora;
    }

    @Override
    public GuardiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_guardia, parent, false);
        return new GuardiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuardiaViewHolder holder, int position) {
        GuardiaBitacora guardiaBitacora = mGuardiaBitacoraList.get(position);
        holder.bindGuardia(guardiaBitacora);
    }

    @Override
    public int getItemCount() {
        return mGuardiaBitacoraList.size();
    }

    public void updateBitacoraSimple(BitacoraSimple bitacoraSimple, int pos){
        // TODO: set Correct status

        // Create route if does not exist
        if (mGuardiaBitacoraList.get(pos).getBitacoraSimple() == null){
            BitacoraSimple newBitacoraSimple = new BitacoraSimple();
            mGuardiaBitacoraList.get(pos).setBitacoraSimple(newBitacoraSimple);

            mGuardiaBitacoraList.get(pos).getBitacoraSimple().setAsistio(true);
            mGuardiaBitacoraList.get(pos).getBitacoraSimple().setFecha(new DatePost().getDateKey());
        }else {

            mGuardiaBitacoraList.get(pos).getBitacoraSimple().setAsistio(true);
        }
        notifyItemChanged(pos);
    }

    public class GuardiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mGuardianameDisplay;
        private ImageView mAsistioIcon, mNoAsistioIcon, mDobleTurnoIcon, mDescansoElaboradoIcon,
                mHorasExtraIcon;

        public GuardiaViewHolder(View itemView) {
            super(itemView);

            mGuardianameDisplay = (TextView) itemView.findViewById(R.id.nameTxt);
            mAsistioIcon = (ImageView) itemView.findViewById(R.id.asistioIcon);
            mNoAsistioIcon = (ImageView) itemView.findViewById(R.id.inasistioIcon);
            mDobleTurnoIcon = (ImageView) itemView.findViewById(R.id.dobleTurnoIcon);
            mDescansoElaboradoIcon = (ImageView) itemView.findViewById(R.id.descansoElaboradoIcon);
            mHorasExtraIcon = (ImageView) itemView.findViewById(R.id.horasExtraIcon);

            itemView.setOnClickListener(this);
        }

        public void bindGuardia(GuardiaBitacora guardia){

            mGuardianameDisplay.setText(guardia.getUsuarioNombre());

            if (guardia.getBitacoraSimple() != null && guardia.getBitacoraSimple().getFecha() != null){
                if (guardia.getBitacoraSimple().getFecha().equals(new DatePost().getDateKey()) ){
                    // Bind

                    // Asistio
                    if (guardia.getBitacoraSimple().isAsistio()){
                        mAsistioIcon.setVisibility(View.VISIBLE);
                    }

                    // Cubre Descanso
                    if (guardia.getBitacoraSimple().isCubredescanso()){
                        mDescansoElaboradoIcon.setVisibility(View.VISIBLE);
                    }

                    // Doble Turno
                    if (guardia.getBitacoraSimple().isDobleturno()){
                        mDobleTurnoIcon.setVisibility(View.VISIBLE);
                    }

                    // Horas Extra
                    if (guardia.getBitacoraSimple().getHorasExtra() > 0){
                        mHorasExtraIcon.setVisibility(View.VISIBLE);
                    }

                    // No Asitio
                    if (guardia.getBitacoraSimple().isNoasistio()){
                        mNoAsistioIcon.setVisibility(View.VISIBLE);
                    }

                }
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, GuardiaActivity.class);
            intent.putExtra(ClienteActivity.EXTRA_CLIENTE, mClient);
            intent.putExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA , mGuardiaBitacoraList.get(getAdapterPosition()));
            intent.putExtra(ClienteActivity.EXTRA_LIST_POSITION, getAdapterPosition());
            ((Activity)mContext).startActivityForResult(intent, ClienteActivity.REQUEST_FAVORITE);
        }
    }



}
