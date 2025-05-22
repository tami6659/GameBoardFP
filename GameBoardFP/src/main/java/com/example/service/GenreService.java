package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Genre;
import com.example.repository.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository re_genre;//Genreのリポジトリ
	
	//ジャンル一覧
		public Iterable<Genre> GenreList() {

			Iterable<Genre> genre_list = re_genre.findAll();

			return genre_list;
		}

	//既存ジャンル確認
	public boolean AddCheck(String genre) {

		List<Genre> genrelist = re_genre.findByGenre(genre);

		if (genrelist.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	//ジャンル追加
	public void GenreInsert(String genre) {
		re_genre.insertByGenre(genre);
	}

}
