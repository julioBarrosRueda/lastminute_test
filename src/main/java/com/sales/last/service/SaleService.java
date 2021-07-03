package com.sales.last.service;

import java.util.List;

import com.sales.last.model.ProductDTO;
import com.sales.last.model.SaleResponseDTO;

public interface SaleService {
	
	SaleResponseDTO porcessSale(List<ProductDTO> productDTO);
	
}
