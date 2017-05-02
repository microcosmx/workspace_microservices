package com.trainticket.verificationcode.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;import java.util.HashMap;
import java.util.Map;import java.util.Random;

public interface VerificationCodeService {
	public Map<String, Object> getImageCode(int width, int height, OutputStream os);
}
