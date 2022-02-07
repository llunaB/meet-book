package com.ssafy.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssafy.db.converter.ActionConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConferenceHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "CONFE_ID")
	private Conference conference;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Convert(converter = ActionConverter.class)
	private String action;
	
	@Column(name = "inserted_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedTime;
	
}
