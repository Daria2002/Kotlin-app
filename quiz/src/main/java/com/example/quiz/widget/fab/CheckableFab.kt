package com.example.quiz.widget.fab

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CheckableFab(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs), Checkable {
    private var _checked = true
    private val attrs = intArrayOf(android.R.attr.state_checked)
    init {

    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(1 + extraSpace)
        if(_checked) {

        }
        return drawableState
    }

    override fun setChecked(checked: Boolean) {
        if(_checked == checked) return
        _checked = checked
        refreshDrawableState()
    }

    override fun isChecked() = _checked
    override fun toggle() {
        _checked = !_checked
    }
}