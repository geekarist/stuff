package stuff.geekarist.github.com.stuff;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An activity representing a single Product detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ProductListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ProductDetailFragment}.
 */
public class ProductDetailActivity extends AppCompatActivity {

    private boolean mEditingInformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditingInformations) {
                    TextView detailView = (TextView) findViewById(R.id.product_detail);
                    detailView.setVisibility(View.GONE);
                    EditText detailEdit = (EditText) findViewById(R.id.product_detail_edit);
                    detailEdit.setVisibility(View.VISIBLE);
                    detailEdit.setText(detailView.getText());
                    fab.setImageResource(R.drawable.ic_save_white_24dp);
                } else {
                    TextView detailView = (TextView) findViewById(R.id.product_detail);
                    detailView.setVisibility(View.VISIBLE);
                    EditText detailEdit = (EditText) findViewById(R.id.product_detail_edit);
                    detailEdit.setVisibility(View.GONE);
                    detailView.setText(detailEdit.getText());
                    fab.setImageResource(R.drawable.ic_mode_edit_white_24dp);
                }
                mEditingInformations = !mEditingInformations;
            }
        });

        // Show the Up button in the action bar.
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ProductDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ProductDetailFragment.ARG_ITEM_ID));
            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ProductListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
