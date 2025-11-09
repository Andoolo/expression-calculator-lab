package com.Andrey.calculator;

/**
 * Представляет токен в математическом выражении
 */
public class Token {
    private final TokenType type;
    private final String value;

    /**
     * Создает новый токен
     * @param type тип токена
     * @param value значение токена
     */
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return тип токена
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return значение токена
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}
