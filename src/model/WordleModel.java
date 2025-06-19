package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class WordleModel {
	private List<String> words;
	Set<String> wordSet = new TreeSet<String>();
	private String problemAnswer;//こたえ
	private Random rand = new Random();
	String filename = "wordList.txt";
	List<Set<Character>> allowedChars = new ArrayList<>();


	public WordleModel(){
		getWordList(filename);
		listInitialization();
		System.out.println(problemAnswer);
		System.out.println(words.size());
	}

	//単語リストを返す
	public List<String> getWordList(String filename) {
		final int index = 13;//ひらがなのある場所
		final int typeIndex = 3;//～する系の単語の排除用
		List<String> wordList;
		InputStream is = getClass().getClassLoader().getResourceAsStream(filename);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			String line;

			while ((line = br.readLine()) != null) {
				line = Normalizer.normalize(line, Normalizer.Form.NFC);
				line = line.trim();
				String[] tmpStrings = line.split(",");

				if (tmpStrings.length != 15) continue;
				
				if(tmpStrings[typeIndex].equals("用")) continue;

				String kanaWord = tmpStrings[index]; // ← ここで仮名語を取り出す

				if (kanaWord.length() != 5) continue;

				HiraganaModel hiraganaModel = new HiraganaModel();
				Set<Character> exclude = hiraganaModel.getExcludeHiraganaSet();
				boolean skip = false;
				for (int i = 0; i < kanaWord.length(); i++) {
					if (exclude.contains(kanaWord.charAt(i))) {
						skip = true;
						break;
					}
				}
				if (skip) continue;

				wordSet.add(kanaWord);
			}
		} catch (IOException e) {
			System.err.println("ファイルの読み込み中にエラーが発生しました: " + e.getMessage());
		}
		wordList = new ArrayList<String>(wordSet);
		return wordList;
	}

	public void updateHint(boolean[] isCollectPosition, boolean[] isContain, String userAnswer) {
		List<String> hintList = new ArrayList<>();
		int length = problemAnswer.length();

		// 全体で含む必要がある文字
		Set<Character> mustInclude = new HashSet<>();

		for (int i = 0; i < length; i++) {
			char ch = userAnswer.charAt(i);

			if (isCollectPosition[i]) {
				allowedChars.get(i).clear();
				allowedChars.get(i).add(ch);
			} else if (isContain[i]) {
				allowedChars.get(i).remove(ch);
				mustInclude.add(ch);
			} else {
				for (int j = 0; j < length; j++) {
					allowedChars.get(j).remove(ch);
				}
			}
		}

		outerLoop:
			for (String word : words) {
				for (int i = 0; i < length; i++) {
					if (!allowedChars.get(i).contains(word.charAt(i))) {
						continue outerLoop;
					}
				}

				for (char c : mustInclude) {
					if (countChar(word, c) == 0) {
						continue outerLoop;
					}
				}
				hintList.add(word);
			}
		words = hintList;
	}

	private int countChar(String s, char c) {
		int count = 0;
		for (char ch : s.toCharArray()) {
			if (ch == c) count++;
		}
		return count;
	}



	public List<String> getHints() {
		return words;
	}

	public String getAnswer() {
		return problemAnswer;
	}

	public boolean isContain(String text) {
		return wordSet.contains(text);
	}

	public void listInitialization() {
		words = new ArrayList<String>(wordSet);
		int index = rand.nextInt(words.size() - 1);
		problemAnswer = words.get(index);
		allowedChars.clear();
		for (int i = 0; i < 5; i++) {
			allowedChars.add(new HiraganaModel().getHiraganaSet());
		}
	}
}
