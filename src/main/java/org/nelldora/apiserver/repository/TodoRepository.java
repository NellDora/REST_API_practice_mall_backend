package org.nelldora.apiserver.repository;

import org.nelldora.apiserver.domain.Todo;
import org.nelldora.apiserver.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long>, TodoSearch { //<Entity, PK>
}
