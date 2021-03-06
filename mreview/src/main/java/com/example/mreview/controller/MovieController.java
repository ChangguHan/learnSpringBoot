package com.example.mreview.controller;

import com.example.mreview.dto.MovieDTO;
import com.example.mreview.dto.PageRequestDTO;
import com.example.mreview.dto.PageResultDTO;
import com.example.mreview.service.MovieService;
import com.example.mreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    @GetMapping("/register")
    public void register(@ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO: " + movieDTO);
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";

    }

    @GetMapping("/list")
    public void list(PageRequestDTO requestDTO, Model model) {
        log.info("pageRequestDTO: " + requestDTO);
        model.addAttribute("result", movieService.getList(requestDTO));
    }

    @GetMapping({"/read","/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);
        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);
    }

    @PostMapping("/modify")
    public String modify(MovieDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify...............");
        log.info("dto: " + dto);

        movieService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
//        redirectAttributes.addAttribute("type", requestDTO.getType());
//        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("mno", dto.getMno());

        return "redirect:/movie/read";
    }


    @PostMapping("/remove")
    public String remove(long mno, RedirectAttributes redirectAttributes) {
        log.info("mno: " + mno);

        movieService.remove(mno);

        redirectAttributes.addFlashAttribute("mno",mno);
        return "redirect:/movie/list";

    }
}
