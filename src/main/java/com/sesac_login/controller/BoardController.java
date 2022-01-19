package com.sesac_login.controller;

import com.sesac_login.model.Board;
import com.sesac_login.repository.BoardRepository;
import com.sesac_login.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText) {//데이터 값을 넘겨 줄때 model사용 페이징 처리   http://localhost:8081/board/list?page=0
        //Page<Board> boards = boardRepository.findAll(pageable);//db 데이터 다가져온다
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);

        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);//현재 페이지 가저옴 페이지개수 보여주는 부분 최소값 0
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4); //전체에 끝페이지 개수가 몇개인지 현재 페지가 2 면 앞뒤로 보여줄 페이지 개수
        model.addAttribute("startPage", startPage );
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards",boards);    // 키와 벨류 가져옴
        return "board/list";//총게시판 개수
    }
    @GetMapping("/form")    //Header tag 에 포함 URL에 변수(데이터)를 포함시켜 요청
    public String form(Model model, @RequestParam(required = false)Long id) {      //required = false필수인지 아닌지
        if (id == null) {
            model.addAttribute("board", new Board());
        }else {
            Board board = boardRepository.findById(id).orElse(null);    //키값을 기준으로 가져옴 orElse:키값이 없으면 null로 셋팅
            model.addAttribute("board", board);//값셋팅
        }
        return "board/form";
    }
    @PostMapping("/form")   //body Tag에 포함 URL에 변수(데이터)를 노출하지 않고 요청
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board,bindingResult); //validate메서드를 사용하여 값의 유무 확인
        //위에서 Board객체를 ModelAttribute 사용 하여 다시 받아옴
        //Board에 id 에 키값이 있을경우 업데이트  없을경우 새로운 데이터가 인서트
        //리다이렉트(redirect)는 웹 브라우저(사용자)가 어떤 URL로 웹 서버를 요청했을때 다른 URL로 넘겨주는 것을 말합니다
        //Valid :객체에 들어가는 값을 검증해주는 어노테이션 min max notnull 어노테이션으로 간단하게 값체크
        //BindingResult : 검증 오류가 발생할 경우 오류 내용을 보관하는 객체
        if(bindingResult.hasErrors()) { //hasErrors 에러가 존재하는지
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
