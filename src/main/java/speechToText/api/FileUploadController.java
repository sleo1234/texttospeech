package speechToText.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import speechToText.api.security.AuthRequest;
import speechToText.api.security.User;
import speechToText.api.security.UserService;




@RestController
public class FileUploadController {
	
	
	  @Autowired UserService userService;
	
	@PostMapping("/auth/api/")
	
	public ResponseEntity<FileUploadResponse> uploadFile (@RequestParam ("file") MultipartFile multipartFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();
		System.out.println("]]]]]]]]]]" + size);
		FileUploadUtil.saveFile(fileName, multipartFile);
		FileUploadResponse response = new FileUploadResponse();
		
		
		String path = "C:\\Users\\Leo\\eclipse-workspace\\speech\\text-files\\";
		File file = new File(path+fileName);
		
		
		
		
		TextToSpeech txt2sp = new TextToSpeech();
		txt2sp.speak(file, "audio_file",path);
		response.setFileName("audio" + LocalDateTime.now());
		txt2sp.getAudioPlay(path, "audio_file.wav");
		
		response.setSize(size);
		return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
		
	}
	
	@PostMapping("auth/api_text")
	public ResponseEntity<FileUploadResponse> sendText(@RequestBody BodyText text) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		FileUploadResponse response = new FileUploadResponse();
		TextToSpeech txt2sp = new TextToSpeech();
		String path = "C:\\Users\\Leo\\eclipse-workspace\\speech\\text-files\\";
		
		txt2sp.speak(text.getText(), "audio_file",path);
		txt2sp.getAudioPlay(path, "audio_file.wav");
		System.out.println("======================" +text.toString());
		response.setFileName("audio" ); 
		
		
		return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("auth/create_account")
	
	public String createAccount(@RequestBody AuthRequest request){
		

		User user = new User(request.getEmail(), request.getPassword());
		
		userService.saveUser(user);
		return "Account has been created. Login to generate your token.";
	}
	
}
