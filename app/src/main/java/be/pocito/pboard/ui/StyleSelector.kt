package be.pocito.pboard.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import be.pocito.pboard.R
import be.pocito.pboard.style.FontStyle
import be.pocito.pboard.style.FontStyleTransformer

/**
 * PopupWindow showing all Unicode font styles in a grid.
 * Uses PopupWindow instead of Dialog to work properly in IME context.
 */
class StyleSelector(
    private val context: Context,
    private val currentStyle: FontStyle,
    private val onStyleSelected: (FontStyle) -> Unit,
) {
    companion object {
        private const val ITEM_MARGIN_DP = 8
        private const val ITEM_PADDING_DP = 4
        private const val BUTTON_TEXT_SIZE_SP = 12f
        private const val PREVIEW_TEXT_SIZE_SP = 14f
    }

    private var popupWindow: PopupWindow? = null

    fun show() {
        val view = LayoutInflater.from(context).inflate(R.layout.style_selector, null)

        popupWindow =
            PopupWindow(
                view,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                true,
            )

        val gridLayout = view.findViewById<GridLayout>(R.id.style_grid)
        setupStyleGrid(gridLayout)

        view.findViewById<Button>(R.id.close_button).setOnClickListener { dismiss() }

        popupWindow?.showAtLocation(view, android.view.Gravity.BOTTOM, 0, 0)
    }

    fun dismiss() {
        popupWindow?.dismiss()
        popupWindow = null
    }

    private fun setupStyleGrid(gridLayout: GridLayout) {
        gridLayout.removeAllViews()
        for (style in FontStyle.getAllStyles()) {
            gridLayout.addView(createStyleButton(style))
        }
    }

    private fun createStyleButton(style: FontStyle): View {
        val container =
            android.widget.LinearLayout(context).apply {
                layoutParams =
                    GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins(ITEM_MARGIN_DP, ITEM_MARGIN_DP, ITEM_MARGIN_DP, ITEM_MARGIN_DP)
                    }
                orientation = android.widget.LinearLayout.VERTICAL
                gravity = android.view.Gravity.CENTER
            }

        val button =
            Button(context).apply {
                layoutParams =
                    android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                text = style.displayName
                textSize = BUTTON_TEXT_SIZE_SP
                val bgColor = if (style == currentStyle) R.color.key_pressed else R.color.key_normal
                setBackgroundColor(ContextCompat.getColor(context, bgColor))
                setTextColor(ContextCompat.getColor(context, R.color.key_text))
                setOnClickListener {
                    onStyleSelected(style)
                    dismiss()
                }
            }

        val preview =
            TextView(context).apply {
                layoutParams =
                    android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                text = FontStyleTransformer.transformText("Hello", style)
                textSize = PREVIEW_TEXT_SIZE_SP
                gravity = android.view.Gravity.CENTER
                setPadding(ITEM_PADDING_DP, ITEM_PADDING_DP, ITEM_PADDING_DP, ITEM_PADDING_DP)
            }

        container.addView(button)
        container.addView(preview)
        return container
    }
}
