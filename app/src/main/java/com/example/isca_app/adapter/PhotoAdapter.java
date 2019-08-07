package com.example.isca_app.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isca_app.R;
import com.example.isca_app.model.Retrofit.PhotoModel;
import com.example.isca_app.model.Room.entity.Photo;
import com.example.isca_app.view.FullScreenPhotoActivity;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

public class PhotoAdapter extends PagedListAdapter<PhotoModel,PhotoAdapter.PhotoHolder> {

    Context mContext;
    Activity mActivity;
    public PhotoAdapter(Context context,Activity activity) {
        super(PhotoModel.CALLBACK);
        mContext=context;
        mActivity=activity;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_item, null); //client_recycleview
        PhotoHolder photoHolder = new PhotoHolder(layoutView);
        return photoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoHolder photoHolder, int potsition) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            View decor = mActivity.getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);

            fade.excludeTarget(android.R.id.navigationBarBackground, true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setEnterTransition(fade);
                mActivity.getWindow().setExitTransition(fade);
            }
        }

        final PhotoModel model=getItem(potsition);


        final RotateLoading rotateLoading=photoHolder.rotateLoading;
        rotateLoading.start();

        Picasso.with(mContext).load("https:\\/\\/live.staticflickr.com\\/"+model.getServer()+"\\/"+model.getId()+"_"+model.getSecret()+"_m.jpg").fit().centerInside()
                .into(photoHolder.photoItem, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        rotateLoading.stop();
                    }
                    @Override
                    public void onError() {
                        rotateLoading.stop();
                        photoHolder.photoItem.setImageResource(R.drawable.ic_action_navigation_arrow_back);
                    }
                });
        photoHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MyCustomDualSimDialog(model);
                return false;
            }
        });
        photoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair pair=new Pair<View,String>(photoHolder.photoItem,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(mActivity,pair);
                }
                Intent intent=new Intent(mContext, FullScreenPhotoActivity.class);
                intent.putExtra("URL","https:\\/\\/live.staticflickr.com\\/"+model.getServer()+"\\/"+model.getId()+"_"+model.getSecret()+"_z.jpg");
                mActivity.startActivity(intent,options.toBundle());
            }
        });
    }





    public class PhotoHolder extends RecyclerView.ViewHolder {

        ImageView photoItem;
        RotateLoading rotateLoading;
        public PhotoHolder(View itemView) {
            super(itemView);

            rotateLoading=itemView.findViewById(R.id.rotateloading);
            photoItem=itemView.findViewById(R.id.photo_item);

        }
    }


    Dialog MyDialogPhoto;

    public void MyCustomDualSimDialog(PhotoModel photoModel) {
        MyDialogPhoto = new Dialog(mContext);
        MyDialogPhoto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialogPhoto.setContentView(R.layout.dialog_photo);
        Window window = MyDialogPhoto.getWindow();
        window.setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        MyDialogPhoto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView id, owner, secret, server, farm, title, ispublic, isfriend, isfamily;

        id=MyDialogPhoto.findViewById(R.id.Id_text);
        owner=MyDialogPhoto.findViewById(R.id.owner_text);
        secret=MyDialogPhoto.findViewById(R.id.secret_text);
        server=MyDialogPhoto.findViewById(R.id.server_text);
        farm=MyDialogPhoto.findViewById(R.id.farm_text);
        title=MyDialogPhoto.findViewById(R.id.Photo_Title);
        ispublic=MyDialogPhoto.findViewById(R.id.ispublic_text);
        isfriend=MyDialogPhoto.findViewById(R.id.isfriend_text);
        isfamily=MyDialogPhoto.findViewById(R.id.isfamily_text);

        id.setText(photoModel.getId());
        owner.setText(photoModel.getOwner());
        secret.setText(photoModel.getSecret());
        server.setText(photoModel.getServer());
        farm.setText(String.valueOf(photoModel.getFarm()));
        title.setText(photoModel.getTitle());
        ispublic.setText(String.valueOf(photoModel.getIspublic()));
        isfriend.setText(String.valueOf(photoModel.getIsfriend()));
        isfamily.setText(String.valueOf(photoModel.getIsfamily()));

        MyDialogPhoto.show();
    }
}
