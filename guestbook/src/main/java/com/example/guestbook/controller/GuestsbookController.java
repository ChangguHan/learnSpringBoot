package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입
public class GuestsbookController {
    private final GuestbookService service; // final로 선언

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }
    
    /**
     * spring MVC : 파라미터 자동으로 수집, page, size 파라미터 전달 시 PageRequestDTO 객체로 자동으로 수집
     * Model : 결과 데이터를 화면에 전달하기 위해 사용
     * @param pageRequestDTO
     * @param model
     * @return
     */
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list......." + pageRequestDTO);
        // model에 PageResult를 result라는 이름으로 전달
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    // 등록의 GET은 화면을 보여줌
    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    // 등록의 POST는 처리 후 목록 페이지로 이동
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto...." + dto);

        // 새로 추가
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno);// addFlash~ : 한번만 데이터 전달, 화면상 모달창 보여주기 위해 사용
        return "redirect:/guestbook/list";
    }

    // 조회 서비스
    // GET방식으로 gno받아서, Model에 GuestbookDTO 담아서 전달
    // 다시 목록 페이지 돌아갈 경우 생각해 PageRequestDTO 같이 사용
    // @ModelAttribute 없어도 처리 가능하나 명시적으로 requestDTO라는 이름으로 처리
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("gno: " + gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto",dto);
    }

    // 삭제
    // POST, gno 전달 후 목록의 첫페이지로 이동
    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("gno: " + gno);
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }

    // 수정

    /**
     *
     * @param dto : 수정해야하는 글 정보 가지는 GuestbookDTO
     * @param requestDTO : 기존 페이지정보 유지 : 리다이렉트로 이동
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify...............");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }
}
