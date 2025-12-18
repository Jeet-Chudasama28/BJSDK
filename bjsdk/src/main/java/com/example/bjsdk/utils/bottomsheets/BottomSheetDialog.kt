package com.example.bjsdk.utils.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bjsdk.model.Genre
import com.example.bjsdk.R
import com.example.bjsdk.model.Result
import com.example.bjsdk.model.User
import com.example.bjsdk.presentation.viewmodel.MainViewModel
import com.example.bjsdk.utils.RecyclerType
import com.example.bjsdk.utils.adapters.MyAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(
    private val item: Result? = null,
    private val genreList: List<Genre>? = emptyList(),
    private val userList: List<User> = emptyList(),
    private val mainViewModel: MainViewModel? = null
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.detail_bottom_sheet,
            container, false
        )
        /*val v2: View = inflater.inflate(
            R.layout.ac_bottom_sheet,
            container, false
        )
        val accRecyclerView = v2.findViewById<RecyclerView>(R.id.acc_recycler)
        accRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val accAdapter = MyAdapter(
            adapterType = RecyclerType.ACCOUNTS,
            loadMoreEndItems = { _, onComplete -> },
            loadMoreStartItems = { _, onComplete -> },
            onItemClicked = { item ->

            }
        )
        mainViewModel?.user2List?.observe(this, Observer { items ->
            accAdapter.submitUserList(items)
        })
        accRecyclerView.adapter = accAdapter*/

        val imagView = v.findViewById<ImageView>(R.id.detail_img)
        val titleTxt = v.findViewById<TextView>(R.id.movie_title_txt)
        val genreTxt = v.findViewById<TextView>(R.id.genres_txt)
        val dateTxt = v.findViewById<TextView>(R.id.release_date_txt)
        val ratingTxt = v.findViewById<TextView>(R.id.rating_txt)
        val descriptionTxt = v.findViewById<TextView>(R.id.description_txt)
        titleTxt.isSelected = true

        val screenHeight = imagView.resources.displayMetrics.heightPixels
        val params = imagView.layoutParams
        params.height = (screenHeight * 0.5).toInt()
        imagView.layoutParams = params

        val imageUrl = "https://image.tmdb.org/t/p/w500${item?.backdropPath}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(imagView)

        val genreFilteredList = genreList
            ?.filter { genre -> item?.genreIds!!.contains(genre.id) }
            ?.map { it.name }

        titleTxt.text = item?.originalTitle
        genreTxt.text = genreFilteredList?.joinToString(", ") ?: "Unknown"
        dateTxt.text = item?.releaseDate
        ratingTxt.text = item?.voteAverage.toString()
        descriptionTxt.text = item?.overview

        return v
    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )?.let { sheet ->
            sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            BottomSheetBehavior.from(sheet).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
            }
        }
    }
}