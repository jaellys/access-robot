package ksmaragh.c4q.nyc.accessrobot;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComponentListActivity extends FragmentActivity
        implements View.OnTouchListener {

    @Bind(R.id.tv_parts_instructions)
    TextView tv_parts_instructions;
    @Bind(R.id.iv_mozi)
    ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_components);

        ButterKnife.bind(this);

        ImageView iv = (ImageView) findViewById(R.id.iv_mozi);
        if (iv != null) {
            iv.setOnTouchListener(this);
        }
    }

    /**
     * Respond to the user touching the screen.
     * Change images to make things appear and disappear from the screen.
     */

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;            // resource id of the next image to display

        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_mozi);
        if (imageView == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
        Integer tagNum = (Integer) imageView.getTag();
        int currentResource = (tagNum == null) ? R.drawable.mozi_parts : tagNum.intValue();

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (currentResource == R.drawable.mozi_parts) {
                    nextImage = R.drawable.mozi_parts_indicators;
                    handledHere = true;
       /*
       } else if (currentResource != R.drawable.mozi_parts) {
         nextImage = R.drawable.mozi_parts;
         handledHere = true;
       */
                } else handledHere = true;
                break;

            case MotionEvent.ACTION_UP:
                // On the UP, we do the click action.
                // The hidden image (image_areas) has three different hotspots on it.
                // Use image_areas to determine which region the user touched.
                int touchColor = getHotspotColor(R.id.iv_hotspots, evX, evY);

                // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
                // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
                // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
                // varying pixel density.
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                nextImage = R.drawable.mozi_parts;
                String partName = null;
                String partInfo = null;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
                    partName = getString(R.string.part_arduino_name);
                    partInfo = getString(R.string.part_arduino_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
                    partName = getString(R.string.part_ultrasonic_range_finder_name);
                    partInfo = getString(R.string.part_ultrasonic_range_finder_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)) {
                    partName = getString(R.string.part_leds_name);
                    partInfo = getString(R.string.part_leds_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.WHITE, touchColor, tolerance) ||
                        (ct.closeMatch(Color.CYAN, touchColor, tolerance))) {
                    partName = getString(R.string.part_breadboard_wires_name);
                    partInfo = getString(R.string.part_breadboard_wires_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance)) {
                    partName = getString(R.string.part_usb_otg_bt_name);
                    partInfo = getString(R.string.part_usb_otg_bt_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) {
                    partName = getString(R.string.part_mini_servos_name);
                    partInfo = getString(R.string.part_mini_servos_info);
                    showComponentDialog(partName, partInfo);
                } else if (ct.closeMatch(Color.LTGRAY, touchColor, tolerance)) {
                    partName = getString(R.string.part_resistors_name);
                    partInfo = getString(R.string.part_resistors_info);
                    showComponentDialog(partName, partInfo);
                } else {
                    // nothing
                }

                // If the next image is the same as the last image, go back to the default.
                // toast ("Current image: " + currentResource + " next: " + nextImage);
                if (currentResource == nextImage) {
                    nextImage = R.drawable.mozi_parts;
                }
                handledHere = true;
                break;

            default:
                handledHere = false;
        }

        if (handledHere) {

            if (nextImage > 0) {
                imageView.setImageResource(nextImage);
                imageView.setTag(nextImage);
            }
        }
        return handledHere;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Get the color from the hotspot image at point x-y.
     */

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Show the dialog for a component.
     */

    private void showComponentDialog(String partName, String partInfo) {
        FragmentManager fm = getSupportFragmentManager();
        ComponentDialog componentDialog = ComponentDialog.newInstance(partName, partInfo);
        componentDialog.show(this.getFragmentManager(), "PART");
    }
}