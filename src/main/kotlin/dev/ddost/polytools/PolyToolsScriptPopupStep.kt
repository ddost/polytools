package dev.ddost.polytools

import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.SpeedSearchFilter
import com.intellij.openapi.ui.popup.util.BaseListPopupStep

class PolyToolsScriptPopupStep(
    title: String?,
    scripts: List<PolyToolScript>,
    private val onChosen: (PolyToolScript) -> Unit
) : BaseListPopupStep<PolyToolScript>(title, scripts) {
    override fun onChosen(selectedValue: PolyToolScript, finalChoice: Boolean): PopupStep<*>? {
        onChosen(selectedValue)
        return FINAL_CHOICE
    }

    override fun getTextFor(value: PolyToolScript): String {
        return value.name
    }

    override fun isSpeedSearchEnabled(): Boolean {
        return true
    }

//    override fun getSpeedSearchFilter(): SpeedSearchFilter<PolyToolScript?>? {
//        return super.getSpeedSearchFilter()
//    }
}
