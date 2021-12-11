package com.intermediate.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RAdapter extends RecyclerView.Adapter<RAdapter.NumberViewHolder> {

    // Represents the total number of times the NumberViewHolder is asked by RecyclerView.
    private static int viewHolderInstance;

    // Represents the total number of item Views this Adapter will store.
    private final int mNumberOfItems;

    // Allows us to use an External click handler for our RecyclerView items.
    private final ListItemClickListener mListItemClickListener;

    // Contains a single method having the clicked item view's position in the adapter's data set.
    public interface ListItemClickListener {

        /**
         * Method gets invoked whenever any list item in the RecyclerView is clicked by the user.
         *
         * @param position Position of the clicked View in the adapter dataset.
         */
        void onListItemClick(int position);
    }

    public RAdapter(int numberOfItems, ListItemClickListener listItemClickListener) {
        mNumberOfItems = numberOfItems;
        mListItemClickListener = listItemClickListener;
        viewHolderInstance = 0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * Inflating of View happens here based on "viewType".
         *
         * This is called by RecyclerView in order to inflate item Views as many as the total
         * number of items or fill in and scroll the screen, mainly, whichever requires FEWER
         * ViewHolders.
         *
         * After this the RecyclerView will call the "onBindViewHolder" to bind each item with
         * data.
         */
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.number_list_item, parent, false);

        // Set this ViewHolder's instance.
        TextView textHolder = view.findViewById(R.id.item_text_holder);
        textHolder.setText(parent.getContext().getString(R.string.holder_index,
                viewHolderInstance));

        // Incrementing ViewHolder instance.
        viewHolderInstance++;

        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        /*
         * Binding data with the inflated/cached item View.
         *
         * The position of item within the adapter's dataset is passed.
         */
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        /*
         * RecyclerView first calls this method to know the number of items in the data-set
         * (ArrayList, Networking Result, Database).
         *
         * It may ask this multiple time during the layout process. Meaning, we shouldn't perform
         * any large operations here.
         */
        return mNumberOfItems;
    }

    /**
     * Class describes the item view and its properties/meta-data about its place in the
     * RecyclerView.
     * <p>
     * Class is purposefully nested inside the Adapter.
     * <p>
     * Note - This class is NOT responsible for any inflation of Views. This class IS responsible
     * for CACHING Views that are modified later in the Adapter.
     * <p>
     * To provide click functionality for each list item, we've implemented the OnClickListener
     * from View. This is then linked with the external interface -> ListItemClickListener.
     */
    protected class NumberViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        // View is placed inside the layout file "number_list_item.xml".
        private final TextView textView;

        // Constructor needs an View that was previously created by the layout file.
        public NumberViewHolder(View itemView) {
            super(itemView);

            // Initializing layout view items here.
            textView = itemView.findViewById(R.id.item_text_number);

            // Attaching a click listener to the entire list item.
            itemView.setOnClickListener(this);
        }

        /**
         * Binds data to this TextView.
         *
         * @param listIndex is the index of an item view placed in RecyclerView.
         */
        public void bindData(int listIndex) {
            textView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View v) {
            // Provide the index position for the clicked item.
            mListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}