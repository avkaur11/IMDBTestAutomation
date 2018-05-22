package com.imdb.utility;

public class CustomException extends Exception{
		
		public CustomException(String Message){
			super(Message);
			System.out.println(Message);
		}
	}
