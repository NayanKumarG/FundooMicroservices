/**
 * @author Nayan Kumar G
 * purpose : Provide services of Note
 * 
 */
package com.bridgelabz.noteservice.service;

import java.util.List;

import com.bridgelabz.noteservice.dto.NoteDto;
import com.bridgelabz.noteservice.dto.NoteUpdateDto;
import com.bridgelabz.noteservice.entity.NoteEntity;

public interface NoteService {

	boolean createNote(NoteDto noteDto, String token , long userId);

	boolean deleteNotePermanently(long noteId);
	
	boolean updateNote(long noteId, NoteUpdateDto noteUpdateDto);

	List<NoteEntity> fetchAllNotes(long userId);
	
}
