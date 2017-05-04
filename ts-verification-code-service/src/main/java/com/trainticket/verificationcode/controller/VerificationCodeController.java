package com.trainticket.verificationcode.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.trainticket.verificationcode.service.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.*;
import java.io.*;

@RestController
public class VerificationCodeController {

	@Autowired
	private VerificationCodeService verificationCodeService;


	@RequestMapping(value="/error",method=RequestMethod.GET)
	public String welcome(){
		return "error";
	}

	
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public void imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    OutputStream os = response.getOutputStream();
	    Map<String,Object> map = verificationCodeService.getImageCode(60, 20, os, request, response);
	    String simpleCaptcha = "simpleCaptcha";
	    request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
	    request.getSession().setAttribute("codeTime",new Date().getTime());
	    try {
	        ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
	    } catch (IOException e) {
	        //error
			String error = "Can't generate verification code";
			os.write(error.getBytes());
	    }
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public boolean verifyCode(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
		String receivedCode = map.get("verificationCode");
		boolean result = verificationCodeService.verifyCode(request, response, receivedCode);
		return result;
	}
}
