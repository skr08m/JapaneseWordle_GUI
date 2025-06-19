package model;

import java.util.HashSet;
import java.util.Set;

public class HiraganaModel {
    public final Set<Character> ALL_HIRAGANA = new HashSet<>();
    public final Set<Character> EXCLUDE_HIRAGANA = new HashSet<>();

    public HiraganaModel() {
    	for (char c = 'あ'; c <= 'ん'; c++) {
            ALL_HIRAGANA.add(c);
        }

        char[] smallHiragana = {
            'ぁ', 'ぃ', 'ぅ', 'ぇ', 'ぉ',
            'ゃ', 'ゅ', 'ょ', 'ゎ', 'っ','を'
        };
        for (char c : smallHiragana) {
            EXCLUDE_HIRAGANA.add(c);
        }

        String dakuten = "がぎぐげござじずぜぞだぢづでどばびぶべぼ";
        for (char c : dakuten.toCharArray()) {
            ALL_HIRAGANA.add(c);
        }

        String handakuten = "ぱぴぷぺぽ";
        for (char c : handakuten.toCharArray()) {
            ALL_HIRAGANA.add(c);
        }
	}
    
    public Set<Character> getHiraganaSet() {
		return new HashSet<>(ALL_HIRAGANA);
	}
    
    public Set<Character> getExcludeHiraganaSet() {
		return new HashSet<>(EXCLUDE_HIRAGANA);
	}
}