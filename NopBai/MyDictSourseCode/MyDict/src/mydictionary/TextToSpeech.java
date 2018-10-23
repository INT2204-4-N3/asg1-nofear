package mydictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {

    static VoiceManager voiceManager;
    static Voice voice;

    static void speak(String text) {
        System.setProperty("mbrola.base", ".\\mbrola");
        voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("mbrola_us1");
//        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
    }

    public static void main(String[] args) {
        String speekstring = "I love you";
        speak(speekstring);
    }

}
