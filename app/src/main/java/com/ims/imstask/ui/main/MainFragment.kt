package com.ims.imstask.ui.main

import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import com.ims.imstask.R
import com.ims.imstask.data.AbstractExpandableDataProvider
import com.ims.imstask.data.ExampleExpandableDataProvider
import com.ims.imstask.databinding.MainFragmentBinding
import com.ims.imstask.retrofit.BookingSlotsResponseItem

class MainFragment : Fragment(), RecyclerViewExpandableItemManager.OnGroupCollapseListener,
    RecyclerViewExpandableItemManager.OnGroupExpandListener {

    private val SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var mBinding: MainFragmentBinding

    private var cardsRecyclerAdapter: BookingSlotsListAdapter? = null

    private var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager? = null

    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private var mDataProvider: ExampleExpandableDataProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MainFragmentBinding.inflate(layoutInflater)
        mBinding.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        cardsRecyclerAdapter = BookingSlotsListAdapter()
        val eimSavedState =
            savedInstanceState?.getParcelable<Parcelable>(SAVED_STATE_EXPANDABLE_ITEM_MANAGER)
        mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(eimSavedState)
        mRecyclerViewExpandableItemManager?.setOnGroupExpandListener(this)
        mRecyclerViewExpandableItemManager?.setOnGroupCollapseListener(this)

        listenToViewModel()
    }

    private fun listenToViewModel() {
        viewModel.getMasterResponse.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                setData(it1)
            }
        }
    }

    private fun setData(itemList: List<BookingSlotsResponseItem?>) {
        mDataProvider = ExampleExpandableDataProvider()

        //adapter
        val myItemAdapter = ExpandableExampleAdapter(getDataProvider())

        mLayoutManager = LinearLayoutManager(requireActivity())

        mWrappedAdapter = mRecyclerViewExpandableItemManager!!.createWrappedAdapter(myItemAdapter) // wrap for expanding

        val animator: GeneralItemAnimator = RefactoredDefaultItemAnimator()
        animator.supportsChangeAnimations = false

        mBinding.recyclerView.adapter = mWrappedAdapter // requires *wrapped* adapter

        mBinding.recyclerView.itemAnimator = animator
        mBinding.recyclerView.setHasFixedSize(false)

        mBinding.recyclerView.addItemDecoration(
            ItemShadowDecorator(
                (ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.material_shadow_z1
                ) as NinePatchDrawable?)!!
            )
        )
        mBinding.recyclerView.addItemDecoration(
            SimpleListDividerDecorator(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.list_divider_h
                ), true
            )
        )

        mRecyclerViewExpandableItemManager!!.attachRecyclerView(mBinding.recyclerView)
    }

    override fun onGroupCollapse(groupPosition: Int, fromUser: Boolean, payload: Any?) {
        //TODO("Not yet implemented")
    }

    override fun onGroupExpand(groupPosition: Int, fromUser: Boolean, payload: Any?) {
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition)
        }
    }

    private fun adjustScrollPositionOnGroupExpanded(groupPosition: Int) {
        val childItemHeight =
            requireActivity().resources.getDimensionPixelSize(R.dimen.list_item_height)
        val topMargin =
            (requireActivity().resources.displayMetrics.density * 16).toInt() // top-spacing: 16dp
        mRecyclerViewExpandableItemManager!!.scrollToGroup(
            groupPosition,
            childItemHeight,
            topMargin,
            topMargin
        )
    }

    private fun getDataProvider(): AbstractExpandableDataProvider? {
        return mDataProvider
    }

}