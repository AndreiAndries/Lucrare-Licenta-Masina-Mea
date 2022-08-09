package com.example.licentafii2022;

import android.app.Application;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewMessage extends RecyclerView.ViewHolder {

    ImageView profileImageView;
    TextView time,name, message;
    ImageButton saveButton;
    DatabaseReference favouriteReferince;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ViewMessage(@NonNull View itemView) {
        super(itemView);
    }

    public void setItem(FragmentActivity activity,String name,String url,String userId, String question, String time,String key){
        profileImageView = itemView.findViewById(R.id.profile_image_message_item);
        this.time = itemView.findViewById(R.id.time_qi);
        this.name = itemView.findViewById(R.id.name_qi);
        this.message = itemView.findViewById(R.id.message_qi);

        Picasso.get().load(url).into(profileImageView);

        this.name.setText(name);
        this.time.setText(time);
        this.message.setText(question);
    }


    public void setItemBookmarked(Application activity, String name, String url, String userId, String question, String time, String key){
        ImageView imageViewB = itemView.findViewById(R.id.profile_image_bookmarked_item);
        TextView timeB = itemView.findViewById(R.id.time_bi);
        TextView nameB = itemView.findViewById(R.id.name_bi);
        TextView questionB = itemView.findViewById(R.id.question_bi);

        Picasso.get().load(url).into(imageViewB);
        nameB.setText(name);
        timeB.setText(time);
        questionB.setText(question);
    }

    public void favouriteChecker(String postKey) {
        saveButton = itemView.findViewById(R.id.save_fav_qi);
        favouriteReferince = database.getReference("FavouriteMessages");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        favouriteReferince.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postKey).hasChild(uid)){
                    saveButton.setImageResource(R.drawable.bookmark_saved_logo);
                }else{
                    saveButton.setImageResource(R.drawable.bookmark_unsaved_logo);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
