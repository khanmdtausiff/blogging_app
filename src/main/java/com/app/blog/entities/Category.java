package com.app.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	private String categoryTitle;
	private String categoryDescription;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category") //mappedBy means foreign key column will be mapped by Category named categoryId in Post table...Cascade ALL we use in parent class that if parent is created,fetched or destroyed then child also gets created.fetched,destroyed simultaneously...cascade ALL is always used in parent class.
	private List<Post> posts = new ArrayList<>();
}
