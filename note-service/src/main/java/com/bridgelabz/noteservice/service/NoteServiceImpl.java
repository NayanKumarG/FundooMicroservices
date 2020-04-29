/**
 * @author Nayan Kumar G
 * purpose :Service implementation of Note
 * 
 */
package com.bridgelabz.noteservice.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.noteservice.dto.NoteDto;
import com.bridgelabz.noteservice.dto.NoteUpdateDto;
import com.bridgelabz.noteservice.entity.NoteEntity;
import com.bridgelabz.noteservice.exception.NoteNotFoundException;
import com.bridgelabz.noteservice.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService{

	private NoteEntity noteEntity = new NoteEntity();

	@Autowired
	private NoteRepository noteRepository;


	/**
	 * provides service to create note
	 * @param noteDto to store the data , token to identify user
	 * 
	 */
	@Override
	public boolean createNote(NoteDto noteDto, String token , long userId) {


			BeanUtils.copyProperties(noteDto, noteEntity);
			noteEntity.setCreatedDate(LocalDateTime.now());
			noteEntity.setArchieved(false);
			noteEntity.setTrashed(false);
			noteEntity.setPinned(false);
			noteEntity.setColor("white");
			noteEntity.setUserId(userId);
			noteRepository.save(noteEntity);
			return true;
	
	}

	/**
	 * provides service to update note
	 * @param noteUpdateDto to store the modified data , token to identify user
	 * 
	 */
	@Override
	public boolean updateNote(long noteId , NoteUpdateDto noteUpdateDto) {
	
			NoteEntity note = noteRepository.fetchById(noteId);
			if(note!=null)
			{
				note.setTitle(noteUpdateDto.getTitle());
				note.setDescription(noteUpdateDto.getDescription());
				note.setUpdateDate(LocalDateTime.now());
				noteRepository.saveOrUpdate(note);
				return true;
			}
			else

				throw  new NoteNotFoundException("Note Not Found" , HttpStatus.NOT_FOUND);
	

	}
	/**
	 * provides service to delete  note permanently
	 * @param noteId to delete note permanently, token to identify user
	 * 
	 */
	@Override
	public boolean deleteNotePermanently(long noteId) {
		
			NoteEntity note = noteRepository.fetchById(noteId);
			if(note!=null)
			{
				noteRepository.deleteNote(noteId);
				return true;

			}
			else
				throw  new NoteNotFoundException("Note Not Found" , HttpStatus.NOT_FOUND);
		
	}

	/**
	 * provides service to get list of notes
	 * @param token to identify user
	 * 
	 */
	@Override
	public List<NoteEntity> fetchAllNotes(long userId) {

			List<NoteEntity> notes = noteRepository.getNotes(userId);
			notes.sort((NoteEntity note1 , NoteEntity note2)->note1.getTitle().compareTo(note2.getTitle()));            
			return notes;



	}

}
