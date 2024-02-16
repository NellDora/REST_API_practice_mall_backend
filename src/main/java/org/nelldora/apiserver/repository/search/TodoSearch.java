package org.nelldora.apiserver.repository.search;

import org.nelldora.apiserver.domain.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1();

}
