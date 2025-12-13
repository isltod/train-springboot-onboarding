package com.asdf.minilog.Exception;

public class ArticleNotFoundException extends RuntimeException
{
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
