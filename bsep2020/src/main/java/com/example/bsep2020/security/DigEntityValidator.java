package com.example.bsep2020.security;

public class DigEntityValidator {
	
	public static boolean isStringOnlyAlphabet(String str) 
    { 
        return ((str != null) 
                && (!str.equals("")) 
                && (str.matches("^[a-zA-Z]*$"))); 
    } 
}
