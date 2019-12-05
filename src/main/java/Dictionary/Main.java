package Dictionary;

import java.util.HashSet;

/**
 * Главный класс, в методе main которого содержится код, демонстрирующий
 * примеры работы со словарём.
 * @author yaros
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("console.encoding", "Cp866");
        
        // Создание языка
        Lang enLang = new Lang("en", "English");
        printLang(enLang);
        Lang ruLang = new Lang("ru", "Русский");
        printLang(ruLang);
                
        // Создание словаря (инициализация хотя бы одним языком)
        Dictionary dictionary = new Dictionary(enLang);
        if (!addingLang(dictionary, ruLang))
            return;
        
        System.out.println();
        
        // Добавление слова и его перевода
        Word word = new Word("build", enLang);
        dictionary.addWord(word);
        dictionary.addTranslation(word, "строить", ruLang);
        
        // Получение таблицы словаря для двух выбранных языков
        DictionaryTable dict = dictionary.buildDictionary(ruLang, enLang);
        printDictionary(dict);
        
        System.out.println();

        // Добавление слова и получение списка всех слов на этом языке
        dictionary.addWord("словарь", ruLang);
        printWordsByLang(dictionary.biuldWordsSetByLang(ruLang));
        
        System.out.println();

        // Добавление третьего языка
        Lang deLang = new Lang("de", "Deutsch");
        printLang(deLang);
        if (!addingLang(dictionary, deLang))
            return;
        
        // Добавление перевода к слову, на которое нет ссылки
        dictionary.addTranslation(new Word("словарь", ruLang), new Word("wortschatz", deLang));
        printDictionary(dictionary.buildDictionary(deLang, ruLang));

        System.out.println();
        
        // Добавление переводов
        dictionary.addTranslation(word, "errichten", deLang);
        dictionary.addTranslation(word, "aufbauen", deLang);
        
        printDictionary(dictionary.buildDictionary(enLang, deLang));
        
        System.out.println();

        // Удаление слова и проверка его удаления путём постройки словаря и выводом списка слов языка
        dictionary.removeWord(word);
        System.out.println("Постройка словаря переводов с " + enLang.getTitle() + " на " + deLang.getTitle() + "...");
        printDictionary(dictionary.buildDictionary(enLang, deLang));
        
        System.out.println("Список слов на " + enLang.getTitle());
        printWordsByLang(dictionary.biuldWordsSetByLang(enLang));
        
        System.out.println();
        
        // Добавление слова к языку, на котором не осталось слов, как слова-перевода
        dictionary.addTranslation(new Word("errichten", deLang), new Word("dictionary", enLang));
        System.out.println("Список слов на " + enLang.getTitle());
        printWordsByLang(dictionary.biuldWordsSetByLang(enLang));
        printDictionary(dictionary.buildDictionary(deLang, enLang));
        System.out.println();
                
        // Удаление языка (вместе со всеми словами на этом языке)
        System.out.println("Список слов на " + ruLang.getTitle() + " до удаления");
        printWordsByLang(dictionary.biuldWordsSetByLang(ruLang));
        dictionary.removeLang(ruLang);

        // Проверка удаления языка #2
        System.out.println("Список слов на " + ruLang.getTitle() + " после удаления");
        printWordsByLang(dictionary.biuldWordsSetByLang(ruLang));
        
        System.out.println();
        
        // Проверка удаления языка #2
        System.out.print("Попытка добавить слово на " + ruLang.getTitle() + ": ");
        System.out.println(dictionary.addWord("слово", ruLang));
        
        System.out.println();
        
        // Добавление удалённого языка обратно
        dictionary.addLang(ruLang);
        dictionary.addWord("слово", ruLang);
        System.out.println("Список слов на " + ruLang.getTitle());
        printWordsByLang(dictionary.biuldWordsSetByLang(ruLang));
    }
    
    private static boolean addingLang(Dictionary dictionary, Lang lang) {
        if (dictionary.addLang(lang)) {
            System.out.println("Язык добавлен в словарь: " + lang.getTitle());
            return true;
        } else {
            System.out.println("Язык не удалось добавить в словарь: " + lang.getTitle());
            return false;
        }        
    }
    
    private static void printDictionary(DictionaryTable dict) {
        if (dict.size() < 1) {
            System.out.println("Словарь пуст");
            return;
        }
        
        for (Word w : dict.wordSet()) {
            System.out.print("Слово: " + w.getWord());
            System.out.print(" | Переводы: ");
            for (Word tr : dict.get(w)) {
                System.out.print(tr.getWord() + ", ");
            }
            System.out.println();
        }
    }
    
    private static void printLang(Lang lang) {
        System.out.println("Язык: " + lang.getCode() + ", " + lang.getTitle());
    }
    
    private static void printWordsByLang(HashSet<Word> words) {
        if (words == null || words.size() < 1) {
            System.out.println("Список слов на пуст");
            return;
        }
        
        for (Word w : words) {
            System.out.println(w.getWord());
        }
    }
    
}
