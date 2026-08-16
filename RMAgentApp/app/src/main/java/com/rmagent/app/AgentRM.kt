package com.rmagent.app

class AgentRM {

    private val responses = mapOf(
        "cześć" to "Cześć! Jestem Agentem RM. W czym mogę Ci pomóc?",
        "co to rm" to "RM to mały model rozumowania, który pomaga Ci organizować myśli i tworzyć mapy myśli.",
        "mind map" to "Mapa myśli to świetny sposób na wizualizację pomysłów. Spróbuj użyć zakładki 'Mind Map'.",
        "pomocy" to "Aby rozpocząć: 1) Dodaj węzły w mapie myśli, 2) Zadaj pytanie agentowi, 3) Dodawaj notatki.",
        "notatki" to "Możesz zapisywać notatki dzienne i eksportować je do pliku.",
        "przeglądarka" to "W przeglądarce możesz otwierać 3 strony jednocześnie i logować się na nie.",
        "dziękuję" to "Nie ma za co! Miłego korzystania z aplikacji."
    )

    fun ask(question: String): String {
        val normalized = question.lowercase().trim()
        responses.forEach { (key, value) ->
            if (normalized.contains(key)) {
                return value
            }
        }
        return generateResponse(question)
    }

    private fun generateResponse(question: String): String {
        val templates = listOf(
            "To interesujące pytanie: '$question'. Spróbuj podzielić to na mniejsze części w mapie myśli.",
            "Na podstawie Twojego zapytania sugeruję: '$question' - warto to rozwinąć w węzłach.",
            "Agent RM rozważa: '$question'. Możesz dodać to jako węzeł główny w mapie myśli.",
            "Analizuję: '$question'. Proponuję stworzenie struktury myśli wokół tego tematu."
        )
        return templates.random()
    }
}
