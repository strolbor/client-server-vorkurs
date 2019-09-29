package com.jds.vorkurs.shared;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5525242340331171031L;
	private Command command;
	private String message;
	
}
