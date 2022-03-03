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
import com.ims.imstask.databinding.MainFragmentBinding
import com.ims.imstask.retrofit.BookingSlotsChildItem
import com.ims.imstask.retrofit.BookingSlotsParentItem
import com.ims.imstask.retrofit.BookingSlotsResponseItem
import com.ims.imstask.ui.main.DateUtils.getDateFromDateTime
import com.ims.imstask.ui.main.DateUtils.getTimeType

class MainFragment : Fragment() {

    private val SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var mBinding: MainFragmentBinding

    private var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager? = null

    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null

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
        val eimSavedState =
            savedInstanceState?.getParcelable<Parcelable>(SAVED_STATE_EXPANDABLE_ITEM_MANAGER)
        mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(eimSavedState)

        listenToViewModel()
    }

    private fun listenToViewModel() {
        viewModel.getMasterResponse.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                mBinding.progressBar.visibility = View.GONE
                mBinding.recyclerView.visibility = View.VISIBLE
                setData(it1)
            }
        }
    }

    private fun setData(bookingSlots: List<BookingSlotsResponseItem?>) {

        val bookingSlotsParentItemList = ArrayList<BookingSlotsParentItem>()
        bookingSlotsParentItemList.add(onSlotsFilter("Early Morning", bookingSlots, 2, 7))
        bookingSlotsParentItemList.add(onSlotsFilter("Morning", bookingSlots, 7, 12))
        bookingSlotsParentItemList.add(onSlotsFilter("Afternoon", bookingSlots, 12, 15))
        bookingSlotsParentItemList.add(onSlotsFilter("Evening", bookingSlots, 15, 18))
        bookingSlotsParentItemList.add(onSlotsFilter("All Booking (Today)", bookingSlots, 0, 24))

        val myItemAdapter = ExpandableExampleAdapter(bookingSlotsParentItemList)
        mWrappedAdapter =
            mRecyclerViewExpandableItemManager!!.createWrappedAdapter(myItemAdapter) // wrap for expanding
        val animator: GeneralItemAnimator = RefactoredDefaultItemAnimator()
        animator.supportsChangeAnimations = false
        mBinding.recyclerView.adapter = mWrappedAdapter // requires *wrapped* adapter

        mBinding.recyclerView.itemAnimator = animator

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

    private fun onSlotsFilter(
        header: String,
        bookingSlots: List<BookingSlotsResponseItem?>,
        fStartTime: Int,
        fEndTime: Int
    ): BookingSlotsParentItem {
        var slotsAvailable = 0
        val slotsList = ArrayList<BookingSlotsChildItem>()
        for (item in bookingSlots) {
            item?.let {
                if (getTimeType(it.startTime!!) in fStartTime..fEndTime) {
                    slotsList.add(
                        BookingSlotsChildItem(
                            timeSlots = "${getDateFromDateTime(it.startTime)} - ${
                                getDateFromDateTime(
                                    it.endTime!!
                                )
                            }", isBooked = it.isBooked!!
                        )
                    )
                    if (!it.isBooked) {
                        slotsAvailable += 1
                    }
                }
            }
        }
        return BookingSlotsParentItem(header, slotsAvailable, slotsList)
    }

}