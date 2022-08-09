package com.example.licentafii2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookmarkedMessagesActivity extends AppCompatActivity {

    /**
     * în această clasă vor apărea toate mesajele pe care un utilizator le-a salvat vreodată apăsând butonul de bookmark
     * */
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    String userId;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_message);
        recyclerView = findViewById(R.id.bookmark_rv_abq);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = database.getReference("FavouriteMessagesList").child(userId);

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(reference, Message.class).build();
        FirebaseRecyclerAdapter<Message, ViewMessage> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, ViewMessage>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewMessage holder, int position, @NonNull Message model) {
                holder.setItemBookmarked(getApplication(),model.getName(),model.getUrl(),model.getUserId(),model.getMessage(),model.getTime(),model.getKey());
            }

            @NonNull
            @Override
            public ViewMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_item,parent,false);
                return new ViewMessage(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}