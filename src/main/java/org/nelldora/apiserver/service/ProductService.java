package org.nelldora.apiserver.service;

import jakarta.transaction.Transactional;
import org.nelldora.apiserver.dto.PageRequestDTO;
import org.nelldora.apiserver.dto.PageResponseDTO;
import org.nelldora.apiserver.dto.ProductDTO;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

}
