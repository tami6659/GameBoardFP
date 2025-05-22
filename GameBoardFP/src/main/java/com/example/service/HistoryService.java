package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Board;
import com.example.entity.History;
import com.example.repository.BoardRepository;
import com.example.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
	private BoardRepository re_board;//Boardのリポジトリ

	@Autowired
	private HistoryRepository re_history;//Historyのリポジトリ

	//書き込み履歴一覧
	public List<Board> W_HistoryList(String user) {

		List<Board> w_history_list = re_board.findByW_history(user);

		return w_history_list;
	}

	//書き込み件数
	public List<Integer> WriteCount(String user) {

		List<Integer> w_history_count = re_board.findByW_h_count(user);

		return w_history_count;
	}

	//いいね総数
	public List<Integer> GoodCount(String user) {

		List<Integer> good_count = re_board.findByAllGood(user);

		return good_count;
	}

	//いいね履歴一覧
	public List<History> G_HistoryList(String user) {

		List<History> g_history_list = re_history.findByHistory(user);

		return g_history_list;
	}

	//スレッド削除によるいいね履歴削除
	public void LossThread(int t_id) {
		re_history.DeleteHistoryByThreadLoss(t_id);
	}
}
