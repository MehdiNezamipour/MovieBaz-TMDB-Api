package com.nezamipour.mehdi.moviebaz.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nezamipour.mehdi.moviebaz.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GenresFragment : DialogFragment() {

    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val allGenres = viewModel.allGenres
        var selected = HashMap<String, String>()
        if (viewModel.selectedGenres.value != null) {
            selected = viewModel.selectedGenres.value!!
        }

        val scrollView =
            View.inflate(
                requireContext(),
                R.layout.fragment_genres,
                null
            )

        val view = scrollView.findViewById<LinearLayout>(R.id.linear)

        allGenres.value?.forEach {
            val checkBox = CheckBox(context)
            checkBox.text = it.name
            if (selected.containsKey(it.name))
                checkBox.isChecked = true
            view.addView(checkBox)
            selected.put(it.name.toString(), it.id.toString())
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.filterByGenre)
            .setView(scrollView)
            .setPositiveButton(
                android.R.string.ok
            ) { dialogInterface, i ->
                applyGenres(view, selected)
            }
            .setNegativeButton(android.R.string.cancel) { dialogInterface, i ->
                applyGenres(view, selected)
            }
            .setCancelable(false)
            .create()
    }

    private fun applyGenres(view: LinearLayout, selected: HashMap<String, String>) {
        view.children.forEach {
            val checkBox = it as CheckBox
            if (!checkBox.isChecked)
                selected.remove(it.text as String)
        }
        viewModel.setGenresSelected(selected)
    }

}