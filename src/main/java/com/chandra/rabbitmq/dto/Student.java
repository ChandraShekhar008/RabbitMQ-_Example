package com.chandra.rabbitmq.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public final class Student implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("rollNo")
    private String rollNo;
	
	@JsonProperty("studentName")
    private String studentName;
	
	@JsonProperty("section")
	private String section;

}
