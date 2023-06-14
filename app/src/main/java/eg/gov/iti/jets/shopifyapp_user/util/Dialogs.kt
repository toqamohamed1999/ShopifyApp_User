package eg.gov.iti.jets.shopifyapp_user.util

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R

object Dialogs {
    fun SnakeToast(view: View, message: String) {
        val snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_SHORT
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.background= ResourcesCompat.getDrawable(view.context.resources, R.drawable.snackbarback,null)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLACK)
        textView.textSize = 18f
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        snackbar.duration = Snackbar.LENGTH_SHORT
        snackbar.show()

    }

    fun alertDialogBuilder(context: Context, title: String, message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)

        builder.setMessage(message)

        builder.setIcon(android.R.drawable.ic_dialog_alert)

        return builder
    }


    fun showConfirmationDialog(
        context: Context?,
        message: String?,
        onYes : Runnable,
        onNo : Runnable
    ) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(
            context!!
        )
        builder.setMessage(message)
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int -> onYes.run() }
            .setNegativeButton("No") { _: DialogInterface?, _: Int -> onNo.run() }
        val dialog = builder.create()
        dialog.show()
    }
}