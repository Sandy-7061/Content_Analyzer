package sandeep.task.contentanalyzer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.noties.markwon.Markwon;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize Views
        ImageButton backButton = findViewById(R.id.backButton);
        TextView analysisResultView = findViewById(R.id.analysisResult);
        ImageButton copyButton = findViewById(R.id.copyButton);

        // Get Data from Intent
        String analysisResult = getIntent().getStringExtra("ANALYSIS_RESULT");

        // Display the Analysis Result
        if (analysisResult != null) {
            Markwon markwon = Markwon.create(this);
            markwon.setMarkdown(analysisResultView, analysisResult);
        }

        // Handle Back Button Click
        backButton.setOnClickListener(v -> finish());

        // Handle Copy Button Click
        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Analysis Result", analysisResultView.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ResultActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();
        });
    }
}
