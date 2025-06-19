package controller;

import java.awt.Color;
import java.util.List;

import model.SubmitModel;
import model.WordleModel;
import view.WordleView;

public class SubmitController {
	private SubmitModel submitModel = new SubmitModel();
	
	public void handleSubmit(WordleView view){
		if(submitModel.getClearState()) {
			return;
		}
			
		WordleModel wordleModel = view.getModel();
		String userAnswer = view.getAnswer();//ユーザーの回答
		String collectAnswer = view.getModel().getAnswer();//正しい答え
		
		if(!submitModel.isValid(userAnswer)) {
			view.setInputField("");
			return;
		}
		
		if(!wordleModel.isContain(userAnswer)) {
			view.setStatusMessage("回答リストにない単語です");
			return;
		}
		//回答履歴の更新処理、テキストフィールド初期化
		boolean[] collectPosition = submitModel.isCollectPosition(userAnswer, collectAnswer);
		boolean[] isContain = submitModel.isContain(userAnswer, collectAnswer);
		
		List<Color> fillColors = submitModel.getColors(collectPosition, isContain);
		view.updateGrid(submitModel.getIndex(), userAnswer, fillColors);
		submitModel.setNextIndex();
		view.setInputField("");
		
		//ヒント更新処理
		wordleModel.updateHint(collectPosition, isContain, userAnswer);
		List<String> hints = wordleModel.getHints();
		view.updateHintTable(hints);
		
		//クリア判定
		if(submitModel.isClear(fillColors)) {
			view.setStatusMessage("クリア");
			System.out.println("処理");
			return;
		}
		
		if(submitModel.isGameOver(view.ROWS)) {
			view.setStatusMessage("ゲームオーバー");
		}
		
	}
}
