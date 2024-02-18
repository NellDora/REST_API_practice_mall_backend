package org.nelldora.apiserver.repository.search;

import org.nelldora.apiserver.domain.Todo;
import org.nelldora.apiserver.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);

}
