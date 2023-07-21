package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Product;


@RestController
public class ProductController {

	private List<Product> data = new ArrayList<Product>();

	@GetMapping("/product")
	public List<Product> getProduct() {
		
		return data;
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product body) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == body.getProductId()) {
				return null;
			}
		}

		data.add(body);
		return body;

	}

	@GetMapping("/product/search")
	public List<Product> getProductSearch(@RequestParam String productName,@RequestParam String productDetail) {
		List<Product> foundData = new ArrayList<Product>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductName().contains(productName)&&productName != "" || data.get(i).getProductDetail().contains(productDetail )&&productDetail !="")
				{
				foundData.add(data.get(i));
				 
			}
		}
		return foundData;
	}

	@PutMapping("/product/{productId}")
	public Product updaProduct(@PathVariable Integer productId, @RequestBody Product body) {

		for (int i = 0; i < data.size(); i++) {
			if (productId == data.get(i).getProductId()) {

				data.get(i).setProductName(body.getProductName());
				data.get(i).setProductPrice(body.getProductPrice());
				data.get(i).setProductAmount(body.getProductAmount());
				data.get(i).setProductDetail(body.getProductDetail());

				return data.get(i);

			}
		}
		return null;
	}

	@DeleteMapping("/product/{productId}")
	public String deleproduct(@PathVariable Integer productId) {
		for (int i = 0; i < data.size(); i++) {
			if (productId == data.get(i).getProductId()) {
				data.remove(i);
				
				return "delete sucess";
			}
		}

		return "product not found";
	}

}
