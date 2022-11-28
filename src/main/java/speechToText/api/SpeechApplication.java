package speechToText.api;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SpeechApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeechApplication.class, args);

		
		//txt2sp.speak("soareeee", "audio_f");
	}

}
