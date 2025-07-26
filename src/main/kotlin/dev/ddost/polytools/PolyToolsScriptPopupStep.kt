package dev.ddost.polytools

import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep

class PolyToolsScriptPopupStep(
    title: String?,
    scripts: List<PolyToolScript>,
    private val onChosen: (PolyToolScript) -> Unit
) : BaseListPopupStep<PolyToolScript>(title, scripts) {
    override fun onChosen(selectedValue: PolyToolScript, finalChoice: Boolean): PopupStep<*>? {
        onChosen(selectedValue)
        return FINAL_CHOICE // FINAL_CHOICE означает "закрыть это окно"
    }

    override fun getTextFor(value: PolyToolScript): String {
        return value.name
    }
}
