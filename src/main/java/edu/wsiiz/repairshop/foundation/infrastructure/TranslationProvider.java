package edu.wsiiz.repairshop.foundation.infrastructure;

import com.vaadin.flow.i18n.I18NProvider;
import java.text.MessageFormat;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TranslationProvider implements I18NProvider {

  public static final String BUNDLE_PREFIX = "messages";

  public final Locale LOCALE_PL = Locale.of("pl");
  public final Locale LOCALE_EN = Locale.of("en");

  private final List<Locale> locales = Collections
          .unmodifiableList(Arrays.asList(LOCALE_PL, LOCALE_EN));

  @Override
  public List<Locale> getProvidedLocales() {
    return locales;
  }

  @Override
  public String getTranslation(String key, Locale locale, Object... params) {
    if (key == null) {
      log.warn("Got lang request for key with null value!");
      return "";
    }
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
    String value;
    try {
      value = bundle.getString(key);
    } catch (final MissingResourceException e) {
      log.warn("Missing resource", e);
      return "!" + locale.getLanguage() + ": " + key;
    }
    if (params.length > 0) {
      value = MessageFormat.format(value, params);
    }
    return value;
  }
}