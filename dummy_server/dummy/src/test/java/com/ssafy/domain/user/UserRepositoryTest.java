package com.ssafy.domain.user;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void createTest() {
        User user = new User();
        user.setName("euijin");
        user.setAge(25);
        user.setEmail("euijin@gmail.com");
        user.setGender(1);
        userRepository.save(user);
    }

    @Test
    void findTest() {
        // stream 으로 하나씩 출력
        userRepository.findAll().forEach(System.out::println);
        for (User users : userRepository.findAll()) {
            System.out.println(users);
        }

        System.out.println("--------------------------------------------");

        // 모두 검색
        List<User> users2 = userRepository.findAll();
        users2.forEach(System.out::println);

        System.out.println("--------------------------------------------");

        // name 의 역순으로 정렬
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        users.forEach(System.out::println);

        System.out.println("--------------------------------------------");

        // 모두 검색 - 해당 아이디로 검색 (1, 2)
        List<User> ids = userRepository.findAllById(Lists.newArrayList(1, 2));
        ids.forEach(System.out::println);

        System.out.println("--------------------------------------------");


        // 아이디로 검색 (optional mapping -> null 처리 필수)
        Optional<User> user5 = userRepository.findById(3); // optional 반환
        User user6 = userRepository.findById(2).orElse(null); // 객체 반환

        System.out.println(user5);
        System.out.println(user6);
    }

    @Test
    void paginationTest() {
        // Pagination
        // Pageable 은 page 인터페이스의 구현체
        Pageable firstPageWithTwoElements = PageRequest.of(0, 1); // page, size
        // 전체 사이에서 나눠서 배분하는거같음. 토탈 5개일 때
        // 1번 페이지에 5개 사이즈 배분시 총 1개의 페이지 생성 (5)
        // 1번 페이지에 3개 사이즈 배분시 총 2개의 페이지 생성 (3 + 2)
        // 1번 페이지에 2개 사이즈 배분시 총 3개의 페이지 생성 (2 + 2 + 1)
        // 1번 페이지에 1개 사이즈 배분시 총 5개의 페이지 생성 (1 + 1 + 1 + 1 + 1)

        // findAll(Pageable pageable) 메서드 는 기본적으로 Page<T> 개체를 반환. Page 인터페이스의 메서드 사용가능.
        Page<User> allUsers = userRepository.findAll(firstPageWithTwoElements);

        System.out.println("page : " + allUsers); // Returns a Page of entities meeting the paging restriction provided in the Pageable object.
        System.out.println("totalPages : " + allUsers.getTotalPages()); // Returns the number of total pages.
        System.out.println("totalElements : " + allUsers.getTotalElements()); // Returns a new Page with the content of the current one mapped by the given Function.
        System.out.println("sort : " + allUsers.getSort()); // Returns the sorting parameters for the Slice.
        System.out.println("size : " + allUsers.getSize()); // Returns the number of elements currently on this Slice.
    }

    @Test
    void updateTest() {
        User user = new User();
        user.setName("euijin");
        user.setAge(25);
        user.setEmail("euijin@gmail.com");
        user.setGender(1);
        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);
        user.setName("new_euijin");
    }
}
