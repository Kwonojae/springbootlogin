package com.sesac_login.controller;

import com.sesac_login.model.Board;
import com.sesac_login.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model) {//데이터 값을 넘겨 줄때 model사용
        List<Board> boards = boardRepository.findAll();//db 데이터 다가져온다
        model.addAttribute("boards",boards);    // 키와 벨류 가져옴
        return "board/list";
    }
    @GetMapping("/form")    //Header tag 에 포함 URL에 변수(데이터)를 포함시켜 요청
    public String form(Model model) {
        model.addAttribute("board", new Board());//값셋팅
        return "board/form";
    }
    @PostMapping("/form")   //body Tag에 포함 URL에 변수(데이터)를 노출하지 않고 요청
    public String greetingSubmit(@ModelAttribute Board board) { //위에서 Board객체를 ModelAttribute 사용 하여 다시 받아옴
        boardRepository.save(board);        //Board에 id 에 키값이 있을경우 업데이트  없을경우 새로운 데이터가 인서트
        return "redirect:/board/list";      //리다이렉트(redirect)는 웹 브라우저(사용자)가 어떤 URL로 웹 서버를 요청했을때 다른 URL로 넘겨주는 것을 말합니다
    }
}
