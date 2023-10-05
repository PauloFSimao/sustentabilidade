package com.backend.sustentabilidade.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.sustentabilidade.repository.PostagemRepository;

@CrossOrigin
@RequestMapping("/api/postagem")
@RestController
public class PostagemRestController {

	@Autowired
	private PostagemRepository repository;
}
