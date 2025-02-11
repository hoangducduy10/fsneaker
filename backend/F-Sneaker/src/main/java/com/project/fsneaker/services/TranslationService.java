package com.project.fsneaker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationService {
    private final MessageSource messageSource;

    @Autowired
    public TranslationService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String translate(String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            // Nếu không tìm thấy translation, trả về argument đầu tiên
            return args != null && args.length > 0 ? args[0].toString() : key;
        }
    }
}
