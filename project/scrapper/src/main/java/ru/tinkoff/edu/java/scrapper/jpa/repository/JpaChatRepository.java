package ru.tinkoff.edu.java.scrapper.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.edu.java.scrapper.jpa.entity.Chat;

public interface JpaChatRepository extends CrudRepository<Chat, Long> {

}
