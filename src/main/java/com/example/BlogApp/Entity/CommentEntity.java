package com.example.BlogApp.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments_tab")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String body;
	private String name;
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="post_id",nullable = false )
	private Post post;

}
