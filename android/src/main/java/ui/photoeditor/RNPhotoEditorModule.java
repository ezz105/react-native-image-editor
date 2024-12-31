package ui.photoeditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import com.ahmedadeltito.photoeditor.PhotoEditorActivity;
import com.ahmedadeltito.photoeditor.TranslationService;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;

public class RNPhotoEditorModule extends ReactContextBaseJavaModule {

    private static final int PHOTO_EDITOR_REQUEST = 1;
    private static final String E_PHOTO_EDITOR_CANCELLED = "E_PHOTO_EDITOR_CANCELLED";

    private Callback mDoneCallback;
    private Callback mCancelCallback;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
    public void onActivityResult(
        Activity activity,
        int requestCode,
        int resultCode,
        Intent intent
    ) {
        if (requestCode == PHOTO_EDITOR_REQUEST) {
            if (mDoneCallback != null) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    mCancelCallback.invoke(resultCode);
                } else {
                    // Retrieve the list of image paths from the intent
                    ArrayList<String> imagePaths = intent.getExtras().getStringArrayList("imagePaths");

                    if (imagePaths != null) {
                        // Process each image path
                        for (String imagePath : imagePaths) {
                            // Perform any processing needed on each image
                            // For example, you could add the processed path to a new list
                        }

                        // Invoke the callback with the list of processed image paths
                        mDoneCallback.invoke(imagePaths);
                    } else {
                        // Handle the case where no images were returned
                        mDoneCallback.invoke(new ArrayList<String>());
                    }
                }
            }

            mCancelCallback = null;
            mDoneCallback = null;
        }
    }

    public RNPhotoEditorModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "RNPhotoEditor";
    }

    @ReactMethod
    public void Edit(final ReadableMap props, final Callback onDone, final Callback onCancel) {
        // Retrieve the list of image paths from props
    ReadableArray pathsArray = props.getArray("paths");
    ArrayList<String> imagePaths = new ArrayList<>();

    for (int i = 0; i < pathsArray.size(); i++) {
        imagePaths.add(pathsArray.getString(i));
    }

    // The rest of the method remains unchanged


        // print all readable map
        TranslationService.getInstance().init(props.getMap("languages").toHashMap());

        //Process Stickers
        ReadableArray stickers = props.getArray("stickers");
        ArrayList<Integer> stickersIntent = new ArrayList<Integer>();

        for (int i = 0; i < stickers.size(); i++) {
            int drawableId = getReactApplicationContext()
                .getResources()
                .getIdentifier(
                    stickers.getString(i),
                    "drawable",
                    getReactApplicationContext().getPackageName()
                );

            stickersIntent.add(drawableId);
        }

        //Process Hidden Controls
        ReadableArray hiddenControls = props.getArray("hiddenControls");
        ArrayList hiddenControlsIntent = new ArrayList<>();

        for (int i = 0; i < hiddenControls.size(); i++) {
            hiddenControlsIntent.add(hiddenControls.getString(i));
        }

        //Process Colors
        ReadableArray colors = props.getArray("colors");
        ArrayList colorPickerColors = new ArrayList<>();

        for (int i = 0; i < colors.size(); i++) {
            colorPickerColors.add(Color.parseColor(colors.getString(i)));
        }

        Intent intent = new Intent(getCurrentActivity(), PhotoEditorActivity.class);
        intent.putStringArrayListExtra("imagePaths", imagePaths);
        intent.putExtra("colorPickerColors", colorPickerColors);
        intent.putExtra("hiddenControls", hiddenControlsIntent);
        intent.putExtra("stickers", stickersIntent);

        mCancelCallback = onCancel;
        mDoneCallback = onDone;

        getCurrentActivity().startActivityForResult(intent, PHOTO_EDITOR_REQUEST);
    }
}
