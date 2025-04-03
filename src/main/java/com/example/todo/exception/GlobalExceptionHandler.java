package com.example.todo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 想定外の例外をキャッチ
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("想定外のエラーが発生しました: {}", ex.getMessage(), ex);

        model.addAttribute("errorMessage", "システムエラーが発生しました。時間をおいて再度お試しください。");
        return "error";
    }

    /**
     * 不正な入力などに対応する例外
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        log.warn("入力エラー: {}", ex.getMessage());

        model.addAttribute("errorMessage", "入力内容に誤りがあります。");
        return "error";
    }
}
