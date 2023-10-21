package com.example.marvelapp.ui.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentSearchBinding
import com.example.marvelapp.util.EndlessRVScrollListener
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding

    private val viewModel: CharacterViewModel by viewModels()
    private var hasNextData = true
    val compositeDisposable = CompositeDisposable()
    var currentText = ""
    lateinit var endlessRVScrollListener: EndlessRVScrollListener

    private val rvAdapter by lazy { SearchRvAdapter{ Log.e("click search", "$it") } }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        setObserver()
        endlessRVScrollListener =
            object : EndlessRVScrollListener(binding.rvList.layoutManager!!, 6) {
                override fun onLoadMore(page: Int) {
                    if (hasNextData) {
                        getData(currentText, page)
                    }
                }
            }
        setRv()
        setViewAction()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setViewAction() {
        // 키보드 검색 시 자동 검색
        val observableTextQuery = binding.etSearch.textChanges()
        observableTextQuery.debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { charSequence ->
                currentText = charSequence.toString()
                activity?.runOnUiThread {
                    rvAdapter.setClear()
                    endlessRVScrollListener.resetState()
                    if (currentText.length == 0) {
                        binding.tvEmptyMsg.visibility = View.GONE
                    } else {
                        binding.tvEmptyMsg.visibility = View.VISIBLE
                    }
                }
                if (currentText.isNotEmpty()) { getData(charSequence.toString(), 0) }
            }.addTo(compositeDisposable)

    }

    private fun setRv() {
        binding.rvList.run {
            adapter = rvAdapter
            layoutManager?.let {
                addOnScrollListener(endlessRVScrollListener)
            }
        }
    }

    private fun setObserver() {
        viewModel.characterData.observe(viewLifecycleOwner){
            Log.e("character observe", "${it.hasNext}")
            rvAdapter.setItems(it.data)
            hasNextData = it.hasNext
        }
    }

    private fun getData(keyword: String, page: Int) {
        viewModel.getCharacters(keyword, page)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}