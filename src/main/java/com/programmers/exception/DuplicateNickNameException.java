package com.programmers.exception;

public class DuplicateNickNameException extends RuntimeException{
    public DuplicateNickNameException(){
        super("중복된 이메일입니다.");
    }
}
