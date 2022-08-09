package com.example.licentafii2022;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BottomMenu extends BottomSheetDialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    CardView logout, deleteProfile;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String profileImageUrl;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.bottom_menu,null);
        logout = view.findViewById(R.id.card_view_logout);
        deleteProfile = view.findViewById(R.id.card_view_delete);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("All Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();
        reference = db.collection("user").document(userId);

        reference.get().addOnCompleteListener(task -> {
            if (task.getResult().exists())
                profileImageUrl = task.getResult().getString("url");
        });

        logout.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout").setMessage("Are you sure to logout?")
                    .setNegativeButton("no", (dialogInterface, i) -> {
                    }).setPositiveButton("Yes", (dialogInterface, i) -> {
                mAuth.signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            });
            builder.create();
            builder.show();
        });

        deleteProfile.setOnClickListener(view12 -> {AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete Profile").setMessage("Are you sure?")
                    .setNegativeButton("no", (dialogInterface, i) -> {
                    }).setPositiveButton("Yes", (dialogInterface, i) -> reference.delete().addOnSuccessListener(unused -> {
                Query query =databaseReference.orderByChild("userId").equalTo(userId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                            dataSnapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(profileImageUrl);
                ref.delete().addOnCompleteListener(task -> Toast.makeText(getActivity(), "Profile Deleted", Toast.LENGTH_SHORT).show());
            }));
            builder.create();
            builder.show();});


        return view;
    }
}
