package Dictionary;

/**
 * Класс языка для словаря. Содержит поля {@link Lang#langCode} и {@link Lang#langTitle}.
 * @author yaros
 */
public class Lang implements Comparable<Lang> {
    
    /** Код языка. */
    private final String langCode;
    
    /** Название языка. */
    private String langTitle;
    
    /**
     * <p>Конструктор, принимающий строковые значения кода и названия языка.</p>
     * <p>Если {@code langCode} - пустая строка или null, устанавливает
     * {@link Lang#langCode} равным {@code "(no code)"} и {@link Lang#langTitle} равным {@code "Unknown language"}.</p>
     * <p>Если {@code langTitle} - null, устанавливает {@link Lang#langTitle} равным пустой строке.</p>
     * @param langCode код языка.
     * @param langTitle название языка.
     */
    Lang(String langCode, String langTitle) {
        if (langCode == null || langCode.equals("")) {
            this.langCode = "(no code)";
            this.langTitle = "Unknown language";
        } else {
            this.langCode = new String(langCode);
            if (langTitle == null)
                this.langTitle = "";
            else
                this.langTitle = new String(langTitle);
        }
    }
    
    /**
     * Возвращает копию поля {@link Lang#langCode}.
     * @return {@code String} код языка.
     */
    public String getCode() {
        return new String(langCode);
    }
    
    /**
     * Возвращает копию поля {@link Lang#langTitle}.
     * @return {@code String} название языка.
     */
    public String getTitle() {
        return new String(langTitle);
    }
    
    /**
     * Изменяет поле {@link Lang#langTitle} на новое название языка.
     * @param langTitle новое название языка
     * @return {@code true}, если новый {@link Lang#langTitle} установлен;<br>
     * {@code false} в ином случае.
     */
    public boolean setLangTitle(String langTitle) {
        if (langTitle == null)
            return false;
        
        this.langTitle = langTitle;
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        Lang otherLang = (Lang)obj;
        return this.langCode.equals(otherLang.getCode()) &&
                this.langTitle.equals(otherLang.getTitle());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((langCode == null || langCode.equals("")) ? 0 : langCode.hashCode());
        result = prime * result +
                ((langTitle == null || langTitle.equals("")) ? 0 : langTitle.hashCode());
        return result;        
    }

    /**
     * Показывает, имеют ли языки одинаковые коды {@link Link#langCode}.
     * @param otherLang другой язык.
     * @return {@code true}, если языки имеют одинаковые {@link Lang#langCode};<br>
     * {@code false} в ином случае.
     */
    public boolean hasSameCodes(Lang otherLang) {
        return langCode.equals(otherLang.langCode);
    }
    
    /**
     * Показывает, имеют ли языки {@code firstLang} и {@code secondLang} одинаковые коды {@link Lang#langCode}.
     * @param firstLang первый язык
     * @param secondLang второй язык
     * @return {@code true}, если языки имеют одинаковые {@link Lang#langCode};<br>
     * {@code false} в ином случае.
     */
    public static boolean hasSameCodes(Lang firstLang, Lang secondLang) {
        return firstLang.hasSameCodes(secondLang);
    }
    
    @Override
    public int compareTo(Lang lang) {
        return langCode.compareTo(lang.langCode);
    }
    
    @Override
    public Lang clone() {
        return new Lang(new String(langCode), new String(langTitle));
    }
}
