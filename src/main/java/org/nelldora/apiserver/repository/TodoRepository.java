package org.nelldora.apiserver.repository;

import org.nelldora.apiserver.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> { //<Entity, PK>
}
