package api;

import settings.Setting;

import java.util.HashMap;
import java.util.Iterator;


public class SendTranslator extends Setting {
    private HashMap<String, String> dictionary = new HashMap<String, String>();

    /**
     * Метод для перевода текста, который заменяет найденные dictionary слова на их перевод.
     *
     * @param text текст, слова которого будут переведены, если они присутствуют в словаре
     * @return переведенный текст
     */
    String translate(String text) {
        /*
         * Перевод текста с помощью словаря dictionary
         * В цикле строке key присваивается ключ из HashMap
         * Строка value значение, которое хранится по ключу
         * В тексте находится ключ и вместо него подставляется значение value
         */
        String translate = text;
        Iterator iterator = dictionary.keySet().iterator();
        String key;
        String value;
        while (iterator.hasNext()) {
            key = iterator.next().toString();
            value = dictionary.get(key);
            translate = translate.replace(key, value);
        }
        return translate;
    }

    /**
     * Метод, который добавляет в словарь новое слово и его перевод.
     *
     * @param word          слово, которое необходимо добавить в словарь
     * @param translateWord слово, на которое будет изменен параметр word (перевод слова) после вызова translate
     */
    public SendTranslator setLex(String word, String translateWord) {
        dictionary.put(word, translateWord);
        return this;
    }

    /**
     * Метод, который удаляет слово и перевод из словаря.
     *
     * @param word слово, которое необходимо удалить из словаря
     */
    public void removeLex(String word) {
        dictionary.remove(word);
    }

    /**
     * Метод, который возвращяет HashMap словаря для ручного внесения изменений.
     *
     * @return HashMap словаря, для ручного внесения изменений
     */
    public HashMap getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }
}
