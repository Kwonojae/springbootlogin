package com.sesac_login.repository;

import com.sesac_login.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title,String content);  //검색조건이 두개이므로 or조건문으로 두개가 일치한다면 값을 가져옴

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
