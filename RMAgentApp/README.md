# RMAgent App - Aplikacja Android SDK

Aplikacja Android z małym modelem RM (Reasoning Model), agentem i przeglądarką internetową.

## Funkcje
- **Przeglądarka z 3 kartami** - otwieranie stron internetowych, logowanie, kopiowanie między kartami
- **Mind Map** - tworzenie map myśli z możliwością edycji, eksportu i importu
- **Agent RM** - mały model rozumowania odpowiedający na pytania
- **Notatki dzienne** - śledzenie codziennych działań i zdobytych umiejętności
- **Eksport/Import** - zapisywanie i wczytywanie danych

## Wymagania
- Android Studio Hedgehog (2023.1.1) lub nowszy
- Gradle 8.4
- Android SDK 34
- JDK 11 lub nowszy

## Budowanie APK

```bash
# Sklonuj projekt
cd RMAgentApp

# Zbuduj debug APK
./gradlew assembleDebug

# APK znajdziesz w:
# app/build/outputs/apk/debug/app-debug.apk
```

## Struktura projektu
```
RMAgentApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/rmagent/app/
│   │   │   ├── MainActivity.kt
│   │   │   ├── BrowserFragment.kt
│   │   │   ├── MindMapFragment.kt
│   │   │   ├── MindMapView.kt
│   │   │   ├── AgentFragment.kt
│   │   │   ├── AgentRM.kt
│   │   │   ├── NotesFragment.kt
│   │   │   ├── NoteAdapter.kt
│   │   │   ├── MindMapNode.kt
│   │   │   └── Note.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Użycie
1. Uruchom aplikację na urządzeniu lub emulatorze
2. Użyj przeglądarki do otwierania 3 stron jednocześnie
3. Loguj się na stronach - dane zapisywane są lokalnie
4. W zakładce Mind Map dodawaj węzły (podwójne kliknięcie do edycji)
5. Zadawaj pytania Agentowi RM
6. Zapisuj dzienne notatki i eksportuj je

## Licencja
MIT
