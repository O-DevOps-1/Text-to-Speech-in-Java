import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams.Voice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextToSpeech {
  public static void main(String[] args) throws IOException {

    // Configuración de voz
    VoiceSelectionParams voice =
        VoiceSelectionParams.newBuilder()
           .setLanguageCode("es-US") // Idioma español de USA
           .setName("es-US-Wavenet-A") // voz fmenina de ejemplo
           .setSsmlGender(SsmlVoiceGender.FEMALE)
           .build();

    // Configuración del audio
    AudioConfig audioConfig =
        AudioConfig.newBuilder()
           .setAudioEncoding(AudioEncoding.LINEAR16) // su codigicación (necesario)
           .setSampleRateHertz(24000) // frecuencia de muestreo
           .build();

    String text = "Hola, soy un programa de texto a voz en Java. Javalín javalón.";

    try (TextToSpeechClient client = TextToSpeechClient.create()) {
      // crea una solicitud de síntesis
      SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

      // realizar la síntesis!
      List<VoiceSelectionParams> voices = new ArrayList<>();
      voices.add(voice);
      byte[] audioBytes = client.synthesize(input, voice, audioConfig);

      //este último paso el audio se guarda en un archivo
      String audioFile = "output.wav";
      File file = new File(audioFile);
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(audioBytes);
      fos.close();
      System.out.println("Audio guardado en " + audioFile);}}}