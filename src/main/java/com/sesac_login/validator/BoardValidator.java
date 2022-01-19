package com.sesac_login.validator;

import com.sesac_login.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component  //컨트롤러의 인젝션을위해 필요함
public class BoardValidator implements Validator {
    //Validator 인터페이스를 상속한 클래스는 두 메서드를 구현해야 한다.
    //boolean supports(Class clazz) : 어떤 타입의 객체를 검증할 때 이 객체의 클래스가 이 Validator가 검증할 수 있는 클래스인 지를 판단하는 매서드
    //void validate(Object target, Errors error) : 실제 검증 로직이 이루어지는 메서드, 구현할 때 ValidationUtils를 사용하여 편리하게 작성 가능
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;  //형변환 obj 를 Board로
        if (StringUtils.isEmpty(b.getContent())) {
            //값이 비어있을경우
            errors.rejectValue("content","key","내용을 입력하세요");
        }
    }
}
