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

//	NoteEntity deleteNote(long noteId, String token);
//
//	boolean deleteNotePermanently(long noteId, String token);
//
//	NoteEntity pinOrUnpinNote(long noteId, String token);
//
//	NoteEntity archieveNote(long noteId, String token);
//
//	boolean updateNote(long noteId, NoteUpdateDto noteUpdateDto, String token);
//
	List<NoteEntity> fetchAllNotes(long userId);
//
//	List<NoteEntity> fetchTrashedNote(String token);
//
//	List<NoteEntity> fetchArchievedNotes(String token);
//
//	List<NoteEntity> fetchPinnedNotes(String token);
//
//	NoteEntity getNote(long note_id);
//
//	boolean addNoteColor(long noteId, String color, String token);
//
//	List<NoteEntity> fetchByTitle(String title, String token);
	
}
