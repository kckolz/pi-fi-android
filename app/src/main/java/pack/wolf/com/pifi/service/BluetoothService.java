package pack.wolf.com.pifi.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import pack.wolf.com.pifi.util.ContextUtil;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class BluetoothService  extends Service {

    private static final UUID MY_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeAdvertiser mBluetoothLeAdvertiser;


    public static final ParcelUuid PIFI = ParcelUuid.fromString("00031809-0000-1000-8000-00805f9b34fb");


    private void startBlueTooth() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "ble not supported.", Toast.LENGTH_SHORT).show();
            ContextUtil.finish(this);
        }

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                // your implementation here
                Log.e("blah", "scan - " + device.getAddress());
            }
        };

    }

    private byte[] buildTempPacket() {
        int value;
        try {
            value = Integer.parseInt("blah.".toString());
        } catch (NumberFormatException e) {
            value = 0;
        }

        return new byte[] {(byte)value, 0x00};
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//
        Intent discoverableIntent = new
        Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        discoverableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(discoverableIntent);

        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
        enableBluetooth(true);
////
////        mBluetoothAdapter =((BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
////        if (mBluetoothAdapter == null) Log.e("onCreate", "mBluetoothAdapter is NULL!!!!!");
////
////        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
////            Log.d("onCreate", "BLE feature is NOT available");
////        } else {
////            Log.d("onCreate", "BLE feature is available");
////            startAdvertising();
////        }
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice("00:1A:7D:DA:71:13");
        BluetoothSocket tmp;
        try {
            tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e("Blah", "IO EXception creating connection.");
        }

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    public static void enableBluetooth(boolean enable){
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e("enableBluetooth", "Not switching bluetooth since its not present");
            return;
        }
        if (bluetoothAdapter.isEnabled() == enable) {
            Log.e("enableBluetooth", "BT is enabled");

            return;
        }
        if (enable) {
            bluetoothAdapter.enable();
        }
        else {
            bluetoothAdapter.disable();
        }
        Log.i("enableBluetooth", "Switched bluetooth to " + enable);
    }

    /**
     * Stop Advertisements
     */
    public void stopAdvertisements() {
        if (mBluetoothLeAdvertiser != null) {
        }
    }

}