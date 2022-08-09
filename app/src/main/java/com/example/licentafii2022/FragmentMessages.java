package com.example.licentafii2022;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.squareup.picasso.Picasso;

public class FragmentMessages extends Fragment implements View.OnClickListener{

    FloatingActionButton actionButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    ImageView imageView;
    String userId;
    FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, favMessageReference, favList;
    Boolean favChecker = false;
    RecyclerView viewAllMessages;
    Message message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = getActivity().findViewById(R.id.profile_image_f3);
        actionButton = getActivity().findViewById(R.id.floatingActionButton_f3);
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();
        reference = db.collection("user").document(userId);
        actionButton.setOnClickListener(this);
        imageView.setOnClickListener(this);


        viewAllMessages = getActivity().findViewById(R.id.recyclerView_f3);
        viewAllMessages.setHasFixedSize(true);
        viewAllMessages.setLayoutManager(new LinearLayoutManager(getActivity()));


        databaseReference = database.getReference("AllMessages");
        favMessageReference = database.getReference("FavouriteMessages");
        favList = database.getReference("FavouriteMessagesList").child(userId);


        FirebaseRecyclerOptions<Message> optionsMessages = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(databaseReference, Message.class).build();
        FirebaseRecyclerAdapter<Message, ViewMessage> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, ViewMessage>(optionsMessages) {
            @Override
            protected void onBindViewHolder(@NonNull ViewMessage holder, int position, @NonNull Message model) {
                final String postKey = getRef(position).getKey();
                holder.setItem(getActivity(),model.getName(),model.getUrl(),model.getUserId(),model.getMessage(),model.getTime(),model.getKey());
                String msg = getItem(position).getMessage();
                String name = getItem(position).getName();
                String url = getItem(position).getUrl();
                String time = getItem(position).getTime();

                holder.favouriteChecker(postKey);
                holder.saveButton.setOnClickListener(view -> {
                    favChecker = true;
                    favMessageReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(favChecker.equals(true)){
                                if(snapshot.child(postKey).hasChild(userId)){
                                    favMessageReference.child(postKey).child(userId).removeValue();
                                    Query query = favList.orderByChild("time").equalTo(time);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                dataSnapshot.getRef().removeValue();
                                                Toast.makeText(getActivity(), "Deleted from Bookmarks!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {}
                                    });
                                }else {
                                    favMessageReference.child(postKey).child(userId).setValue(true);
                                    message = new Message(name,url,userId,msg,time);
                                    String id = favList.push().getKey();
                                    favList.child(id).setValue(message);
                                }
                                favChecker = false;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                });
            }

            @NonNull
            @Override
            public ViewMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
                return new ViewMessage(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        viewAllMessages.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.get().addOnCompleteListener((task)->{
            if(task.getResult().exists()){
                String imageUrl = task.getResult().getString("url");
                Picasso.get().load(imageUrl).into(imageView);
            }else{
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        } );
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image_f3:
                startActivity(new Intent(getActivity(), BookmarkedMessagesActivity.class));
                break;
            case R.id.floatingActionButton_f3:
                Intent intent = new Intent(getActivity(), PutMessage.class);
                startActivity(intent);
                break;
        }
    }
}
