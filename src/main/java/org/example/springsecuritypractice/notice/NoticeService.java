package org.example.springsecuritypractice.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;


    //모든 공지사항 조회
    @Transactional(readOnly = true)
    public List<Notice> findAll(){
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    //공지사항 저장
    public Notice saveNotice(String title, String content){
        return noticeRepository.save(new Notice(title,content));
    }

    public void deleteNotice(Long id){
        noticeRepository.findById(id).ifPresent(noticeRepository::delete);
    }



}
