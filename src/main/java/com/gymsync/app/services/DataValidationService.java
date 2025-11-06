package com.gymsync.app.services;

import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DataValidationService extends AbstractService {

	public Boolean isPasswordValid(String pass) {
		Boolean ret = false;
		if (pass == null || pass.equalsIgnoreCase("Contraseña"))
			return false;
		if (pass.length() >= 8 && pass.matches(".*[A-Z].*") && pass.matches(".*[0-9].*")
				&& pass.matches(".*[!@#$%^&*()].*")) {
			ret = true;
		}
		return ret;
	}

	public Boolean isEmailValid(String email) {
		Boolean ret = false;
		if (email == null || email.equalsIgnoreCase("Email"))
			return false;
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if (Pattern.matches(emailRegex, email)) {
			ret = true;
		}
		return ret;
	}

	public Boolean isDateValid(String dateStr) {
		Boolean ret = false;
		if (dateStr == null || dateStr.equalsIgnoreCase("Fecha de nacimiento"))
			return false;

		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("dd-MM-yyyy");
		simpleDataFormat.setLenient(false);

		try {
			simpleDataFormat.parse(dateStr);
			ret = true;
		} catch (ParseException e) {
			ret = false;
		}
		return ret;
	}

}
