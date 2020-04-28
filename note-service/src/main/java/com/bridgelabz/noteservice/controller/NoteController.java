/**
 * @author Nayan Kumar G
 * purpose :Controller for Notes
 * 
 */
package com.bridgelabz.noteservice.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.noteservice.dto.NoteDto;
import com.bridgelabz.noteservice.dto.NoteUpdateDto;
import com.bridgelabz.noteservice.dto.User;
import com.bridgelabz.noteservice.entity.NoteEntity;
import com.bridgelabz.noteservice.response.Response;
import com.bridgelabz.noteservice.service.NoteService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@CrossOrigin("*")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private RestTemplate restTemplate;


	/**
	 * Api to create note
	 * @param noteDto to map with request object
	 * @param token to get user id
	 * 
	 */
	@PostMapping("/notes/create")
	public ResponseEntity<Response> createNotes(@RequestBody NoteDto noteDto , @RequestHeader String token)
	{
		log.info("token:"+token);
		log.info("not:"+noteDto);
		
		User user = restTemplate.getForObject("http://user-service/users/getUser/"+token , User.class);
		log.info("user got:"+user.getId());
		if(user!=null)
		{
		noteService.createNote(noteDto , token , user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created ", noteDto));
		}
		else
		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("user not found "));
		
	}

	/**
	 * Api to delete note
	 * @param noteId to delete perticular note
	 * @param token to get user detail
	 * 
	 */
//	@PutMapping("/notes/delete/{noteId}")
//	public ResponseEntity<Response> deleteNotes(@PathVariable long noteId , @RequestHeader String token)
//	{
//		NoteEntity note = noteService.deleteNote(noteId , token);
//		if(note.isTrashed()==true)
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted saved into trash"));
//		else
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("note restored"));
//			
//	}
//
//	/**
//	 * Api to delete note permanently
//	 * @param noteId to delete note
//	 * @param token to get user id
//	 * 
//	 */
//	@DeleteMapping("/notes/deletePermanently/{noteId}")
//	public ResponseEntity<Response> deleteNoteParmanently(@PathVariable long noteId , @RequestHeader String token)
//	{
//		noteService.deleteNotePermanently(noteId , token);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted success"));
//	}
//
//	/**
//	 * Api to pin the note
//	 * @param noteId to pin the note
//	 * @param token to get user id
//	 * 
//	 */
//	@PutMapping("/notes/pin/{noteId}")
//	public ResponseEntity<Response> pinNote(@PathVariable long noteId , @RequestHeader String token)
//	{
//		NoteEntity note = noteService.pinOrUnpinNote(noteId , token);
//		if(note.isPinned()==true)
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned",200));
//		else
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("unpin success",200));
//			
//	}
//
//	/**
//	 * Api to archieve the note
//	 * @param noteId to archieve note
//	 * @param token to get user id
//	 * 
//	 */
//	@PutMapping("/notes/archieve/{noteId}")
//	public ResponseEntity<Response> archieve(@PathVariable long noteId , @RequestHeader String token)
//	{
//		NoteEntity note = noteService.archieveNote(noteId , token);
//		if(note.isArchieved()==true)
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved",200));
//		else
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("note unarchieved",200));
//			
//	}
//
//	/**
//	 * Api to update note
//	 * @param noteUpdateDto to bind the request object
//	 * @param token to get user id
//	 * 
//	 */
//	@PutMapping("/notes/update/{noteId}")
//	public ResponseEntity<Response> updateNote(@PathVariable long noteId , @RequestBody NoteUpdateDto noteUpdateDto , @RequestHeader String token)
//	{
//		noteService.updateNote(noteId , noteUpdateDto , token);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("update success"));
//	}

	/**
	 * Api to get all notes
	 * @param token to identify user
	 * @return list of notes
	 */
	@GetMapping("/notes/getNotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token)
	{
		User user = restTemplate.getForObject("http://user-service/users/getUser/"+token , User.class);
	
		if(user!=null)
		{
		List<NoteEntity> notes = noteService.fetchAllNotes(user.getId());

		if(notes.size()>0)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched all notes",notes));
		else
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("notes not found!! create note",notes));
	
		}
		else
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("user not found!"));
			
		}
		


//	/**
//	 * Api to get trashed notes
//	 * @param token to identify user
//	 * @return list of trashed notes if found
//	 */
//	@GetMapping("/notes/trashedNotes")
//	public ResponseEntity<Response> getTrashedNotes(@RequestHeader String token)
//	{
//		List<NoteEntity> notes = noteService.fetchTrashedNote(token);
//		if(notes.size()>0)
//
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched trashed note",notes));
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Trashed note not found"));
//	}
//
//	/**
//	 * Api to get archieved notes
//	 * @param token to get user id
//	 * @return list of notes
//	 */
//	@GetMapping("/notes/archievedNotes")
//	public ResponseEntity<Response> getArchievedNotes(@RequestHeader String token)
//	{
//		List<NoteEntity> notes = noteService.fetchArchievedNotes(token);
//		if(notes.size()>0)
//
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched archieved note",notes));
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("archieved note not found"));
//	}
//
//	/**
//	 * Api to get pinned notes
//	 * @param token to get user id
//	 * @return list of pinned notes
//	 */
//	@GetMapping("/notes/pinnedNotes")
//	public ResponseEntity<Response> getPinnedNotes(@RequestHeader String token)
//	{
//		List<NoteEntity> notes = noteService.fetchPinnedNotes(token);
//		
//		if(notes.size()>0)
//			
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched pinned note",notes));
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("pinned note not found"));
//	}
//
//	
//	/**
//	 * Api to add color to note
//	 * @param noteId to add color
//	 * @param color type of color added
//	 * @param token to get user id
//	 * @return updated note
//	 */
//	@PutMapping("/notes/addColor")
//	public ResponseEntity<Response> addNoteColor(@RequestParam(value = "noteId") long noteId , @RequestParam(value = "color") String color , @RequestHeader String token)
//	{
//		noteService.addNoteColor(noteId , color , token);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("color added"));
//	}
//	
//	/**
//	 * Api to get notes by title
//	 * @param title to find notes
//	 * @param token to identify user
//	 * @return list of fetched notes
//	 */
//	@GetMapping("/notes/getNotesByTitle")
//	public ResponseEntity<Response> getNotesByTitle(@RequestParam String title , @RequestHeader String token)
//	{
//		List<NoteEntity> notes = noteService.fetchByTitle(title , token);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("Notes fetched by title" , notes));
//	}


}
