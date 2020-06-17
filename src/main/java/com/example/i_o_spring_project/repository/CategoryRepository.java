package com.example.i_o_spring_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.i_o_spring_project.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Category findById(int id);

	public Category findByName(String name);
}
