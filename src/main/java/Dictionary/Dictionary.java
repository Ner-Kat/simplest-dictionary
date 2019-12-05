package Dictionary;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Класс словаря, хранящего все добавленные слова на любых добавленных языках и
 * их переводы. Для составления таблиц словарей (переводов с одного языка на
 * другой) используется представление в виде объекта {@link DictionaryTable}.
 * @author yaros
 */
public class Dictionary {
    
    /** Список языков словаря. */
    private final LangSet langSet;
    
    /** Словарь, содержащий список ключей-слов {@code Word} и значений-списков ссылок на переводы {@code HashSet}. */
    private final HashMap<Word, HashSet<Word>> words = new HashMap<>();
    
    /**
     * Конструктор словаря, устанавливающий копию {@lang} в качестве первого языка словаря.
     * @param lang язык.
     */
    Dictionary(Lang lang) {
        if (lang == null)
            langSet = new LangSet(new Lang(null, null));
        else
            langSet = new LangSet(lang);
    }
    
    /**
     * Конструктор словаря, создающий новый язык с кодом {@code langCode} и названием {@code langTitle}
     * и устанавливающий его в качестве первого языка словаря.
     * @param langCode код языка.
     * @param langTitle название языка.
     */
    Dictionary(String langCode, String langTitle) {
        langSet = new LangSet(new Lang(langCode, langTitle));
    }
    
    /**
     * Создаёт новый язык с кодом {@code langCode} и названием {@code langTitle}
     * и добавляет его в список языков {@code langSet} словаря.
     * @param langCode код языка.
     * @param langTitle название языка.
     * @return 
     */
    public boolean addLang(String langCode, String langTitle) {
        return langSet.addLang(new Lang(langCode, langTitle));
    }
    
    /**
     * Добавляет копию языка {@code lang} в список языков {@code langSet} словаря.
     * @param lang язык.
     * @return 
     */
    public boolean addLang(Lang lang) {
        return langSet.addLang(lang);
    }
    
    /**
     * Создаёт новое слово с текстом {@code word} на языке {@code lang} и добавляет
     * его в словарь.
     * @param word текст добавляемого слова.
     * @param lang язык добавляемого слова.
     * @return {@code true}, если новое слово успешно добавлено;<br>
     * {@code false} в ином случае.
     */
    public boolean addWord(String word, Lang lang) {
        if (word == null || word.equals("") || lang == null)
            return false;
        
        return addWord(new Word(word, lang));
    }
    
    /**
     * Добавляет копию слова {@code word} в словарь.
     * @param word добавляемое слово.
     * @return {@code true}, если новое слово успешно добавлено;<br>
     * {@code false} в ином случае.
     */
    public boolean addWord(Word word) {
        if (word == null || !langSet.contains(word.getLang()))
            return false;
        
        words.put(word.clone(), new HashSet<>());
        return (words.get(word) != null);
    }

    /**
     * Удаляет слово {@code word} из словаря. Также удаляет список {@code HashSet} ссылок
     * на переводы слова {@code word} и удаляет ссылки на него из списков переводов других слов.
     * @param word удаляемое слово.
     * @return {@code true}, если слово успешно удалено;<br>
     * {@code false}, если {@code word} равно {@code null} или не содержится в словаре.
     */
    public boolean removeWord(Word word) {
        if (word == null || !words.containsKey(word))
            return false;
        
        HashSet<Word> wordTranslations = words.get(word);
        for (Word w : wordTranslations) {
            words.get(w).remove(word);
        }
        
        words.remove(word);
        
        return true;
    }
    
    /**
     * Удаляет слово с текстом {@code word} на языке {@code lang} из словаря. Также удаляет 
     * список {@code HashSet} ссылок на переводы слова {@code word} и удаляет ссылки на него
     * из списков переводов других слов.
     * @param word текст удаляемого слова.
     * @param lang язык удаляемого слова.
     * @return {@code true}, если слово успешно удалено;<br>
     * {@code false}, если {@code word} или {@code lang} равно {@code null};
     * {@code false}, если слово с таким текстом и на таком языке не содержится в словаре.
     */
    public boolean removeWord(String word, Lang lang) {
        if (lang == null || word == null)
            return false;
        
        return removeWord(new Word(word, lang));
    }
    
