package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.component.SecuritySession;
import com.example.service.BoardService;
import com.example.service.GameService;
import com.example.service.GenreService;
import com.example.service.HistoryService;
import com.example.service.ThreadService;

@Controller
public class MainController {

	@Autowired
	private SecuritySession securitySession;//セッション情報

	@Autowired
	private ThreadService t_service;//スレッドサービス

	@Autowired
	private BoardService b_service;//掲示板サービス

	@Autowired
	private HistoryService h_service;//履歴サービス

	@Autowired
	private GenreService g_service;//ジャンルサービス

	@Autowired
	private GameService game_service;//ジャンルサービス


	//初期ページ
	@GetMapping("/")
	public ModelAndView main(ModelAndView mav) {

		//セッション情報
		String user = securitySession.getUsername();

		mav.addObject("UserName", user);

		mav.setViewName("index");

		return mav;
	}

	//スレッド一覧
	@GetMapping("/thread")
	public ModelAndView Thread(ModelAndView mav) {

		//ジャンル一覧
		mav.addObject("genre_list", g_service.GenreList());

		//スレッド一覧
		mav.addObject("thread_list", t_service.ThreadList());

		mav.setViewName("board/thread");

		return mav;
	}

	//スレッド検索
	@PostMapping("/thread")
	public ModelAndView ThreadSearch(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "g_search", required = false) String g_search,
			ModelAndView mav) {

		//ジャンル一覧
		mav.addObject("genre_list", g_service.GenreList());

		//タイトル・ゲーム検索
		if (search != null) {
			//検索ボックスが空かどうか
			if (search.isEmpty()) {

				//スレッド一覧
				mav.addObject("thread_list", t_service.ThreadList());

			} else {
				//検索
				mav.addObject("thread_list", t_service.ThreadSearch(search, search));
			}
		}

		//ジャンル検索
		if (g_search != null) {
			mav.addObject("thread_list", t_service.ThreadG_Search(g_search));
		}

		mav.setViewName("board/thread");

		return mav;
	}

	//スレッド追加
	@GetMapping("/thread_add")
	public ModelAndView ThreadAdd(ModelAndView mav) {

		//ジャンル一覧
		mav.addObject("genre_list", g_service.GenreList());

		mav.setViewName("board/thread_add");

		return mav;
	}

	//スレッド追加結果
	@PostMapping("/thread_add")
	public ModelAndView ThreadAddAns(@RequestParam("title") String title, @RequestParam("genre") String genre,
			@RequestParam("game") String game, ModelAndView mav) {

		//セッション情報
		String user = securitySession.getUsername();
		mav.addObject("UserName", user);

		//ジャンル一覧
		mav.addObject("genre_list", g_service.GenreList());

		//スレッド名存在チェック
		if (t_service.ThreadTitle(title).size() >= 1) {
			//失敗
			mav.addObject("ans", "そのスレッド名は既に存在します。");
		} else {
			//成功
			//スレッド登録
			t_service.ThreadInsert(title, game, genre, user);
			//ゲーム登録
			game_service.GameInsert(genre, game);

			mav.addObject("thread", "新規スレッドURL");
			mav.addObject("ans", "スレッドを立ち上げました");

			//立ち上げスレッドID取得
			mav.addObject("threadID", t_service.ThreadID(title));
		}

		mav.setViewName("board/thread_add");

		return mav;
	}

	//スレッド内
	@GetMapping("/board")
	public ModelAndView Board(@RequestParam("t_id") int t_id, ModelAndView mav) {

		//セッション情報
		String user = securitySession.getUsername();
		mav.addObject("UserName", user);

		//スレッド情報検索
		mav.addObject("thread_info", t_service.ThreadInfo(t_id));

		//書き込み一覧
		mav.addObject("board_list", b_service.BoardList(t_id));

		//いいねした投稿ID一覧
		mav.addObject("good_list", b_service.GoodList(user, t_id));

		mav.setViewName("board/board");

		return mav;
	}

	//スレッド内書き込み
	@PostMapping("/board")
	public ModelAndView BoardWrite(@RequestParam(name = "textarea", required = false) String write, //書き込み
			@RequestParam(name = "good", required = false) String good, //いいね
			@RequestParam(name = "del_good", required = false) String del_good, //いいね解除
			@RequestParam(name = "b_id", required = false) String b_id, //書き込みID
			@RequestParam(name = "res_id", required = false) String res_id, //レスID
			@RequestPart(name = "img", required = false) MultipartFile file, //アップ画像
			@RequestParam("t_id") int t_id, //スレッドID
			ModelAndView mav) throws IOException {

		//同名画像チェック用
		int i;

		//セッション情報
		String user = securitySession.getUsername();
		mav.addObject("UserName", user);

		//スレッド情報検索
		mav.addObject("thread_info", t_service.ThreadInfo(t_id));

		//書き込み用変数
		String t_title = t_service.ThreadInfo(t_id).getTitle();//スレッドタイトル
		String t_game = t_service.ThreadInfo(t_id).getGame();//スレッドゲーム
		String t_genre = t_service.ThreadInfo(t_id).getGenre();//スレッドジャンル
		

		//掲示板書き込み
		if (write != null) {

			String image = file.getOriginalFilename();//画像投稿
			if (image.isEmpty()) {
				image = "none";
				//書き込み追加
				b_service.BoardWrite(t_id, t_title, t_game, t_genre, user, write, image);
			} else {

				//画像名かぶりチェック
				//かぶりなし
				if (b_service.BoardImage(image).size() <= 0 || image.equals("none")) {
					
					
					//アップロード
					Path dst = Path.of("/opt/apache-tomcat-10.1.30/webapps/GameBoardFP/WEB-INF/classes/static/upload/",
							file.getOriginalFilename());
					Files.copy(file.getInputStream(), dst);

					//書き込み追加
					b_service.BoardWrite(t_id, t_title, t_game, t_genre, user, write, image);
				} else {
					//かぶり
					for (i = 1;; i++) {
						if (b_service.BoardImage(image + "(" + i + ")").size() <= 0) {
							break;
						}
					}
					//アップロード
					Path dst = Path.of("/opt/apache-tomcat-10.1.30/webapps/GameBoardFP/WEB-INF/classes/static/upload/",
							file.getOriginalFilename() + "(" + i + ")");
					Files.copy(file.getInputStream(), dst);

					//書き込み追加
					b_service.BoardWrite(t_id, t_title, t_game, t_genre, user, write, image + "(" + i + ")");

				}

			}

			mav.addObject("write", "write");
		}

		//いいね追加
		if (good != null) {
			b_service.GoodAdd(b_id, t_id, t_title, t_game, t_genre, user);

			mav.addObject("res_id", Integer.parseInt(res_id));
		}

		//いいね解除
		if (del_good != null) {
			b_service.GoodDel(b_id, user);

			mav.addObject("res_id", Integer.parseInt(res_id));
		}

		//書き込み一覧
		mav.addObject("board_list", b_service.BoardList(t_id));

		//いいねした投稿ID一覧
		mav.addObject("good_list", b_service.GoodList(user, t_id));

		mav.setViewName("board/board");

		return mav;
	}

	//履歴一覧
	@GetMapping("/history")
	public ModelAndView History(ModelAndView mav) {

		mav.setViewName("board/history");

		return mav;
	}

	//書き込み履歴
	@GetMapping("/history_write")
	public ModelAndView HistoryWrite(ModelAndView mav) {

		//セッション情報
		String user = securitySession.getUsername();
		mav.addObject("UserName", user);

		//書き込み履歴一覧
		mav.addObject("w_history_list", h_service.W_HistoryList(user));

		//書き込み件数
		mav.addObject("w_history_count", h_service.WriteCount(user));

		//いいね総数
		mav.addObject("all_good", h_service.GoodCount(user));

		mav.setViewName("board/history_write");

		return mav;
	}

	//いいね履歴
	@GetMapping("/history_good")
	public ModelAndView HistoryGood(ModelAndView mav) {

		//セッション情報
		String user = securitySession.getUsername();

		//いいね履歴一覧
		mav.addObject("history_list", h_service.G_HistoryList(user));

		mav.setViewName("board/history_good");

		return mav;
	}

}
