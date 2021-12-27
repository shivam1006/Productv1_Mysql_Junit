package com.springboot;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.springboot.controllers.ProductRestController;
import com.springboot.dto.Coupon;
import com.springboot.model.Product;
import com.springboot.repos.ProductRepo;

@SpringBootTest
public class ProductserviceApplicationTests {

	private static final String COUPON_CODE = "SUPERSALE";

	private static final String COUPON_SERVICE_URL = "http://localhost:9091/couponapi/coupons/";

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private ProductRepo repo;

	@InjectMocks
	private ProductRestController controller;

	@Test
	public void contextLoads() {
		Coupon coupon = new Coupon();
		coupon.setCode(COUPON_CODE);
		coupon.setDiscount(new BigDecimal(10));

		Product product = new Product();
		product.setCouponCode(COUPON_CODE);
		product.setPrice(new BigDecimal(100));

		controller.setCouponServiceURL(COUPON_SERVICE_URL);

		when(restTemplate.getForObject(COUPON_SERVICE_URL + COUPON_CODE, Coupon.class)).thenReturn(coupon);

		when(repo.save(product)).thenReturn(product);

		Product productCreated = controller.create(product);
		verify(restTemplate).getForObject(COUPON_SERVICE_URL + COUPON_CODE, Coupon.class);
		verify(repo).save(product);

		assertNotNull(productCreated);
		assertEquals(COUPON_CODE, productCreated.getCouponCode());

	}

}
