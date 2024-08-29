package org.example.springsecuritypractice.note;

import lombok.RequiredArgsConstructor;
import org.example.springsecuritypractice.user.User;
import org.example.springsecuritypractice.user.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    //노트 조회
    @Transactional(readOnly = true)
    public List<Note> findByUser(User user){
        if(user == null){
            throw new UserNotFoundException();
        }

        if(user.isAdmin()){
            return noteRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        }

        return noteRepository.findByUserOrderByIdDesc(user);

    }

    //노트 저장
    public Note saveNote(User user, String title, String content){
        if(user == null){
            throw new UserNotFoundException();
        }

        return noteRepository.save(new Note(title, content, user));
    }

    //노트 삭제
    public void deleteNote(User user, Long noteId){
        if(user == null){
            throw new UserNotFoundException();
        }

        Note note = noteRepository.findByIdAndUser(noteId, user);
        if(note != null){
            noteRepository.delete(note);
        }

    }

}
