package controller;

import view.WordleView;

public class HintButtonController {
    public void handleHint(WordleView view) {
        view.switchHintVisible();
        view.setStatusMessage("ヒント一覧を表示しました");
    }
}
