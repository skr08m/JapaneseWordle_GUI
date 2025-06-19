package controller;

import model.SubmitModel;
import model.WordleModel;
import view.WordleView;

public class ResetButtonController {
	SubmitModel submitModel = new SubmitModel();
	
    public void handleReset(WordleView view) {
    	WordleModel model = view.getModel();
    	
    	model.listInitialization();//viewのmodel初期化
        view.updateHintTable(model.getHints());//ヒントテーブルのデータ初期化
        view.initGrid();//回答履歴初期化
        submitModel.resetIndex();
        submitModel.resetIsClear();
        
        view.setInputField("");
        view.setStatusMessage("リセットしました。");
    }
}
