import javax.swing.SwingUtilities;

import model.WordleModel;
import view.WordleView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordleModel model = new WordleModel();
            WordleView view = new WordleView(model);
            view.setVisible(true);
        });
    }
}
