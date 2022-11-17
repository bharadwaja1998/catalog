package com.onimurasame.productcatalog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onimurasame.productcatalog.data.Product;
import com.onimurasame.productcatalog.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/products")
class ProductController {

    @GetMapping
    ResponseEntity<String> getProducts() throws JsonProcessingException {

        List<Product> productList = Arrays.asList(new Product(UUID.randomUUID(), "T-shirt"), new Product(UUID.randomUUID(), "Pants"));

        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(productList), HttpStatus.OK);
    }

    @GetMapping("/exception")
    void getException() throws CustomException {
        throw new CustomException();
    }

}
