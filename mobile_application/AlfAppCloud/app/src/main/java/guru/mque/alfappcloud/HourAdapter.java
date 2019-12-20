package guru.mque.alfappcloud;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HourAdapter extends FirestoreRecyclerAdapter<Hour, HourAdapter.HourHolder> {


    public HourAdapter(@NonNull FirestoreRecyclerOptions<Hour> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HourHolder holder, int position, @NonNull Hour model) {
    holder.textViewNumber.setText(String.valueOf(model.getNumber()));
    holder.textViewDate.setText(model.getDate());
    holder.textViewState.setText(model.getState());
    }

    @NonNull
    @Override
    public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        return new HourHolder(v);
    }

    class HourHolder extends RecyclerView.ViewHolder{

            TextView textViewDate;
            TextView textViewNumber;
            TextView textViewState;

        public HourHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
            textViewState = itemView.findViewById(R.id.text_view_state);

        }
    }

}
