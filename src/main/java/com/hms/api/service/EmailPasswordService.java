package com.hms.api.service;

import com.hms.api.model.EmailDetails;
import com.hms.api.model.ResetPasswordDetail;

public interface EmailPasswordService {

	boolean sendMail(EmailDetails details);

	String sendOtp(String UserId);

	String resetPasswordByOtp(ResetPasswordDetail detail);

	String resetPasswordByQA(ResetPasswordDetail detail);

}
