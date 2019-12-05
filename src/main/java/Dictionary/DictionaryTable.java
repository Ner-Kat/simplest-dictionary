package Dictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс таблицы словаря переводов с одного языка на другой. Содержит поле {@link DictionaryTable#dictionary}.
 * @author yaros
 */
public class DictionaryTable {
    
    /** {@link HashMap} словарь ключей-слов на исходном языке с {@code HashSet} списками переводов.  */
    private final HashMap<Word, HashSet<Word>> dictionary;

    /**
     * Конструктор таблицы словаря, устанавливающий {@code final} поле {@link DictionaryTable#dictionary}.
     * @param dictionary {@code HashMap} словарь ключей-слов на исходном языке с {@code HashSet} списками переводов.
     */
    public DictionaryTable(HashMap<Word, HashSet<Word>> dictionary) {
        this.dictionary = dictionary;
    }
    
    /**
     * Возвращает копию поля {@link DictionaryTable#dictionary}.
     * @return {@code HashMap} представление таблицы словаря.
     */
    public HashMap<Word, HashSet<Word>> hashMap() {
        HashMap<Word, HashSet<Word>> result = new HashMap<>();
        
        for (Word w : dictionary.keySet()) {
            HashSet<Word> translations = new HashSet<>();
            for (Word tr : dictionary.get(w)) {
                translations.add(tr.clone());
            }
            
            result.put(w.clone(), translations);
        }
        
        return result;
    }
    
    @Override
    public DictionaryTable clone() {
        return new DictionaryTable(hashMap());
    }
    
    /**
     * Показывает, содержит ли таблица словаря данное слово.
     * @param word слово.
     * @return {@code true}, если {@code word} содержится в таблице словаря,<br>
     * {@code false} в ином случае.
     */
    public boolean containsWord(Word word) {
        return dictionary.containsKey(word);
    }
    
    /**
     * Возвращает список копий переводов слова {@code word}.
     * @param word слово.
     * @return {@code HashSet} список переводов.
     */
    public HashSet<Word> get(Word word) {
        HashSet<Word> result = new HashSet<>();
        for (Word w : dictionary.get(word)) {
            result.add(w.clone());
        }
        
        return result;
    }
    
    /**
     * Показывает, пуста ли таблица словаря.
     * @return {@code true}, если таблица словаря пуста,<br>
     * {@code false} в ином случае.
     */
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }
    
    /**
     * Возвращает список копий слов {@code Word} на исходном языке таблицы словаря.
     * @return {@code Set} список слов.
     */
    public Set<Word> wordSet() {
        LinkedHashSet<Word> result = new LinkedHashSet<>();
        for (Word w : dictionary.keySet()) {
            result.add(w.clone());
        }
        
        return result;
    }
    
    /**
     * Возвращает длину словаря, т.е. количество слов на исходном языке в таблице словаря.
     * @return длина таблицы словаря.
     */
    public int size() {
        return dictionary.size();
    }
    
}
