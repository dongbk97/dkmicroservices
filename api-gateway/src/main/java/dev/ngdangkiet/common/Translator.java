package dev.ngdangkiet.common;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author ngdangkiet
 * @since 11/10/2023
 */

@Component
@RequiredArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String translate(String message, Locale locale) {
        return messageSource.getMessage(message, null, ObjectUtils.defaultIfNull(locale, LocaleContextHolder.getLocale()));
    }
}
