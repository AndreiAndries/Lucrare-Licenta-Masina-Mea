package com.example.licentafii2022;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class FragmentProfile extends Fragment implements View.OnClickListener{

    ImageView imageView;
    TextView name, bio, phone, email;
    ImageButton edit ,menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment4,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = getActivity().findViewById(R.id.profile_image_f4);
        name = getActivity().findViewById(R.id.name_f4);
        bio = getActivity().findViewById(R.id.bio_f4);
        email = getActivity().findViewById(R.id.email_f4);
        phone = getActivity().findViewById(R.id.phone_f4);
        edit = getActivity().findViewById(R.id.edit_button_profile);
        menu = getActivity().findViewById(R.id.ib_menu_f4);
        edit.setOnClickListener(this);
        menu.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.edit_button_profile:
                startActivity(new Intent(getActivity(), EditProfile.class));
                break;
            case R.id.ib_menu_f4:
                BottomMenu bottomMenu = new BottomMenu();
                bottomMenu.show(getFragmentManager(), "bottomsheet");
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        reference = firebaseFirestore.collection("user").document(userId);

        reference.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()){
                String name = task.getResult().getString("name");
                String bio = task.getResult().getString("bio");
                String phone = task.getResult().getString("phone");
                String email = task.getResult().getString("email");
                String imageUrl = task.getResult().getString("url");

                Picasso.get().load(imageUrl).into(imageView);
                this.name.setText(name);
                this.phone.setText(phone);
                this.bio.setText(bio);
                this.email.setText(email);
            }else{
                Intent intent = new Intent(getActivity(),CreateProfile.class);
                startActivity(intent);
            }
        });
    }
}
