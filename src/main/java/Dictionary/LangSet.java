package Dictionary;

import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

/** Определяет тип сортировки языков в {@link LangSet}:<br>
 * {@code BY_CODE} - по коду;<br>
 * {@code BY_TITLE} - по названию.
 * @author yaros
 */
enum langsSortType { BY_CODE, BY_TITLE }

/**
 * Класс списка языков для словаря. Содержит поле {@link LangSet#langs}.
 * @author yaros
 */
public class LangSet {
    
    /** {@code HashSet} список языков {@link Lang}. */
    private HashSet<Lang> langs = new HashSet<>();
    
    /**
     * Конструктор списка языков для инициализации одним языком.
     * @param lang первый язык.
     */
    LangSet(Lang lang) {
        langs.add(lang.clone());
    }
    
    /**
     * Конструктор списка языков для инициализации двумя языками.
     * @param firstLang первый язык.
     * @param secondLang второй язык.
     */
    LangSet(Lang firstLang, Lang secondLang) {
        langs.add(firstLang.clone());
        if (firstLang.hasSameCodes(secondLang))
            return;
        langs.add(secondLang.clone());
    }
    
    /**
     * Определяет, может ли {@code code} являться кодом языка.<p>
     * Чтобы являться кодом языка, {@code code} не должен быть пуст или {@code null}.
     * @param code проверяемый код языка.
     * @return {@code true}, если {@code code} может являться кодом языка;<br>
     * {@code false} в ином случае.
     */
    private boolean canBeCode(String code) {
        return !(code == null || code.equals(""));
    }
    
    /**
     * <p>Вложенный класс нестандартного компаратора для списка языков {@link LangSet#langs}.</p>
     * <p>Переопределённый {@link LangComparator#compare} реализует сравнение
     * только по названию {@link Lang#langTitle} языка.</p>
     */
    class LangComparator implements Comparator<Lang> {
        
        @Override
        public int compare(Lang firstLang, Lang secondLang) {
            return firstLang.getTitle().compareTo(secondLang.getTitle());
        }
        
    }
    
    /**
     * Возвращает список копий языков, отсортированных по их кодам.
     * @return {@code Set} список языков.
     */
    public TreeSet<Lang> getLangSet() {
        return getLangSet(langsSortType.BY_CODE);
    }
    
    /**
     * Возвращает список копий языков, отсортированных в соответствии с выбранным типом сортировки:<br>
     * {@code BY_CODE} - сортировать по коду;<br>
     * {@code BY_TITLE} - сортировать по названию.
     * @param type тип сортировки.
     * @return {@code Set} список языков.
     */
    public TreeSet<Lang> getLangSet(langsSortType type) {
        TreeSet<Lang> result = null;
        
        if (type == langsSortType.BY_CODE) {
            result = new TreeSet<>(langs);
        } else if (type == langsSortType.BY_TITLE) {
            LangComparator comp = new LangComparator();
            result = new TreeSet<>(comp);
        } else {
            return null;
        }
        
        for (Lang lang : langs) {
            result.add(lang.clone());
        }
        
        return result;
    }
    
    /**
     * Возвращает копию объекта {@link Lang} из списка языков с кодом, эквивалентным {@code code}.
     * @param code код языка.
     * @return объект {@link Lang}, если язык с кодом {@code code} существует в списке;<br>
     * {@code null}, если такого языка в списке нет.
     */
    public Lang getLangByCode(String code) {
        if (!canBeCode(code))
            return null;
        
        for (Lang lang : langs) {
            if (lang.getCode().equals(code))
                return lang.clone();
        }
        
        return null;
    }
    
    /**
     * Возвращает {@code HashSet} список копий языков с названиями, эквивалентными {@code title}.
     * @param title название языка.
     * @return {@code HashSet} список языков с названиями {@code title}.
     */
    public HashSet<Lang> getLangsByTitle(String title) {
        if (title == null)
            return null;
        
        HashSet<Lang> result = new HashSet<>();
        for (Lang lang : langs) {
            if (lang.getTitle().equals(title))
                result.add(lang.clone());
        }
        
        return result;
    }
    
    /**
     * Показывает, сожержится ли язык {@code lang} в текущем списке языков.
     * @param lang язык.
     * @return {@code true}, если язык {@code lang} содержится в списке языков;<br>
     * {@code false} в ином случае.
     */
    public boolean contains(Lang lang) {
        return langs.contains(lang);
    }
    
    /**
     * Показывает, содержится ли язык с кодом {@code code} в текущем списке языков.
     * @param code код языка.
     * @return {@code true}, если язык с кодом {@code code} содержится в списке языков;<br>
     * {@code false} в ином случае;<br>
     * {@code false}, если {@code code} равен {@code null} или пустой строке.
     */
    private boolean hasLangCode(String code) {
        if (code == null || code.equals(""))
            return false;
        
        if (getLangByCode(code) != null)
            return true;
        
        return false;
    }
    
    /**
     * Показывает, содержится ли язык с кодом, равным коду языка {@code lang} в текущем списке языков.
     * @param lang язык.
     * @return {@code true}, если язык с кодом, равным коду языка {@code lang} содержится в списке языков;<br>
     * {@code false} в ином случае;<br>
     * {@code false}, если {@code lang} равен {@code null}.
     */
    private boolean hasLangCode(Lang lang) {
        if (lang == null)
            return false;
        
        return hasLangCode(lang.getCode());
    }
    
    /**
     * Добавляет язык {@code lang} в список языков.
     * @param lang язык.
     * @return {@code true}, если язык {@code lang} успешно добавлен;<br>
     * {@code false} в ином случае.
     */
    public boolean addLang(Lang lang) {
        if (lang == null || hasLangCode(lang))
            return false;
        
        return langs.add(lang.clone());
    }
    
    /**
     * Удаляет язык с кодом {@code code} из списка языков.
     * @param code код языка.
     * @return {@code true}, если язык с кодом {@code code} успешно удалён;<br>
     * {@code false} в ином случае.
     */
    public boolean removeLangByCode(String code) {
        if (!canBeCode(code))
            return false;
        
        Lang toDel = getLangByCode(code);
        if (toDel != null)
            return langs.remove(toDel);
        
        return false;
    }
    
    /**
     * Удаляет язык {@code lang} из списка языков.
     * @param lang язык.
     * @return {@code true}, если язык {@code lang} успешно удалён;<br>
     * {@code false} в ином случае.
     */
    public boolean removeLang(Lang lang) {
        if (lang == null)
            return false;
        
        return removeLangByCode(lang.getCode());
    }
    
    @Override
    public LangSet clone() {
        LangSet result = null;
        
        boolean isFirstIteration = false;
        for (Lang lang : langs) {
            if (isFirstIteration) {
                result = new LangSet(lang.clone());
            } else {
                result.addLang(lang.clone());
            }
        }
        
        return result;
    }
}
