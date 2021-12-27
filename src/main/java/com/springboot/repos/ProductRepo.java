package com.springboot.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
