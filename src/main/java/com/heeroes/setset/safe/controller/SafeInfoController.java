package com.heeroes.setset.safe.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.heeroes.setset.common.Response;
import com.heeroes.setset.safe.model.service.SafeInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/safe/info")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SafeInfoController {
	private final SafeInfoService safeInfoService;
	
	@GetMapping("/disaster")
	public ResponseEntity<?> getDisasterInfo(
			@RequestParam(defaultValue = "100") int size,
			@RequestParam(defaultValue = "0") int page) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException {
		
		return ResponseEntity.ok(Response.success(safeInfoService.getDisasterInfo(size, page)));
	}
	
	@GetMapping("/news")
	public ResponseEntity<?> getNewsInfo(
			@RequestParam(defaultValue = "100") int size,
			@RequestParam(defaultValue = "0") int page) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException {
		
		return ResponseEntity.ok(Response.success(safeInfoService.getNewsInfo(size, page)));
	}
}
