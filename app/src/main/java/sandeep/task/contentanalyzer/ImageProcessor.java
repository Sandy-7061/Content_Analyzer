package sandeep.task.contentanalyzer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ImageProcessor {

    private final Context context;

    public ImageProcessor(Context context) {
        this.context = context;
    }

    public void processImageFile(Uri uri, OnTextExtractedListener listener) {
        try {
            InputImage image = InputImage.fromFilePath(context, uri);
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            recognizer.process(image)
                    .addOnSuccessListener(result -> listener.onTextExtracted(result.getText()))
                    .addOnFailureListener(e -> Log.e("ImageProcessor", "Error processing image: " + e.getMessage(), e));
        } catch (Exception e) {
            Log.e("ImageProcessor", "Error initializing image processing: " + e.getMessage(), e);
        }
    }

    public interface OnTextExtractedListener {
        void onTextExtracted(String text);
    }
}
