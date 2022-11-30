package speechToText.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;




@RestController
public class FileUploadController {
	
	@PostMapping("/auth/api/")
	
	public ResponseEntity<FileUploadResponse> uploadFile (@RequestParam ("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();
		FileUploadUtil.saveFile(fileName, multipartFile);
		FileUploadResponse response = new FileUploadResponse();
		
		
		String path = "C:\\Users\\Leo\\eclipse-workspace\\speech\\text-files\\";
		File file = new File(path+fileName);
		
		
		
		
		TextToSpeech txt2sp = new TextToSpeech();
		txt2sp.speak(file, "audio_file",path);
		response.setFileName("audio" + LocalDateTime.now()); 
		response.setSize(size);
		return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
		
	}
	
	@PostMapping("auth/api_text")
	public ResponseEntity<FileUploadResponse> sendText(@RequestBody BodyText text){
		
		FileUploadResponse response = new FileUploadResponse();
		TextToSpeech txt2sp = new TextToSpeech();
		String path = "C:\\Users\\Leo\\eclipse-workspace\\speech\\text-files\\";
		txt2sp.speak(text.getText().toString(), "audio_file",path);
		System.out.println("======================" +text.toString());
		response.setFileName("audio" + LocalDateTime.now()); 
		
		
		return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
	}
}
