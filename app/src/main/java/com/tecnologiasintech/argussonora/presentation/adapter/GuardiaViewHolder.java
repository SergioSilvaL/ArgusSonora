package com.tecnologiasintech.argussonora.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.DatePost;
import com.tecnologiasintech.argussonora.domain.guardias;

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

    public void bindGuardia(guardias guardia){
        mGuardianameDisplay.setText(guardia.getUsuarioNombre());

        // Implement Binding Code

        // Asistio
        if (guardia.isUsuarioAsistio()){
            if (guardia.getUsuarioAsistenciaFecha()!=null){
                if (guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())){
                    mAsistioIcon.setVisibility(View.VISIBLE);
                }
            }
        }

        // Cubre Descanso
        if (guardia.isUsuarioCubreTurno()){
            if (guardia.getUsuarioAsistenciaFecha()!=null){
                if (guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())){
                    mDescansoElaboradoIcon.setVisibility(View.VISIBLE);
                }
            }
        }

        // Doble Turno
        if (guardia.isUsuarioDobleTurno()){
            if (guardia.getUsuarioAsistenciaFecha()!=null){
                if (guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())){
                    mDobleTurnoIcon.setVisibility(View.VISIBLE);
                }
            }
        }

        // Horas Extra
        if (guardia.getUsuarioHorasExtra()>0){
            if (guardia.getUsuarioAsistenciaFecha()!=null){
                if (guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())){
                    mHorasExtraIcon.setVisibility(View.VISIBLE);
                }
            }
        }

        // No Asisitio
        if (guardia.isUsuarioAsistio() == false){
            if (guardia.getUsuarioAsistenciaFecha()!=null){
                if (guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())){
                    mNoAsistioIcon.setVisibility(View.VISIBLE);

                    // Set the rest to False
                    mAsistioIcon.setVisibility(View.INVISIBLE);
                    mHorasExtraIcon.setVisibility(View.INVISIBLE);
                    mDescansoElaboradoIcon.setVisibility(View.INVISIBLE);
                    mDobleTurnoIcon.setVisibility(View.INVISIBLE);
                }
            }
        }
        // Todo Set Invisible


    }


    @Override
    public void onClick(View v) {
        // Transfer Information
    }
}
