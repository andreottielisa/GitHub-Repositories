package com.andreotti.github.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat.setAccessibilityHeading
import androidx.core.view.isVisible
import com.andreotti.github.R
import com.andreotti.github.databinding.ComponentAlertViewBinding

internal class AlertView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ComponentAlertViewBinding.inflate(
        LayoutInflater.from(context), this
    )

    init {
        setAccessibilityHeading(binding.alertTitle, true)
    }

    fun setupError(data: Throwable? = null, action: () -> Unit) = with(binding) {
        alertButton.setOnClickListener { action.invoke() }

        data?.let { setupError(it) } ?: setupDefaultError()

        isVisible = true
    }

    private fun setupError(data: Throwable) = with(binding) {
        alertButton.setText(R.string.error_button_try_again)
        alertDescription.text = data.message ?: resources.getString(R.string.default_error_message)
    }

    private fun setupDefaultError() = with(binding) {
        alertButton.setText(R.string.default_alert_button)
        alertDescription.setText(R.string.default_error_message)
    }
}