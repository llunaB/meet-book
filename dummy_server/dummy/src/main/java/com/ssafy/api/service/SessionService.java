package com.ssafy.api.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.Getter;

@Getter
@Service
public class SessionService {
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
	private Map<String, Map<String, Integer>> mapSessionNamesUsers = new ConcurrentHashMap<>();
}
