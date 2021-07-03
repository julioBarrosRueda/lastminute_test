package com.sales.last.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.sales.last.model.ProductDTO;
import com.sales.last.model.SaleResponseDTO;
import com.sales.last.service.SaleService;

@RunWith(MockitoJUnitRunner.class)
public class SaleControllerTest {

	@Mock
	SaleService saleService;
	
	SaleController cut;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		cut = new SaleController(saleService);
	}
	
	@Test
	public void addProductTest() {
		List<ProductDTO> listProduct = new ArrayList<>();
		SaleResponseDTO response = mock(SaleResponseDTO.class);
		
		when( saleService.porcessSale(any()) ).thenReturn(response );
		
		ResponseEntity<SaleResponseDTO> resp = cut.addProduct(listProduct );
		
		assertNotNull(resp);
		verify(saleService).porcessSale(any());
	}

}
