package pack.wolf.com.pifi.util;

import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class BluetoothUtil {

    public static String getBlueToothAddress(Context context) {
        BluetoothManager ba=(BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        return ba.getAdapter().getAddress();
    }
}
