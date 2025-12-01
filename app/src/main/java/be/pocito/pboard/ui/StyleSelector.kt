package be.pocito.pboard.ui

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import be.pocito.pboard.R
import be.pocito.pboard.style.FontStyle
import be.pocito.pboard.style.FontStyleTransformer

/**
 * Style Selector Dialog
 * 
 * Displays all available Unicode font styles in a grid layout.
 * Allows users to select a style and provides a callback when selection changes.
 */
class StyleSelector(
    private val context: Context,
    private val currentStyle: FontStyle,
    private val onStyleSelected: (FontStyle) -> Unit
) {
    
    private var dialog: Dialog? = null
    
    /**
     * Show the style selector dialog.
     */
    fun show() {
        // Create dialog
        dialog = Dialog(context, android.R.style.Theme_Material_Light_Dialog)
        
        // Inflate layout
        val view = LayoutInflater.from(context).inflate(R.layout.style_selector, null)
        dialog?.setContentView(view)
        
        // Set up grid layout with style buttons
        val gridLayout = view.findViewById<GridLayout>(R.id.style_grid)
        setupStyleGrid(gridLayout)
        
        // Set up close button
        val closeButton = view.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            dismiss()
        }
        
        // Show dialog
        dialog?.show()
    }
    
    /**
     * Dismiss the dialog.
     */
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
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
        val container = LinearLayout(context).apply {
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
                setBackgroundColor(context.resources.getColor(R.color.key_pressed, null))
                setTextColor(context.resources.getColor(R.color.key_text, null))
            } else {
                setBackgroundColor(context.resources.getColor(R.color.key_normal, null))
                setTextColor(context.resources.getColor(R.color.key_text, null))
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

/**
 * LinearLayout import helper
 */
private class LinearLayout(context: Context) : android.widget.LinearLayout(context)
