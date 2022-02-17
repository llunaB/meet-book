package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.ConferenceHistoryDTO;
import com.ssafy.api.requestDto.conference.ForceDisconnectReq;
import com.ssafy.api.requestDto.conference.ForceUnpublishReq;
import com.ssafy.api.requestDto.conference.LeaveConferenceReq;
import com.ssafy.api.responseDto.GetConferencesRes;
import com.ssafy.api.responseDto.GetUserByProfileRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.SessionService;
import com.ssafy.db.entity.User;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;

@Slf4j
@RestController
@RequestMapping("/conference")
public class ConferenceController {
	
	private ConferenceService conferenceService;
	private OpenVidu openVidu;
	private SessionService sessionService;
	
	private String OPENVIDU_URL;
	private String SECRET;
	
	@Autowired
	public ConferenceController(ConferenceService conferenceService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl, SessionService sessionService) {
		this.conferenceService = conferenceService;
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
		this.sessionService = sessionService;
	}
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> createConference(@RequestBody ConferenceDTO conferenceDto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if(conferenceService.createConference(conferenceDto)) {
				map.put("message", "회의 생성 성공");
			}else {
				map.put("message", "회의 생성  실패");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "회의 생성  실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Page<GetConferencesRes>> getConferences(@RequestParam("size") Integer size, @RequestParam("page") Integer page, @AuthenticationPrincipal User userEntity){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		
		try {
			list = conferenceService.getConferences(request, userEntity);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bookmarked")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByUserId(@RequestParam("size") Integer size, @RequestParam("page") Integer page,  @RequestParam("id") Integer userId){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		
		try {
			list = conferenceService.getConfrerencesByBookmarkUserId(userId, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}


	
	@GetMapping("/{id}")
	public ResponseEntity<GetConferencesRes> getConferenceById(@PathVariable("id") String id){
		GetConferencesRes response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<GetConferencesRes>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/list")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesById(@PathVariable("id") String id, @AuthenticationPrincipal User userEntity){
		Page<GetConferencesRes> response = Page.empty();
		PageRequest request =PageRequest.of(0, 5); //검색을 원하는 페이지, 개수
		
		try {
			response = conferenceService.getConferencesById(Integer.parseInt(id), userEntity, request);
			new ResponseEntity<Page<GetConferencesRes>>(response, HttpStatus.OK);
		}catch (Exception e){
			e.printStackTrace();
			log.info("회의목록 에러");
		}
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}/bookmark")
	public ResponseEntity<List<GetUserByProfileRes>> getUserById(@PathVariable("id") String id){
		List<GetUserByProfileRes> response = new ArrayList<GetUserByProfileRes>();
		
		try {
			response = conferenceService.getUsersByBookmarkConference(Integer.parseInt(id));
			new ResponseEntity<List<GetUserByProfileRes>>(response, HttpStatus.OK);
		}catch (Exception e){
			e.printStackTrace();
			log.info("회의목록 에러");
		}
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<GetConferencesRes> getConferenceDetail(@PathVariable("id") String id){
		GetConferencesRes response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<GetConferencesRes>(response, HttpStatus.OK);
	}
	
	/*******************/
	/*** Session API ***/
	/*******************/
	
	@GetMapping("/{id}/live")
	public ResponseEntity<String> isSessionLive(@PathVariable("id") String id){
		
		if (sessionService.getMapSessions().get(id) == null) {
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
		GetConferencesRes target = null;
		try {
			target = conferenceService.getConferenceById(Integer.parseInt(id));
			
			if(target == null) {
				return new ResponseEntity<>("conference not found", HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("conference not found", HttpStatus.NOT_FOUND);
		}
		
		
		
		//Give moderator role  if user make this conference.
		if(target.getUser().getId() == user.getId()) {
			role = OpenViduRole.MODERATOR;
		}
		
		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data("user_data").build();

		if (sessionService.getMapSessions().get(id) != null) {
			// Session already exists
			System.out.println("Existing session " + id);
			try {

				// Generate a new token with the recently created connectionProperties
				String token = sessionService.getMapSessions().get(id).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				sessionService.getMapSessionNamesTokens().get(id).put(token, role);
				sessionService.getMapSessionNamesUsers().get(id).put(token, user.getId());
				conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "JOIN"));

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
					sessionService.getMapSessions().remove(id);
					sessionService.getMapSessionNamesTokens().remove(id);
					sessionService.getMapSessionNamesUsers().remove(id);
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
			sessionService.getMapSessions().put(id, session);
			conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "CREATE"));

			sessionService.getMapSessionNamesTokens().put(id, new ConcurrentHashMap<>());
			sessionService.getMapSessionNamesTokens().get(id).put(token, role);

			sessionService.getMapSessionNamesUsers().put(id, new ConcurrentHashMap<>());
			sessionService.getMapSessionNamesUsers().get(id).put(token, user.getId());
			conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), user.getId(), "JOIN"));

			// Return the response to the client
			return new ResponseEntity<>(token, HttpStatus.OK);

		} catch (Exception e) {
			// If error generate an error message and return it to client
			return new ResponseEntity<>(e.toString(), HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}/leave")
	public ResponseEntity<JsonObject> deleteUser(@PathVariable("id") String id , @RequestBody LeaveConferenceReq req) throws Exception {
		String token = req.getToken();
		System.out.println("Removing user | {sessionName, token}=" + id+", "+token);
		
		// If the session exists
		if (sessionService.getMapSessions().get(id) != null && sessionService.getMapSessionNamesTokens().get(id) != null) {
			int userId = sessionService.getMapSessionNamesUsers().get(id).get(token);
			
			
			// If the token exists
			if (sessionService.getMapSessionNamesTokens().get(id).remove(token) != null) {
				// User left the session

				sessionService.getMapSessionNamesUsers().get(id).remove(token);
				conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), userId, "EXIT"));

				if (sessionService.getMapSessionNamesTokens().get(id).isEmpty()) {
					// Last user left: session must be removed
					sessionService.getMapSessions().remove(id);
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
	public ResponseEntity<JsonObject> deleteSession(@PathVariable("id") String id) throws Exception {

		System.out.println("Closing session | {sessionName}=" + id);

		// If the session exists
		if (sessionService.getMapSessions().get(id) != null && sessionService.getMapSessionNamesTokens().get(id) != null) {
			Session s = sessionService.getMapSessions().get(id);
			s.close();
			sessionService.getMapSessions().remove(id);
			sessionService.getMapSessionNamesTokens().remove(id);

			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{id}/force-disconnect", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceDisconnect(@PathVariable("id") String id, @RequestBody ForceDisconnectReq req) {
		try {
			String token = req.getToken();
			String connectionId = req.getConnectionId();
			
			// If the session exists
			if (sessionService.getMapSessions().get(id) != null && sessionService.getMapSessionNamesTokens().get(id) != null) {
				Session s = sessionService.getMapSessions().get(id);
				int userId = sessionService.getMapSessionNamesUsers().get(id).get(token);
				s.forceDisconnect(connectionId);
				conferenceService.createSessionHistory(new ConferenceHistoryDTO(Integer.parseInt(id), userId, "EXIT"));
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

	@RequestMapping(value = "/{id}/force-unpublish", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceUnpublish(@PathVariable("id") String id, @RequestBody ForceUnpublishReq req) {
		String streamId = req.getStreamId();
		
		try {
			// If the session exists
			if (sessionService.getMapSessions().get(id) != null && sessionService.getMapSessionNamesTokens().get(id) != null) {
				Session s = sessionService.getMapSessions().get(id);
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
	
	@GetMapping("/{id}/attend")
	public ResponseEntity<Map<String, Integer>> getNumOfAttend(@PathVariable("id") String id){
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			// If the session exists
			if (sessionService.getMapSessions().get(id) != null && sessionService.getMapSessionNamesTokens().get(id) != null) {
				map.put("data", sessionService.getMapSessionNamesTokens().get(id).size());
				return new ResponseEntity<>(map ,HttpStatus.OK);
			} else {
				// The SESSION does not exist
				map.put("data", 0);
				return new ResponseEntity<>(map,HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("data", -1);
			return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
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
