package com.intermediate.recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.intermediate.recycler_view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        RAdapter.ListItemClickListener {

    // Used to show the index position of the clicked list item in the RecyclerView.
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate((LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        /*
         * LayoutManager determines how the collection of items is displayed. It is also responsible
         * for determining when to RECYCLE item Views when they are scrolled off the screen.
         *
         * Initializing LinearLayoutManager to scroll vertically.
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // Link "linearLayoutManager" with RecyclerView.
        binding.rvNumbers.setLayoutManager(linearLayoutManager);

        /*
         * This allows RecyclerView to optimize its UI such that it avoids INVALIDATING the whole
         * layout when contents in the Adapter with which it is linked with changes.
         *
         * Set this to "true" when you know that number of items in the Adapter will remain the
         * same.
         */
        binding.rvNumbers.setHasFixedSize(true);

        // Link "GreenAdapter" with RecyclerView.
        binding.rvNumbers.setAdapter(new RAdapter(100, this));
    }

    @Override
    public void onListItemClick(int position) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, getString(R.string.toast_index, position),
                Toast.LENGTH_SHORT);
        mToast.show();
    }
}