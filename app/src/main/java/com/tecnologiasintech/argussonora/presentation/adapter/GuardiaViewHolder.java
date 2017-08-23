package com.tecnologiasintech.argussonora.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;

/**
 * Created by sergiosilva on 8/17/17.
 */

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

    public void bindGuardia(Guardia guardia){
        mGuardianameDisplay.setText(guardia.getUsuarioNombre());

        // Implement Binding Code
        // 1. Check if fecha is not null;

        // Implement Binding Code
        // TODO: set Current Date Key
        String fechaActual = "20170821";

        String fecha = guardia.getBitacoraSimple().getFecha();
        if (fecha.equals(fechaActual) ){
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
            if (guardia.getBitacoraSimple().getHorasextra() > 0){
                mHorasExtraIcon.setVisibility(View.VISIBLE);
            }

            // No Asitio
            if (guardia.getBitacoraSimple().isNoasistio()){
                mNoAsistioIcon.setVisibility(View.VISIBLE);
            }


        }else{
            // Update day and delete node
            // OverRide bitacora
            // TODO: If it's not the same date, decide how it will be handled, Handle in Signature Activity


        }

    }

    @Override
    public void onClick(View v) {
        // Transfer Information
        // TODO Send Intent on Given Information

    }
}
