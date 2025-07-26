package dev.ddost.polytools

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.ui.components.JBScrollPane
import java.awt.Dimension
import javax.swing.JTextArea

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
            val actionResult = selectedScript.execute(selectedText)
            when (actionResult) {
                is ActionResult.ReplaceText -> {
                    WriteCommandAction.runWriteCommandAction(project) {
                        val selection = editor.selectionModel
                        editor.document.replaceString(selection.selectionStart, selection.selectionEnd, actionResult.value)
                    }
                }
                is ActionResult.ShowPopup -> {
                    showResultInPopup(editor, actionResult.title, actionResult.content)
                }
                is ActionResult.Error -> {
                    NotificationGroupManager.getInstance()
                        .getNotificationGroup("PolyTools Notifications")
                        .createNotification("PolyTools error", actionResult.message, NotificationType.ERROR)
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

    private fun showResultInPopup(editor: Editor, title: String, content: String) {
        val textArea = JTextArea(content).apply {
            isEditable = false
            lineWrap = true
            wrapStyleWord = true
        }
        val scrollPane = JBScrollPane(textArea)
        JBPopupFactory.getInstance()
            .createComponentPopupBuilder(scrollPane, textArea)
            .setTitle(title)
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .setMinSize(Dimension(600, 400))
            .createPopup()
            .showInCenterOf(editor.component)
    }
}
