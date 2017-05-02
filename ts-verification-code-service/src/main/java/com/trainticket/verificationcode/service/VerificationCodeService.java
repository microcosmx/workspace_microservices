package com.trainticket.verificationcode.service;

import java.io.OutputStream;
import java.util.Map;

public interface VerificationCodeService {
	Map<String, Object> getImageCode(int width, int height, OutputStream os);
}
