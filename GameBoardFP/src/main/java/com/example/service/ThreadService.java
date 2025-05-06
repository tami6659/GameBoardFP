package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Thread;
import com.example.repository.ThreadRepository;

@Service
public class ThreadService {

	@Autowired
	private ThreadRepository re_thread;//Threadのリポジトリ

	//スレッド一覧
	public Iterable<Thread> ThreadList() {

		Iterable<Thread> thread_list = re_thread.findAll();

		return thread_list;
	}

	//スレッド検索
	public List<Thread> ThreadSearch(String title, String game) {

		List<Thread> thread_list = re_thread.findByThread(title, game);

		return thread_list;
	}

	//スレッドジャンル検索
	public List<Thread> ThreadG_Search(String genre) {

		List<Thread> thread_list = re_thread.findByThread_genre(genre);

		return thread_list;
	}

	//スレッド名存在チェック
	public List<Thread> ThreadTitle(String title) {

		List<Thread> thread_title = re_thread.findByTitle(title);

		return thread_title;
	}
	
	//スレッド情報検索
	public Thread ThreadInfo(int t_id) {

		List<Thread> thread_info = re_thread.findByThread_info(t_id);

		return thread_info.get(0);
	}

	//スレッド登録
	public void ThreadInsert(String title, String game, String genre, String user) {
		re_thread.insertByThread(title, game, genre, user);
	}

	//スレッドID取得
	public int ThreadID(String title) {

		List<Thread> thread = re_thread.findByTitle(title);

		return thread.get(0).getId();
	}
	
	//スレッド削除
	public void ThreadDelete(int t_id) {
		re_thread.DeleteByThread(t_id);
	}
	
}
