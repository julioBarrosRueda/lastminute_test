package com.sales.last.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sales.last.api.SaleApi;
import com.sales.last.model.ProductDTO;
import com.sales.last.model.SaleResponseDTO;
import com.sales.last.service.SaleService;

@RestController
public class SaleController implements SaleApi{
	
	protected final SaleService saleService;
	
	@Autowired
	SaleController(SaleService saleService){
		this.saleService = saleService;
	}
	
	@Override
	public ResponseEntity<SaleResponseDTO> addProduct(List<ProductDTO> productDTO) {
		return ResponseEntity.ok(saleService.porcessSale(productDTO));
	}
}
