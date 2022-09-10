package com.andreotti.github.ui.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.andreotti.github.R
import com.andreotti.github.commons.ErrorAction
import com.andreotti.github.commons.LoadAction
import com.andreotti.github.commons.SuccessAction
import com.andreotti.github.commons.getThrowable
import com.andreotti.github.commons.isError
import com.andreotti.github.commons.isLoading
import com.andreotti.github.commons.isNotEmpty
import com.andreotti.github.databinding.FragmentRepositoriesListBinding
import com.andreotti.github.domain.vo.RepoVO
import com.andreotti.github.ui.adapter.RepositoriesAdapter
import com.andreotti.github.ui.viewmodel.RepositoriesViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    private val binding by lazy { FragmentRepositoriesListBinding.bind(requireView()) }
    private val viewModel: RepositoriesViewModel by viewModel()
    private val reposAdapter by lazy { RepositoriesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindToolbar()
        binding.bindAdapter(reposAdapter)

        binding.reposRefreshLayout.setOnRefreshListener {
            reposAdapter.refresh()
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.repositories.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorAction -> showError(it.data)
                is LoadAction -> showLoading()
                is SuccessAction -> showSuccess(it.data)
            }
        }

        viewModel.fetchRepositories()
    }

    private fun getLoadStateListener(): (CombinedLoadStates) -> Unit = {
        when {
            it.isLoading() -> showLoading()
            it.isError() -> showError(it.getThrowable())
            else -> hideLoading()
        }
    }

    private fun showSuccess(data: PagingData<RepoVO>) {
        hideLoading()
        reposAdapter.addLoadStateListener(getLoadStateListener())
        reposAdapter.submitData(viewLifecycleOwner.lifecycle, data)
    }

    private fun hideLoading() {
        binding.reposRefreshLayout.isRefreshing = false
    }

    private fun showLoading() {
        binding.reposRefreshLayout.isRefreshing = true
        binding.reposRefreshLayout.isVisible = true
        binding.reposAlert.isVisible = false
    }

    private fun showError(data: Throwable?) {
        hideLoading()

        if (reposAdapter.isNotEmpty()) {
            Snackbar.make(
                binding.reposRecyclerView,
                getString(R.string.default_error_message),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        binding.reposAlert.setupError(data) {
            viewModel.fetchRepositories()
        }
        binding.reposRefreshLayout.isVisible = false
    }

    private fun FragmentRepositoriesListBinding.bindToolbar() {
        reposToolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun FragmentRepositoriesListBinding.bindAdapter(
        reposAdapter: RepositoriesAdapter
    ) {
        reposRecyclerView.adapter = reposAdapter
        reposRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
    }
}