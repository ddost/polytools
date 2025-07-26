package dev.ddost.polytools

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType

class PolyToolsAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor: Editor? = e.getData(CommonDataKeys.EDITOR)
        if (editor == null || editor.selectionModel.selectedText.isNullOrBlank()) {
            return
        }
        val selectedText = editor.selectionModel.selectedText!!
        val scripts = ScriptManager.allScripts

        val step = PolyToolsScriptPopupStep("Choose Action", scripts) { selectedScript ->
            val scriptResult = selectedScript.execute(selectedText)

            when (scriptResult) {
                is ScriptResult.Success -> {
                    WriteCommandAction.runWriteCommandAction(project) {
                        val selectionStart = editor.selectionModel.selectionStart
                        val selectionEnd = editor.selectionModel.selectionEnd
                        editor.document.replaceString(selectionStart, selectionEnd, scriptResult.value)
                    }
                }
                is ScriptResult.Failure -> {
                    NotificationGroupManager.getInstance()
                        .getNotificationGroup("PolyTools Notifications") // Используем ID из plugin.xml
                        .createNotification("PolyTools script error", scriptResult.message, NotificationType.ERROR)
                        .notify(project)
                }
            }
        }

        val popup = JBPopupFactory.getInstance().createListPopup(step)
        popup.showInBestPositionFor(editor)
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible = editor?.selectionModel?.hasSelection() == true
    }
}
