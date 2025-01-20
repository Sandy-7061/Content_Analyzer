package sandeep.task.contentanalyzer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {
    private AlertDialog loadingDialog;

    private final ActivityResultLauncher<String> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), this::handleSelectedFile);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout pickFileButton = findViewById(R.id.pickFileButton);
        pickFileButton.setOnClickListener(v -> filePickerLauncher.launch("*/*"));
    }

    private void handleSelectedFile(Uri uri) {
        if (uri == null) {
            showDialog("No File Selected", "Please select a file to proceed.");
            return;
        }

        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null) {
            if (mimeType.startsWith("image/")) {
                new ImageProcessor(this).processImageFile(uri, this::onTextExtracted);
            } else if (mimeType.equals("application/pdf")) {
                new PdfProcessor(this).processPdfFile(uri, this::onTextExtracted);
            } else {
                showDialog("Unsupported File Type", "The selected file type is not supported. Please select an image or PDF file.");
            }
        } else {
            showDialog("File Type Not Recognized", "The type of the selected file could not be determined. Please try again.");
        }
    }

    private void onTextExtracted(String extractedText) {
        if (!extractedText.isEmpty()) {
            // Show loading animation
            showLoadingDialog();
            // Analyze content
            new GeminiApiService().analyzeContent(extractedText, this, this::onAnalysisReceived);
        } else {
            // Show a dialog for empty text
            showNoTextDialog();
        }
    }

    private void onAnalysisReceived(String analysisResult) {
        // Hide loading animation
        dismissLoadingDialog();

        // Start ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("ANALYSIS_RESULT", analysisResult);
        startActivity(intent);
    }


    private void showNoTextDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Text Found")
                .setIcon(R.mipmap.icon_round)
                .setMessage("There is no text to analyze. Please try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setIcon(R.mipmap.icon_round)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showLoadingDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);

        // Initialize Lottie animation
        LottieAnimationView lottieLoading = dialogView.findViewById(R.id.lottieLoading);
        TextView pleaseWaitText = dialogView.findViewById(R.id.pleaseWaitText);

        // You can modify the text dynamically if needed
        pleaseWaitText.setText("Please wait...");

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);  // Disable outside touch to dismiss the dialog
        loadingDialog = builder.create();

        // Show the dialog
        loadingDialog.show();
    }

    // Method to dismiss the loading dialog
    private void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
