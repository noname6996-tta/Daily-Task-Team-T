package com.tta.core_base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tta.core_base.databinding.ItemLoadMoreBinding
import com.tta.core_utils.extension.visible

abstract class BaseAdapter<T>(
    private val list: ArrayList<T>,
    private val isPaging: Boolean = false,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var typeBody = 1
    private var typeMore = 2

    private var isLoading = false
    private var isFull = false
    private var currentPage = 0
    var page = 1

    var itemClickListener: ((T, Int) -> Unit)? = null

    abstract fun getLayoutId(): Int

    abstract fun bindData(binding: ViewDataBinding, data: T, pos: Int)

    class BaseHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    class LoadMoreHolder(val binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (list.getOrNull(position) != null) {
            typeBody
        } else {
            typeMore
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == typeBody) {
            BaseHolder(DataBindingUtil.inflate(layoutInflater, getLayoutId(), parent, false))
        } else {
            LoadMoreHolder(ItemLoadMoreBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun getItemCount(): Int = if (isPaging) list.size + 1 else list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseHolder -> {
                bindData(holder.binding, list[position], position)
            }

            is LoadMoreHolder -> {
                // Only show loading when process loading and data isn't full
                holder.binding.loading.visible(!isFull && isLoading)
            }
        }
    }

    private fun loadDataSuccess() {
        isLoading = false
        isFull = false
        page += 1
    }

    private fun loadFull() {
        isLoading = false
        isFull = true
    }

    fun resetLoadMore() {
        page = 1
        isFull = false
        isLoading = false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: ArrayList<T>, isReset: Boolean = true, perPage: Int = 20) {
        if (isReset) list.clear()

        // Add new data to rcv
        val oldSize = list.size
        list.addAll(newData)

        // Check whether load last page
        if (newData.size < perPage) {
            loadFull()
        } else {
            loadDataSuccess()
        }

        // Update UI rcv
        if (isReset) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(oldSize, newData.size)
        }
    }

    fun setLoadMore(rcv: RecyclerView, callback: (page: Int) -> Unit) {
        val linearLayout = rcv.layoutManager as LinearLayoutManager
        var lastVisible: Int // last item user can see in screen
        var total: Int // total all items of list
        val threshold = 5 // offset to sure user scroll last visible item

        rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                total = linearLayout.itemCount
                lastVisible = linearLayout.findLastVisibleItemPosition()

                // If user has scrolled last page -> do nothing
                if (isFull) return

                // List is empty -> do nothing
                if (total == 0 || lastVisible == 0) return

                // User has scrolled last page -> check logic load more
                if (!isLoading && lastVisible + threshold >= total) {
                    if (currentPage != page) {
                        callback.invoke(page)
                        currentPage = page
                        isLoading = true
                    }
                }
            }
        })
    }


}