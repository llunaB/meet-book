package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ssafy.api.responseDto.GetConferencesRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.ConferenceHistoryDTO;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.db.entity.User;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;

@RestController
@RequestMapping("/conference")
public class ConferenceController {
	
	private ConferenceService conferenceService;
	private OpenVidu openVidu;
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
	
	private String OPENVIDU_URL;
	private String SECRET;
	
	@Autowired
	public ConferenceController(ConferenceService conferenceService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.conferenceService = conferenceService;
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> createConference(@RequestBody ConferenceDTO conferenceDto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			conferenceService.createConference(conferenceDto);
			map.put("message", "회의 생성 성공");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "회의 생성  실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/list/{pageno}")
	public ResponseEntity<List<ConferenceDTO>> getConferences(@PathVariable("pageno") String pageno){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			list = conferenceService.getConferences();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConferenceDTO> getConferenceById(@PathVariable("id") String id){
		ConferenceDTO response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<ConferenceDTO>(response, HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateConference(@PathVariable("id") String id, @RequestBody ConferenceDTO conferenceDto){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			conferenceService.updateConference(conferenceDto);
			map.put("message", "회의 수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "회의 수정 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteConference(@PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			conferenceService.deleteConference(Integer.parseInt(id));
			map.put("message", "회의 삭제 성공");
		}catch(Exception e){
			map.put("message", "회의 수정 실패");
		}
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<ConferenceDTO> getConferenceDetail(@PathVariable("id") String id){
		ConferenceDTO response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ConferenceDTO>(response, HttpStatus.OK);
	}
	
	/*******************/
	/*** Session API ***/
	/*******************/
	
	@GetMapping("/{id}/live")
	public ResponseEntity<String> isSessionLive(@PathVariable("id") String id){
		
		if (this.mapSessions.get(id) == null) {
			return new ResponseEntity<>("false", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("true", HttpStatus.OK);
	}
	
	@GetMapping("/{id}/token")
	public ResponseEntity<String> getToken(@PathVariable("id") String id, @AuthenticationPrincipal final User user) {

		System.out.println("Getting sessionId and token | {sessionName}=" + id);

		// Role associated to this user
		OpenViduRole role = OpenViduRole.PUBLISHER;
		
		//Checking valid of conference.
		ConferenceDTO target = conferenceService.getConferenceById(Integer.parseInt(id));
		if(target == null) {
			return new ResponseEntity<>("conference not found", HttpStatus.NOT_FOUND);
		}
		
		//Give moderator role  if user make this conference.
		if(target.getUserId() == user.getId()) {
			role = OpenViduRole.MODERATOR;
		}
		
		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data("user_data").build();

		JsonObject responseJson = new JsonObject();

		if (this.mapSessions.get(id) != null) {
			// Session already exists
			System.out.println("Existing session " + id);
			try {

				// Generate a new token with the recently created connectionProperties
				String token = this.mapSessions.get(id).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionNamesTokens.get(id).put(token, role);
				conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "JOIN"));
				// Prepare the response with the token
				responseJson.addProperty("token", token);

				// Return the response to the client
				return new ResponseEntity<>(token, HttpStatus.OK);

			} catch (OpenViduJavaClientException e1) {
				// If internal error generate an error message and return it to client
				e1.printStackTrace();
				return new ResponseEntity<>(e1.toString(), HttpStatus.OK);
			} catch (OpenViduHttpException e2) {
				if (404 == e2.getStatus()) {
					// Invalid sessionId (user left unexpectedly). Session object is not valid
					// anymore. Clean collections and continue as new session
					this.mapSessions.remove(id);
					this.mapSessionNamesTokens.remove(id);
				}
			}
		}

		// New session
		System.out.println("New session " + id);
		try {

			// Create a new OpenVidu Session
			Session session = this.openVidu.createSession();
			// Generate a new token with the recently created connectionProperties
			String token = session.createConnection(connectionProperties).getToken();

			// Store the session and the token in our collections
			this.mapSessions.put(id, session);
			conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "CREATE"));

			this.mapSessionNamesTokens.put(id, new ConcurrentHashMap<>());
			this.mapSessionNamesTokens.get(id).put(token, role);
			conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "JOIN"));


			// Prepare the response with the sessionId and the token
			responseJson.addProperty("token", token);

			// Return the response to the client
			return new ResponseEntity<>(token, HttpStatus.OK);

		} catch (Exception e) {
			// If error generate an error message and return it to client
			return new ResponseEntity<>(e.toString(), HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}/leave")
	public ResponseEntity<JsonObject> deleteUser(@PathVariable("id") String id , @RequestBody String token, @AuthenticationPrincipal final User user) throws Exception {

		System.out.println("Removing user | {sessionName, token}=" + id+", "+token);

		// If the session exists
		if (this.mapSessions.get(id) != null && this.mapSessionNamesTokens.get(id) != null) {

			// If the token exists
			if (this.mapSessionNamesTokens.get(id).remove(token) != null) {
				// User left the session
				conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "EXIT"));

				if (this.mapSessionNamesTokens.get(id).isEmpty()) {
					// Last user left: session must be removed
					this.mapSessions.remove(id);
					conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "CLOSE"));
				}
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The TOKEN wasn't valid
				System.out.println("Problems in the app server: the TOKEN wasn't valid");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}/close")
	public ResponseEntity<JsonObject> deleteSession(@PathVariable("id") String id, @AuthenticationPrincipal final User user) throws Exception {

		System.out.println("Closing session | {sessionName}=" + id);

		// If the session exists
		if (this.mapSessions.get(id) != null && this.mapSessionNamesTokens.get(id) != null) {
			Session s = this.mapSessions.get(id);
			s.close();
			this.mapSessions.remove(id);
			this.mapSessionNamesTokens.remove(id);
			conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "CLOSE"));

			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/force-disconnect", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceDisconnect(@RequestBody Map<String, Object> params) {
		try {
			// Retrieve the param from BODY
			String session = (String) params.get("sessionName");
			String connectionId = (String) params.get("connectionId");

			// If the session exists
			if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
				Session s = this.mapSessions.get(session);
				s.forceDisconnect(connectionId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The SESSION does not exist
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}

	@RequestMapping(value = "/force-unpublish", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceUnpublish(@RequestBody Map<String, Object> params) {
		try {
			// Retrieve the param from BODY
			String session = (String) params.get("sessionName");
			String streamId = (String) params.get("streamId");

			// If the session exists
			if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
				Session s = this.mapSessions.get(session);
				s.forceUnpublish(streamId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The SESSION does not exist
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}
	
	private ResponseEntity<JsonObject> getErrorResponse(Exception e) {
		JsonObject json = new JsonObject();
		json.addProperty("cause", e.getCause().toString());
		json.addProperty("error", e.getMessage());
		json.addProperty("exception", e.getClass().getCanonicalName());
		return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
