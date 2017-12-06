package kr.co.wooltari.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.pet.PetDetailActivity;
import kr.co.wooltari.util.LoadUtil;


public class PetListRecyclerAdapter extends RecyclerView.Adapter<PetListRecyclerAdapter.Holder>{

    List<PetDummy.Dummy> data;
    Context context;

    public PetListRecyclerAdapter(List<PetDummy.Dummy> data, Context context){
        this.context=context;
        this.data=data;
    }

    @Override
    public PetListRecyclerAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PetDummy.Dummy pet = data.get(position);
        //holder.imageView.setImageURI();
        holder.pPK = pet.pPK;
        holder.petName.setText(pet.pName+"");
        LoadUtil.circleImageLoad(context,pet.pProfile,holder.petImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        int pPK;
        ImageView petImage;
        TextView petName;
        public Holder(View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.Main_PetImage_imageview);
            petName = itemView.findViewById(R.id.Main_PetName_textview);

            setListener(itemView);
        }
        private void setListener(View itemView){
            itemView.setOnClickListener( v ->{
                Intent intent = new Intent(context, PetDetailActivity.class);
                intent.putExtra(Const.PET_ID, pPK);
                context.startActivity(intent);
            });
        }
    }
}
