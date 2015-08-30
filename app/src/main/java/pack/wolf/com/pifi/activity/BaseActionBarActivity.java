package pack.wolf.com.pifi.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import pack.wolf.com.pifi.R;
import pack.wolf.com.pifi.application.AppConstants;
import pack.wolf.com.pifi.fragment.BaseFragment;
import pack.wolf.com.pifi.service.BluetoothService;
import pack.wolf.com.pifi.util.DialogUtil;

public class BaseActionBarActivity extends AppCompatActivity {

    private Context context;
    public static FragmentManager fragmentManager;
    public static ActionBar actionBar;
    public static BluetoothAdapter bluetoothAdapter;
    private Toolbar mToolbar;
    public static TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // set up toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.home));
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.home);

        // get context
        this.context = this;

        // get fragment manager
        fragmentManager = getSupportFragmentManager();

        // set up action bar
        ColorDrawable newColor = new ColorDrawable(getResources().getColor(R.color.black));
        newColor.setAlpha(128);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setStackedBackgroundDrawable(newColor);
        actionBar.setBackgroundDrawable(newColor);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setElevation(0);

        // set home
        fragmentManager.beginTransaction()
                .replace(R.id.container, BaseFragment.newInstance())
                .addToBackStack(AppConstants.FRAGMENT_BASE)
                .commit();

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if(bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
        {
            this.startService(new Intent(this, BluetoothService.class));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_search:
                startActivity(new Intent(context, UserSearchActivity.class));

                break;
            case R.id.action_logout:
                DialogUtil.logoutDialog(context, fragmentManager);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public static void setTitle(String title) {
        mTitle.setText(title);
    }

    public static void showTitleBar() {
        actionBar.show();
    }

    public static void hideTitleBar() {
        actionBar.hide();
    }

    public void setBackButtonVisibility(Boolean visibility) {

        actionBar.setDisplayHomeAsUpEnabled(visibility);

    }

    @Override
    public void onBackPressed(){

        showTitleBar();

        FragmentManager fm = this.getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {

            String fragName = String.valueOf(fm.getBackStackEntryAt(fm.getBackStackEntryCount()-2).getName());
            switch (fragName) {
                case AppConstants.FRAGMENT_BASE:
                    mTitle.setText(getString(R.string.home));
                    break;
                case AppConstants.FRAGMENT_SEARCH:
                    mTitle.setText(getString(R.string.search));
                    break;
                case AppConstants.FRAGMENT_SIGNIN:
                    mTitle.setText(getString(R.string.signin));
                    break;
                case AppConstants.FRAGMENT_SIGNUP:
                    mTitle.setText(getString(R.string.signup));
                    break;
                case AppConstants.FRAGMENT_SETTINGS:
                    mTitle.setText(getString(R.string.action_settings));
                    break;
            }
            fm.popBackStack();
        } else {
            finish();
        }

    }

    public static class FragmentOnClickListener implements View.OnClickListener
    {

        Fragment fragment;
        String fragmentName;

        public FragmentOnClickListener(String fragmentName, Fragment fragment) {
            this.fragment = fragment;
            this.fragmentName = fragmentName;
        }

        @Override
        public void onClick(View v)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragmentName)
                    .commit();
        }

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}