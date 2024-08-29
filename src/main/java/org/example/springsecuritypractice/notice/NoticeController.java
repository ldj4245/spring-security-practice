package org.example.springsecuritypractice.notice;

import lombok.RequiredArgsConstructor;
import org.example.springsecuritypractice.note.NoteRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;


    //공지사항 조회

    @GetMapping
    public String getNotice(Model model){
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices",notices);
        return "notice/index";

    }


    //공지사항 등록
    @PostMapping
    public String postNotice(@ModelAttribute NoteRegisterDto noteDto){
        noticeService.saveNotice(noteDto.getTitle(), noteDto.getContent());
        return "redirect:notice";

    }

    //공지사항 삭제
    @DeleteMapping
    public String deleteNotice(@RequestParam Long id){
        noticeService.deleteNotice(id);
        return "redirect:notice";
    }
}
