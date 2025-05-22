package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Board;
import com.example.repository.BoardRepository;
import com.example.repository.HistoryRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository re_board;//Boardのリポジトリ

	@Autowired
	private HistoryRepository re_history;//Historyのリポジトリ

	//書き込み一覧
	public List<Board> BoardList(int t_id,int page, int size) {
		
		int offset = page * size;

		List<Board> board_list = re_board.findByBoard(t_id,size,offset);

		return board_list;
	}

	//ページ総数
	public int getTotalPages(int t_id,int pageSize) {
		List<Integer> totalCountAns = re_board.findByBoardCount(t_id);
        int totalCount = totalCountAns.get(0);
        return (int) Math.ceil((double) totalCount / pageSize);
    }

	//いいねした投稿ID一覧
	public List<Integer> GoodList(String user, int t_id) {

		List<Integer> good_list = re_history.findByLike(user, t_id);

		return good_list;
	}

	//書き込み追加
	public void BoardWrite(int t_id, String t_title, String t_game, String t_genre, String user,
			String write, String image) {
		re_board.insertByWrite(t_id, t_title, t_game, t_genre, user, ResId(t_id) + 1,
				write, image);
	}

	//各書き込みID取得
	public int ResId(int t_id) {

		List<Board> res = re_board.findByBoard(t_id,9999,0);

		return res.size();
	}

	//画像名かぶりチェック
	public List<Board> BoardImage(String image) {

		List<Board> image_info = re_board.findByImage(image);

		return image_info;
	}

	//いいね追加
	public void GoodAdd(String b_id, int t_id, String t_title, String t_game, String t_genre, String user) {
		re_board.updateByGood(Integer.parseInt(b_id));
		re_history.insertByHistory(Integer.parseInt(b_id), t_id, t_title, t_game, t_genre, user);
	}

	//いいね解除
	public void GoodDel(String b_id,String user) {
		re_board.updateByDel_Good(Integer.parseInt(b_id));
		re_history.deleteByHistory(Integer.parseInt(b_id), user);
	}
	
	//スレッド削除による全書き込み削除
	public void LossThread(int t_id) {
		re_board.DeleteWriteByThreadLoss(t_id);
	}
}
