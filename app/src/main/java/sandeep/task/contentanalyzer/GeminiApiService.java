package sandeep.task.contentanalyzer;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class GeminiApiService {

    private static final String TAG = "GeminiApiService";
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "Your Api KEY";

    public void analyzeContent(String extractedText, Context context, OnAnalysisReceivedListener listener) {
        try {
            JSONObject requestBody = new JSONObject();
            JSONArray contentsArray = new JSONArray();
            JSONObject partsObject = new JSONObject();

            partsObject.put("text", "Response is in only Markdown and beautiful dont give me orignal Content and Arrange the Markdown for Mobile View" +
                    "This is a content and analyze content and suggests engagement improvements and in first show a suggested result in beautiful way in markdown  and Categriosed in used Hastag if avaliavle sugesses which hashtag used and categriosed show like Engament in other and everything" +
                    "  HEre is my content : "+extractedText);
            Log.d(TAG, "Adding text to request: " + extractedText);
            JSONObject contentsObject = new JSONObject();
            contentsObject.put("parts", new JSONArray().put(partsObject));
            contentsArray.put(contentsObject);
            requestBody.put("contents", contentsArray);
            Log.d(TAG, "Request body: " + requestBody.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, GEMINI_API_URL + "?key=" + API_KEY, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "Response from Gemini API: " + response.toString());
                            try {
                                JSONArray candidates = response.getJSONArray("candidates");
                                StringBuilder extractedText = new StringBuilder();

                                for (int i = 0; i < candidates.length(); i++) {
                                    JSONObject candidate = candidates.getJSONObject(i);
                                    JSONObject contentObject = candidate.getJSONObject("content");
                                    JSONArray partsArray = contentObject.getJSONArray("parts");

                                    for (int j = 0; j < partsArray.length(); j++) {
                                        JSONObject partObject = partsArray.getJSONObject(j);
                                        String text = partObject.optString("text", "");
                                        extractedText.append(text).append("\n");
                                    }
                                }

                                String finalExtractedText = extractedText.toString().trim();
                                Log.d(TAG, "Extracted Text: " + finalExtractedText);

                                // Pass only the extracted text to the listener
                                listener.onAnalysisReceived(finalExtractedText);

                            }
                            catch (Exception e) {
                                Log.e(TAG, "Error processing response: " + e.getMessage(), e);
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                String responseBody = new String(error.networkResponse.data);
                                Log.e(TAG, "Error Response Body: " + responseBody);
                                Log.e(TAG, "Error Status Code: " + error.networkResponse.statusCode);
                            } else {
                                Log.e(TAG, "Error analyzing content: " + error.getMessage());
                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    Log.d(TAG, "Request Headers: " + headers.toString());
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Log.d(TAG, "Network Response Status Code: " + response.statusCode);
                    Log.d(TAG, "Network Response Headers: " + response.headers.toString());
                    return super.parseNetworkResponse(response);
                }
            };

            jsonObjectRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                    10000,
                    com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Log.d(TAG, "Retry policy set with timeout: 10000ms");

            Volley.newRequestQueue(context).add(jsonObjectRequest);
            Log.d(TAG, "Request added to queue.");

        } catch (Exception e) {
            Log.e(TAG, "Error creating request body: " + e.getMessage(), e);
        }
    }

    public interface OnAnalysisReceivedListener {
        void onAnalysisReceived(String analysisResult);
    }
}
