package Dictionary;

/**
 * Класс слова для словаря. Содержит поля {@link Word#word} и {@link Word#lang}.
 * @author yaros
 */
public class Word {
    
    /** Текст слова. */
    private String word;
    
    /** Язык слова. */
    private final Lang lang;
    
    /**
     * Показывает, может ли строка {@code word} быть словом.
     * @param word проверяемая на возможность быть словом строка.
     * @return {@code true}, если {@code word} может быть словом;<br>
     * {@code false} в ином случае.
     */
    private static boolean canBeWord(String word) {
        return !(word == null || word.equals(""));
    }
    
    /**
     * <p>Конструктор, принимающий строковое значение слова и язык слова.</p>
     * <p>Если {@code word} не может быть словом, {@link Word#word} примет значение {@code "no word"}.</p>
     * @param word текст слова.
     * @param lang язык.
     */
    public Word(String word, Lang lang) {
        if (canBeWord(word))
            this.word = new String(word);
        else
            this.word = "no word";
        this.lang = lang.clone();
    }
    
    /**
     * Возвращает копию поля {@link Word#word}.
     * @return {@code String} текст слова.
     */
    public String getWord() {
        return new String(word);
    }
    
    /**
     * Изменяет {@link Word#word} на {@code word}, если
     * {@code word} может быть словом ({@link Word#canBeWord}).
     * @param word новый текст слова.
     * @return {@code true}, если слово было изменено;<br>
     * {@code false} в ином случае.
     */
    public boolean setWord(String word) {
        if (!canBeWord(word))
            return false;
        
        this.word = word;
        return true;
    }
    
    /**
     * Возвращает копию поля {@link Word#lang}.
     * @return {@code Lang} язык слова.
     */
    public Lang getLang() {
        return lang.clone();
    }
    
    /**
     * Показывает, имеют ли текущее слово и слово {@code word} одинаковые языки.
     * @param word слово.
     * @return {@code true}, если языки у текущего слова и {@code word} одинаковы;<br>
     * {@code false} в ином случае.
     */
    private boolean hasSameLang(Word word) {
        return lang.equals(word.getLang());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        Word otherWord = (Word)obj;
        return this.word.equals(otherWord.getWord()) &&
                this.lang.equals(otherWord.getLang());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((word.equals("") || word == null) ? 0 : word.hashCode());
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        return result;
    }
    
    @Override
    public Word clone() {
        return new Word(new String(word), lang.clone());
    }
}
