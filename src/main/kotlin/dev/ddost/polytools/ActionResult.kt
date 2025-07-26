package dev.ddost.polytools

sealed class ActionResult {
    /**
     * Указывает, что нужно заменить выделенный текст в редакторе.
     * @param value Новый текст для вставки.
     */
    data class ReplaceText(val value: String) : ActionResult()

    /**
     * Указывает, что нужно показать результат во всплывающем окне.
     * @param title Заголовок окна.
     * @param content Текст для отображения в окне.
     */
    data class ShowPopup(val title: String, val content: String) : ActionResult()

    /**
     * Указывает, что произошла ошибка.
     * @param message Сообщение об ошибке для уведомления.
     */
    data class Error(val message: String) : ActionResult()
}
