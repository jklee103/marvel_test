package com.example.marvelapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentSearchBinding
import com.example.marvelapp.util.EndlessRVScrollListener
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private var hasNextData = true
    val compositeDisposable = CompositeDisposable()
    var currentText = ""
    lateinit var endlessRVScrollListener: EndlessRVScrollListener
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
                        getData()
                    }
                }
            }
        setRv()
        setViewAction()
        return binding.root
    }

    private fun setViewAction() {
        // 키보드 검색 시 자동 검색
        val observableTextQuery = binding.etSearch.textChanges()
        observableTextQuery.debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { charSequence ->
                currentText = charSequence.toString()
                activity?.runOnUiThread {
                    //TODO rv clear
                    endlessRVScrollListener.resetState()
                    if (currentText.length == 0) {
                        binding.tvEmptyMsg.visibility = View.GONE
                    } else {
                        binding.tvEmptyMsg.visibility = View.VISIBLE
                    }
                }
                if (currentText.isNotEmpty()) {
                } //TODO vm getData
            }.addTo(compositeDisposable)

    }

    private fun setRv() {
        binding.rvList.run {
            adapter = SearchRvAdapter()
            layoutManager?.let {
                addOnScrollListener(endlessRVScrollListener)
            }
        }
    }

    private fun setObserver() {

    }

    private fun getData() {
        //TODO vm데이터
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}