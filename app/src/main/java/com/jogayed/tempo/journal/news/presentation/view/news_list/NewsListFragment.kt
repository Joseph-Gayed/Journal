package com.jogayed.tempo.journal.news.presentation.view.news_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jogayed.core.presentation.utils.AdapterStatus
import com.jogayed.core.presentation.utils.RecyclerViewPaginator
import com.jogayed.core.presentation.view.BaseFragment
import com.jogayed.core.presentation.view.getQueryFlow
import com.jogayed.core.presentation.view.hideSoftKeyboard
import com.jogayed.tempo.journal.R
import com.jogayed.tempo.journal.news.domain.action.NewsAction
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import com.jogayed.tempo.journal.news.presentation.model.NewsUiModel
import com.jogayed.tempo.journal.news.presentation.viewmodel.NewsViewModel
import com.jogayed.tempo.journal.news.presentation.viewstate.NewsUiViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * A fragment representing a list of Items.
 */
@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewsListFragment : BaseFragment() {
    private lateinit var searchView: SearchView
    private lateinit var swipeNews: SwipeRefreshLayout
    private lateinit var rvNews: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var emptyView: View
    private lateinit var tvErrorMessage: TextView

    private lateinit var paginator: RecyclerViewPaginator


    private val newsListAdapter: NewsListAdapter by lazy {
        NewsListAdapter { item ->
            val directions = NewsListFragmentDirections.navActionNewsListToNewsDetails(item)
            findNavController().navigate(directions)
        }
    }

    private val viewModel: NewsViewModel by activityViewModels()


    override fun getLayoutResId() = R.layout.fragment_news_list

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        super.init()
        initUi()
        val title = if (viewModel.keyword.isNotEmpty())
            viewModel.keyword
        else
            getString(R.string.app_name)
        setScreenTitle(title)
    }


    override fun subscribe() {
        super.subscribe()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsListViewStates.collect { state ->
                handleStates(state)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu_item, menu);
        val menuItem = menu.findItem(R.id.action_search)
        searchView = menuItem.actionView as SearchView
        observeSearchView()
    }


    private fun observeSearchView() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchView.run {
                getQueryFlow()
                    .collect { keyword ->
                        setScreenTitle(keyword)
                        viewModel.executeAction(NewsAction.SearchNews(NewsSearchParams(keyword = keyword)))
                    }
            }
        }
    }


    private fun initUi() {
        loadingView = requireView().findViewById(R.id.view_loading)
        errorView = requireView().findViewById(R.id.view_error)
        emptyView = requireView().findViewById(R.id.view_no_data)
        tvErrorMessage = requireView().findViewById(R.id.tv_error)

        swipeNews = requireView().findViewById(R.id.swipe_news)
        swipeNews.isEnabled = false

        swipeNews.setOnRefreshListener {
            viewModel.executeAction(NewsAction.RefreshResults(NewsSearchParams(keyword = viewModel.keyword)))
        }
        rvNews = requireView().findViewById(R.id.rv_news)
        with(rvNews) {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }

        paginator = RecyclerViewPaginator(rvNews, { !viewModel.isLoadMoreDisabled() }, { page ->
            viewModel.executeAction(
                NewsAction.LoadMoreResults(
                    NewsSearchParams(
                        keyword = viewModel.keyword,
                        page = page
                    )
                )
            )
        })
    }


    private fun handleStates(state: NewsUiViewState) {
        swipeNews.isRefreshing = state.isRefreshing
        swipeNews.isEnabled =
            viewModel.keyword.isNotEmpty() || (state.error != null && viewModel.keyword.isNotEmpty())
        loadingView.isVisible = state.isLoading && !state.isRefreshing && state.data.isNullOrEmpty()
        errorView.isVisible = state.error != null
        emptyView.isVisible = state.isEmpty


        swipeNews.isVisible = !loadingView.isVisible


        if (state.isLoadingMore) {
            rvNews.post {
                newsListAdapter.adapterStatus = AdapterStatus.Loading
            }
        }
        if (state.data.isNotEmpty())
            handleSuccessState(state.data)
        else if (state.error != null) {
            tvErrorMessage.text = state.error.message
            state.error.printStackTrace()
        }
    }


    private fun handleSuccessState(data: List<NewsUiModel>) {
        if (data.isNotEmpty()) {
            requireContext().hideSoftKeyboard()
            newsListAdapter.setData(data)
        }
    }

}