    /**
     * Добавляет {@code translationWord} к списку переводов слова {@code word}.<br>
     * Добавляет слово {@code translationWord} в словарь, если оно в словаре отсутствует.
     * @param word переводимое слово.
     * @param translationWord перевод.
     * @return {@code true}, если перевод успешно добавлен;<br>
     * {@code false} в ином случае.
     */
    public boolean addTranslation(Word word, Word translationWord) {
        if ((word == null || !langSet.contains(word.getLang())) ||
                translationWord == null || !langSet.contains(translationWord.getLang()))
            return false;
        
        HashSet<Word> wordTranslations = words.get(word);
        if (wordTranslations.add(translationWord)) {
        
            boolean translationWordAdded = false;
            if (!words.containsKey(translationWord))
                translationWordAdded = addWord(translationWord);
            
            if (translationWordAdded && words.get(translationWord).add(word))
                return true;
            
            if (translationWordAdded)
                removeWord(translationWord);
            wordTranslations.remove(translationWord);
            
        }
        
        return false;
    }
    
    /**
     * Добавляет слово с текстом {@code translationWord} на языке {@code translationLang}
     * к списку переводов слова {@code word}.<br>
     * Добавляет слово с текстом {@code translationWord} на языке {@code translationLang}
     * в словарь, если оно в словаре отсутствует.
     * @param word переводимое слово.
     * @param translationWord текст перевода.
     * @param translationLang язык перевода.
     * @return {@code true}, если перевод успешно добавлен;<br>
     * {@code false} в ином случае.
     */
    public boolean addTranslation(Word word, String translationWord, Lang translationLang) {
        if (word == null || translationWord == null || translationLang == null)
            return false;
        
        return addTranslation(word, new Word(translationWord, translationLang));
    }
    
    /**
     * Удаляет язык {@code lang} из списка языков словаря и удаляет из словаря все слова на этом языке.
     * @param lang удаляемый язык.
     * @return {@code true}, если язык и все слова на этом языке успешно удалены из словаря;<br>
     * {@code false} в ином случае.
     */
    public boolean removeLang(Lang lang) {
        if (lang == null || !langSet.contains(lang))
            return false;

        for (Word w : new HashSet<Word>(words.keySet())) {
            if (w.getLang().equals(lang)) {
                removeWord(w);
            }
        }
        
        langSet.removeLang(lang);
        
        return true;
    }
    
    /**
     * Возвращает список слов на языке {@code lang}.
     * @param lang язык.
     * @return {@code HashSet} список слов.
     */
    private HashSet<Word> getWordsByLang(Lang lang) {
        if (lang == null || !langSet.contains(lang))
            return null;
        
        HashSet<Word> result = new HashSet<>();
        for (Word w : words.keySet()) {
            if (w.getLang().equals(lang)) {
                result.add(w);
            }
        }
        
        return result;
    }
    
    
    /**
     * Создаёт и возвращает список копий слов на языке {@code lang}.
     * @param lang язык.
     * @return {@code HashSet} список копий слов.
     */
    public HashSet<Word> biuldWordsSetByLang(Lang lang) {
        if (lang == null || !langSet.contains(lang))
            return null;
        
        HashSet<Word> result = new HashSet<>();
        for (Word w : words.keySet()) {
            if (w.getLang().equals(lang)) {
                result.add(w.clone());
            }
        }
        
        return result;        
    }
    
    /**
     * Строит {@link DictionaryTable} таблицу словаря с копиями слов на языке
     * {@code langFirst} (исходном языке) и списками копий их переводов на 
     * язык {@code langSec} (язык перевода).
     * @param langFirst исходный язык.
     * @param langSec язык перевода.
     * @return {@code DictionaryTable} таблицу словаря переводов.
     */
    public DictionaryTable buildDictionary(Lang langFirst, Lang langSec) {
        if (langFirst == null || langSec == null ||
                !langSet.contains(langFirst) || !langSet.contains(langSec))
            return null;
        
        HashMap<Word, HashSet<Word>> result = new HashMap<>();
        
        HashSet<Word> wordsFirstLang = getWordsByLang(langFirst);
        if (wordsFirstLang == null)
            return null;
        
        for (Word w : wordsFirstLang) {
            HashSet<Word> wTranslations = new HashSet<>();
            for (Word tr : words.get(w)) {
                if (tr.getLang().equals(langSec)) {
                    wTranslations.add(tr.clone());
                }
            }
            
            if (!wTranslations.isEmpty()) {
                result.put(w.clone(), wTranslations);
            }
        }
        
        return new DictionaryTable(result);
    }
    
    /**
     * Возвращает копию поля {@link Dictionary#langSet}.
     * @return {@code LangSet} список языков словаря.
     */
    public LangSet getLangs() {
        return langSet.clone();
    }

}
