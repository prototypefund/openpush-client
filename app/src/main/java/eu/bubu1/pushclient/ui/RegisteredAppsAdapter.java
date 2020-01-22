package eu.bubu1.pushclient.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import eu.bubu1.pushclient.R;
import eu.bubu1.pushclient.apimodels.AppRegistration;
import eu.bubu1.pushclient.db.entity.Registration;

import java.util.ArrayList;
import java.util.List;

public class RegisteredAppsAdapter extends RecyclerView.Adapter<RegisteredAppsAdapter.ViewHolder> {

    private List<RegistrationItem> items;
    private Context context;

    public RegisteredAppsAdapter(Context context){
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(items.get(position).title);
        holder.mContentView.setCompoundDrawablesRelativeWithIntrinsicBounds(items.get(position).icon, null, null, null);

        holder.mView.setOnClickListener(v -> {

        });
    }

    void setApps(List<Registration> apps){
        items = new ArrayList<>();
        for (Registration r: apps){
            items.add(new RegistrationItem(r, context.getPackageManager()));
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (items != null){
            return items.size();
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
