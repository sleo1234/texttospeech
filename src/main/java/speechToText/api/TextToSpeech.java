package speechToText.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat.Type;

import org.springframework.stereotype.Component;

import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

@Component
public class TextToSpeech {

	public void speak(Object obj, String filename, String path) {
		String message = null;
		if (obj instanceof String) {
			message  = (String) obj;
		} 
		
		else {
     	message = getTextFromFile((File) obj);
		}
		FreeTTS freeTts;
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		String voiceName = "kevin16";

		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice voice = voiceManager.getVoice(voiceName);
		SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer(path + filename,
				Type.WAVE);
    System.out.println("--------------" +audioPlayer.getVolume());
		if (voice == null) {
			System.err.println("Cannot find a voice named " + voiceName + ".  Please specify a different voice.");
			System.exit(1);
		}
		voice.allocate();

		voice.setAudioPlayer(audioPlayer);
		voice.speak(message);
		voice.deallocate();
		audioPlayer.close();
		voice.setWaveDumpFile(filename);
	}
	
	public String getTextFromFile(File file) {
		String message=new String();
		 try {
		      
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        message = message+data;
		        
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 return message;
	}
	
}
