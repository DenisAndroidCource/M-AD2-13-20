package by.itacademy.android.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText newItemEditText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new NameListAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        newItemEditText = findViewById(R.id.newItemEditText);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = newItemEditText.getText().toString();
                if (!newName.trim().isEmpty() && recyclerView.getAdapter() != null) {
                    NameListAdapter adapter1 = (NameListAdapter) recyclerView.getAdapter();
                    adapter1.addItem(newName);
                }
            }
        });
    }

    static class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.ItemViewHolder> {

        private ArrayList<String> items = new ArrayList<>();

        NameListAdapter() {
        }

        void addItem(String name) {
            items.add(name);
//            notifyItemChanged(items.indexOf(name)); // for item
            notifyDataSetChanged(); // for all items
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bindData(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;

            ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.nameText);
            }

            void bindData(String name) {
                nameText.setText(name);
            }
        }
    }
}
