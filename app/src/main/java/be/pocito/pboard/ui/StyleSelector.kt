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
 * Style Selector PopupWindow
 * 
 * Displays all available Unicode font styles in a grid layout.
 * Allows users to select a style and provides a callback when selection changes.
 * Uses PopupWindow instead of Dialog to work properly in IME context.
 */
class StyleSelector(
    private val context: Context,
    private val currentStyle: FontStyle,
    private val onStyleSelected: (FontStyle) -> Unit
) {
    
    private var popupWindow: PopupWindow? = null
    
    /**
     * Show the style selector popup.
     */
    fun show() {
        // Inflate layout
        val view = LayoutInflater.from(context).inflate(R.layout.style_selector, null)
        
        // Create popup window
        popupWindow = PopupWindow(
            view,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            true  // focusable
        )
        
        // Set up grid layout with style buttons
        val gridLayout = view.findViewById<GridLayout>(R.id.style_grid)
        setupStyleGrid(gridLayout)
        
        // Set up close button
        val closeButton = view.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            dismiss()
        }
        
        // Show popup at the bottom of the screen
        popupWindow?.showAtLocation(
            view,
            android.view.Gravity.BOTTOM,
            0,
            0
        )
    }
    
    /**
     * Dismiss the popup.
     */
    fun dismiss() {
        popupWindow?.dismiss()
        popupWindow = null
    }
    
    /**
     * Set up the grid of style buttons.
     */
    private fun setupStyleGrid(gridLayout: GridLayout) {
        val allStyles = FontStyleTransformer.getAllStyles()
        
        // Clear any existing children
        gridLayout.removeAllViews()
        
        // Add a button for each style
        for (style in allStyles) {
            val button = createStyleButton(style)
            gridLayout.addView(button)
        }
    }
    
    /**
     * Create a button for a single style.
     */
    private fun createStyleButton(style: FontStyle): View {
        // Create container for button and preview
        val container = android.widget.LinearLayout(context).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8)
            }
            orientation = android.widget.LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER
        }
        
        // Create button
        val button = Button(context).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = style.displayName
            textSize = 12f
            
            // Highlight current style
            if (style == currentStyle) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.key_pressed))
                setTextColor(ContextCompat.getColor(context, R.color.key_text))
            } else {
                setBackgroundColor(ContextCompat.getColor(context, R.color.key_normal))
                setTextColor(ContextCompat.getColor(context, R.color.key_text))
            }
            
            // Set click listener
            setOnClickListener {
                onStyleSelected(style)
                dismiss()
            }
        }
        
        // Create preview text
        val preview = TextView(context).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = getStylePreview(style)
            textSize = 14f
            gravity = android.view.Gravity.CENTER
            setPadding(4, 4, 4, 4)
        }
        
        // Add button and preview to container
        container.addView(button)
        container.addView(preview)
        
        return container
    }
    
    /**
     * Get a preview text for a style.
     */
    private fun getStylePreview(style: FontStyle): String {
        val previewText = "Hello"
        return FontStyleTransformer.transformText(previewText, style)
    }
}
