package ksmaragh.c4q.nyc.accessrobot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialIconView;

import at.markushi.ui.CircleButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = "BluetoothFragment";
    private final BluetoothHandler mHandler = new BluetoothHandler(this);


    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private String mConnectedDeviceName = null;
    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothArduinoService mChatService = null;

    @Bind(R.id.btn_connect)
    CircleButton btnConnect;
    @Bind(R.id.btn_move_backward)
    MaterialIconView btnMoveBackward;
    @Bind(R.id.btn_move_forward)
    MaterialIconView btnMoveForward;
    @Bind(R.id.btn_move_left)
    MaterialIconView btnMoveLeft;
    @Bind(R.id.btn_move_right)
    MaterialIconView btnMoveRight;
    @Bind(R.id.btn_light_left)
    MaterialIconView btnLightLeft;
    @Bind(R.id.btn_light_right)
    MaterialIconView btnLightRight;
    @Bind(R.id.btn_look_cute)
    MaterialIconView btnLookCute;
    @Bind(R.id.btn_stop)
    MaterialIconView btnStop;

    public TourGuide tutorialHandler;
    Animation enterAnimation;
    Animation exitAnimation;

    boolean tutorialMode = true; // TODO: SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ButterKnife.bind(this);

        // On first run, launch the tutorial.
        setupEnterExitAnis();
        initTourGuide();
        initBluetooth();
        tutorialHandler.playOn(btnConnect);
    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
    }

    private void initTourGuide() {
        // initialize TourGuide without playOn()
        tutorialHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip()
                        .setTitle("Connect!")
                        .setDescription("This lets the robot to listen to commands.")
                        .setBackgroundColor(Color.parseColor("#78909C"))
                        .setGravity(Gravity.CENTER_HORIZONTAL))
                .setOverlay(new Overlay().setEnterAnimation(enterAnimation).setExitAnimation(exitAnimation));
    }

    private void setupEnterExitAnis() {
        enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);
    }

    @OnClick(R.id.btn_connect)
    public void connectRobot() {
        if (tutorialMode) {
            tutorialHandler.cleanUp();
            tutorialHandler.setToolTip(new ToolTip()
                    .setTitle("Move forward!")
                    .setDescription("You can also move left, right, and backward.")
                    .setBackgroundColor(Color.parseColor("#78909C"))
                    .setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL))
                    .playOn(btnMoveForward);
        } else {
            Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
        }
    }

    @OnClick(R.id.btn_move_backward)
    public void moveBackward() {
        Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
        sendMessage("b");
    }

    @OnClick(R.id.btn_move_forward)
    public void moveForward() {
        if (tutorialMode) {
            tutorialHandler.cleanUp();
            tutorialHandler.setToolTip(new ToolTip()
                    .setTitle("Stop!")
                    .setDescription("Tap here to stop moving.")
                    .setBackgroundColor(Color.parseColor("#78909C"))
                    .setGravity(Gravity.TOP | Gravity.LEFT))
                    .playOn(btnStop);
        } else {
            Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
            sendMessage("f");
        }
    }

    @OnClick(R.id.btn_move_left)
    public void moveLeft() {
        Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
        sendMessage("l");
    }

    @OnClick(R.id.btn_move_right)
    public void moveRight() {
        Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
        sendMessage("r");
    }

    @OnClick(R.id.btn_light_left)
    public void lightLeft() {
        if (tutorialMode) {
            tutorialHandler.cleanUp();
            tutorialHandler.setToolTip(new ToolTip()
                    .setTitle("Look cute!")
                    .setDescription("?!")
                    .setBackgroundColor(Color.parseColor("#78909C"))
                    .setGravity(Gravity.TOP | Gravity.RIGHT))
                    .playOn(btnLookCute);
        } else {
            Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
            sendMessage("q");
        }
    }

    @OnClick(R.id.btn_light_right)
    public void lightRight() {
        Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
        sendMessage("p");
    }

    @OnClick(R.id.btn_look_cute)
    public void lookCute() {
        if (tutorialMode) {
            tutorialHandler.cleanUp();
            tutorialMode = false;
        } else {
            Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
            sendMessage("u");
        }
    }

    @OnClick(R.id.btn_stop)
    public void stopMoving() {
        if (tutorialMode) {
            tutorialHandler.cleanUp();
            tutorialHandler.setToolTip(new ToolTip()
                    .setTitle("Light it up!")
                    .setDescription("You can blink left and right LEDs.")
                    .setBackgroundColor(Color.parseColor("#78909C"))
                    .setGravity(Gravity.TOP | Gravity.RIGHT))
                    .playOn(btnLightLeft);
        } else {
            Toast.makeText(this, "Beep boop!", Toast.LENGTH_SHORT).show();
            sendMessage("x");
        }
    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothArduinoService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothArduinoService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {

    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {

    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private class BluetoothHandler extends Handler {

        private final DemoActivity mActivity;

        public BluetoothHandler(DemoActivity activity) {
            mActivity = activity;
        }


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothArduinoService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));

                            break;
                        case BluetoothArduinoService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothArduinoService.STATE_LISTEN:
                        case BluetoothArduinoService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
//                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != mActivity) {
                        Toast.makeText(mActivity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != mActivity) {
                        Toast.makeText(mActivity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    ;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else if (mChatService == null) {
            setupChat();
        }
    }

    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothArduinoService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
            case R.id.blockly: {
                Intent blocklyIntent = new Intent(this, MoziActivity.class);
                startActivity(blocklyIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}