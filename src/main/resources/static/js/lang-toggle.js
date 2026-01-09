(() => {
  const STORAGE_KEY = 'siteLang';
  const GOOGLE_SCRIPT_ID = 'google-translate-script';

  const getStoredLang = () => localStorage.getItem(STORAGE_KEY) || 'ko';

  const updateToggleLabel = (lang) => {
    document.querySelectorAll('[data-lang-toggle]').forEach((toggle) => {
      if (lang === 'en') {
        toggle.setAttribute('aria-label', '한국어로 보기');
      } else {
        toggle.setAttribute('aria-label', 'View in English');
      }
    });
  };

  const setSelectLanguage = (lang) => {
    const select = document.querySelector('.goog-te-combo');
    if (!select) {
      return false;
    }
    if (select.value !== lang) {
      select.value = lang;
      select.dispatchEvent(new Event('change'));
    }
    return true;
  };

  const applyLanguage = (lang, skipStore = false) => {
    const normalized = lang === 'en' ? 'en' : 'ko';
    document.documentElement.lang = normalized;
    if (!skipStore) {
      localStorage.setItem(STORAGE_KEY, normalized);
    }
    updateToggleLabel(normalized);

    let attempts = 0;
    const interval = setInterval(() => {
      if (setSelectLanguage(normalized) || attempts > 20) {
        clearInterval(interval);
      }
      attempts += 1;
    }, 250);
  };

  const toggleLanguage = () => {
    const current = getStoredLang();
    applyLanguage(current === 'en' ? 'ko' : 'en');
  };

  window.googleTranslateElementInit = () => {
    if (!window.google || !window.google.translate) {
      return;
    }
    new window.google.translate.TranslateElement(
      {
        pageLanguage: 'ko',
        includedLanguages: 'en,ko',
        autoDisplay: false,
      },
      'google_translate_element',
    );
    applyLanguage(getStoredLang(), true);
  };

  const loadGoogleTranslate = () => {
    if (document.getElementById(GOOGLE_SCRIPT_ID)) {
      return;
    }
    const script = document.createElement('script');
    script.id = GOOGLE_SCRIPT_ID;
    script.src = 'https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit';
    script.async = true;
    document.head.appendChild(script);
  };

  document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('[data-lang-toggle]').forEach((toggle) => {
      toggle.addEventListener('click', toggleLanguage);
      toggle.addEventListener('keydown', (event) => {
        if (event.key === 'Enter' || event.key === ' ') {
          event.preventDefault();
          toggleLanguage();
        }
      });
    });

    updateToggleLabel(getStoredLang());
    loadGoogleTranslate();
  });
})();
