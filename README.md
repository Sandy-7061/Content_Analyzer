# Content Analyzer App

## Overview
The Content Analyzer App addresses the challenge of extracting and analyzing text from PDF and image files in a user-friendly manner. It is built to streamline the workflow of turning raw files into actionable insights. By leveraging Google’s ML Kit for precise text recognition and the Gemini API for content analysis, the app ensures that users receive highly accurate, Markdown-formatted recommendations and engagement tips. The design prioritizes efficiency, a polished user experience, and robust error handling, all enhanced with interactive animations for a modern and engaging feel.

---

## Features
- **Text Extraction:** Extracts text from images using Google ML Kit and from PDF files with iText.
- **Content Analysis:** Analyzes extracted text through the Gemini API and provides actionable insights in Markdown format.
- **Error Handling:** Gracefully handles unsupported file types and empty text scenarios.
- **Interactive Animations:** Enhances user experience with Lottie animations during processing.
- **Markdown Display:** Displays results beautifully formatted for mobile viewing.

---

## Setup Instructions
1. **Clone the Repository:**
   - Clone the repository to your local system using your preferred Git client. Use `git clone <repository-url>` in your terminal or Git Bash.

2. **Open in Android Studio:**
   - Launch Android Studio and import the project by selecting the project folder.

3. **Configure API Key:**
   - Open the `GeminiApiService` class in the `sandeep.task.contentanalyzer` package.
   - Locate the line `private static final String API_KEY = "Your Api KEY";`.
   - Replace `Your Api KEY` with your actual API key obtained from the Gemini API platform.
     - [Sign Up for Gemini API](https://gemini-api.example.com/signup)
     - [API Documentation](https://gemini-api.example.com/docs)

4. **Permissions:**
   - Ensure the following permissions are added to the `AndroidManifest.xml` file:
     - Internet access: `<uses-permission android:name="android.permission.INTERNET" />`
     - File access: `<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />`

5. **Build and Run:**
   - Click on `Build > Rebuild Project` in Android Studio to ensure all dependencies are resolved.
   - Deploy the app to an Android device or emulator with file access capabilities.

---

## Approach
1. **Problem Identification:**
   - Users often struggle to extract and analyze text from various file formats in a mobile-friendly way.
   - The app bridges this gap by combining robust text extraction and insightful content analysis.

2. **Technology Stack:**
   - **Google ML Kit:** For accurate text recognition from images.
   - **iText Library:** For extracting text from PDF files.
   - **Gemini API:** For advanced content analysis and Markdown formatting.

3. **Error Handling:**
   - Unsupported file types trigger an informative dialog.
   - Empty text scenarios prompt users to select a valid file.

4. **User Experience:**
   - Interactive Lottie animations provide visual feedback during lengthy processes.
   - Results are displayed in an easy-to-read, mobile-optimized Markdown format.

---

## Download the App
- [Download APK](https://drive.google.com/file/d/1pcN9i8FPVtO-cgMCZv8p2DHoeawfb9iB/view?usp=sharing)

---

## Contact
- **Name:** [Sandeep Kushwaha](mailto:sandeepkush880@gmail.com)
- **Email:** [sandeepkush880@gmail.com](mailto:sandeepkush880@gmail.com)
- **Mobile:** [7024520740](tel:+917024520740)

---

