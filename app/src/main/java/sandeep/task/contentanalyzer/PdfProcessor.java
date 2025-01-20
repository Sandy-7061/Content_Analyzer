package sandeep.task.contentanalyzer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.InputStream;

public class PdfProcessor {

    private final Context context;

    public PdfProcessor(Context context) {
        this.context = context;
    }

    public void processPdfFile(Uri uri, OnTextExtractedListener listener) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            if (inputStream == null) {
                return;
            }

            PdfReader reader = new PdfReader(inputStream);
            PdfDocument pdfDocument = new PdfDocument(reader);

            StringBuilder extractedText = new StringBuilder();
            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                extractedText.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i))).append("\n");
            }

            pdfDocument.close();
            listener.onTextExtracted(extractedText.toString());
        } catch (Exception e) {
            Log.e("PdfProcessor", "Error processing PDF: " + e.getMessage(), e);
        }
    }

    public interface OnTextExtractedListener {
        void onTextExtracted(String text);
    }
}
