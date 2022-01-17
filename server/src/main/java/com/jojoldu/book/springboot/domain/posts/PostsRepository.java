package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository <Posts, Long>{


    //SpringDataJpa에서 제공하지 않는 메소드는 직접 작성해야함
    @Query("Select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();


}
