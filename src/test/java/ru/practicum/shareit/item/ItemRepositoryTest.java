package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void search() {
    }

    @Test
    void findByIdAndOwnerId() {
    }

    @Test
    void findAllByOwnerIdOrderById() {
    }

    @Test
    void findAllByRequestId() {
    }
}