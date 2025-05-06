package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.service.BoardService;
import com.example.service.GenreService;
import com.example.service.HistoryService;
import com.example.service.ThreadService;

@Controller
public class AdminController {

	@Autowired
	private BoardService b_service;//掲示板サービス
	
	@Autowired
	private GenreService g_service;//ジャンルサービス

	@Autowired
	private ThreadService t_service;//スレッドサービス
	
	@Autowired
	private HistoryService h_service;//履歴サービス

	//管理者ページ
	@GetMapping("/admin")
	public ModelAndView Admin(ModelAndView mav) {

		mav.setViewName("admin/admin");

		return mav;
	}

	//スレッド削除ページ
	@GetMapping("/thread_delete")
	public ModelAndView ThreadDelete(ModelAndView mav) {

		//スレッド一覧
		mav.addObject("thread_list", t_service.ThreadList());

		mav.setViewName("admin/thread_delete");

		return mav;
	}
	
	//スレッド削除処理
	@PostMapping("/thread_delete")
	public ModelAndView ThreadDeleteAns(ModelAndView mav,@RequestParam("t_id") int t_id) {
		
		//スレッド削除
		t_service.ThreadDelete(t_id);
		b_service.LossThread(t_id);
		h_service.LossThread(t_id);

		//スレッド一覧
		mav.addObject("thread_list", t_service.ThreadList());

		mav.setViewName("admin/thread_delete");

		return mav;
	}

	//ジャンル追加ページ
	@GetMapping("/genre_add")
	public ModelAndView GenreAdd(ModelAndView mav) {

		mav.setViewName("admin/genre_add");

		return mav;
	}

	//ジャンル追加処理
	@PostMapping("/genre_add")
	public ModelAndView GenreAddAns(
			@RequestParam("genreadd") String genre,
			ModelAndView mav) {

		//既存ジャンル確認
		if (g_service.AddCheck(genre)) {
			//成功
			//追加
			g_service.GenreInsert(genre);
			mav.addObject("ans", "ジャンルを追加しました");
		} else {
			//失敗
			mav.addObject("ans", "すでに同名のジャンルが存在します");
		}

		mav.setViewName("admin/genre_add");
		return mav;
	}

}
