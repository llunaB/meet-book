package com.jojoldu.book.springboot.domain.user;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.now;

@Transactional  // could not initialize proxy [org.example.user.User#3] - no Session (getOne)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {

        // stream
        userRepository.findAll().forEach(System.out::println);

        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }

        // name 의 역순으로 정렬
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        users.forEach(System.out::println);
        System.out.println("--------------------------------------------");

        // 해당 아이디로 검색 1, 2
        List<User> ids = userRepository.findAllById(Lists.newArrayList(1, 2));
        ids.forEach(System.out::println);
        System.out.println("--------------------------------------------");

        User user1 = new User("이름1", "비밀번호", "비밀번호확인", "닉네임", "이메일");
        User user2 = new User("이름2", "비밀번호", "비밀번호확인", "닉네임", "이메일");
        User user3 = new User("이름3", "비밀번호", "비밀번호확인", "닉네임", "이메일");

        userRepository.saveAll(Lists.newArrayList(user1, user2));
        userRepository.save(user3);

        List<User> users2 = userRepository.findAll();

        users2.forEach(System.out::println);

        System.out.println("--------------------------------------------");

        // getOne 에서 Session 유지를 위해 @Transactional 추가
        User user4 = userRepository.getOne(3);

        System.out.println(user4);

        System.out.println("--------------------------------------------");

        // optional mapping -> null 처리해야
        Optional<User> user5 = userRepository.findById(3); // optional 반환
        User user6 = userRepository.findById(2).orElse(null); // 객체 반환

        System.out.println(user5);
        System.out.println(user6);

        System.out.println("--------------------------------------------");

        // flush는 db 반영 시점을 결정한다.
        userRepository.save(new User("플러시테스트", "비밀번호3", "비밀번호3확인", "닉네임", "이메일"));

        userRepository.flush();

        userRepository.findAll().forEach(System.out::println);

        System.out.println("--------------------------------------------");

        // count
        Long count = userRepository.count();

        System.out.println(count);

        System.out.println("--------------------------------------------");

        // exists
        boolean exists = userRepository.existsById(1);

        System.out.println(exists);

        System.out.println("--------------------------------------------");

        // delete 방법 2가지
        // userRepository.delete(userRepository.findById(1).orElse(null));
        userRepository.delete(userRepository.findById(1).orElseThrow(RuntimeException::new));

        userRepository.deleteById(2);

        // lambda & stream
        for (User el : userRepository.findAll()) {
            System.out.println(el);
        }

        userRepository.findAll().forEach((x) -> System.out.println(x));

        userRepository.findAll().forEach(System.out::println);


        System.out.println("--------------------------------------------");

        // deleteAll
        /*
        userRepository.deleteAll(); // select 후 하나하나 Delete한다.
        userRepository.deleteInBatch(userRepository.findAllById(Lists.newArrayList(1, 2))); // where 조건절에 or 연산 + select 하지 않아 훨씬 빠르다.
        userRepository.deleteAllInBatch(); // select 자체를 하지 않는다.
        */

        userRepository.findAll().forEach(System.out::println);


        System.out.println("--------------------------------------------");


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
    void userRelationTest() {
        User user = new User("이름1", "비밀번호", "비밀번호확인", "닉네임", "이메일");

        userRepository.save(user);


    }
}
