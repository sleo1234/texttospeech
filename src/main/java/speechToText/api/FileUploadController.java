package speechToText.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;




@RestController
public class FileUploadController {
	
	@PostMapping("/api/")
	
	public ResponseEntity<FileUploadResponse> uploadFile (@RequestParam ("file") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();
		FileUploadUtil.saveFile(fileName, multipartFile);
		FileUploadResponse response = new FileUploadResponse();
		
		
		String path = "D:\\speechtotext\\speech\\text-files\\";
		File file = new File(path+fileName);
		
		
		HttpServletRequest request;
	
		
		TextToSpeech txt2sp = new TextToSpeech();
		txt2sp.speak(file, "audio_file","D:\\speechtotext\\speech\\text-files\\");
		response.setFileName("audio" + LocalDateTime.now()); 
		response.setSize(size);
		return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
		
	}
}
