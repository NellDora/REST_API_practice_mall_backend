package org.nelldora.apiserver.repository.search;

import org.nelldora.apiserver.dto.PageRequestDTO;
import org.nelldora.apiserver.dto.PageResponseDTO;
import org.nelldora.apiserver.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);
}
