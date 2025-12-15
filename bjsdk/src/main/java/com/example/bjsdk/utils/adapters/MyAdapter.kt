package com.example.bjsdk.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bjsdk.R
import com.example.bjsdk.model.Result
import com.example.bjsdk.model.User
import com.example.bjsdk.utils.RecyclerType

class MyAdapter(
    private val adapterType: RecyclerType,
    private val loadMoreEndItems: (position : Int, onComplete: () -> Unit) -> Unit,
    private val loadMoreStartItems: (position : Int, onComplete: () -> Unit) -> Unit,
    private val onItemClicked: (item: Result) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItems = mutableListOf<Result>()
    private val usersList = mutableListOf<User>()
    private var isLoading = false

    fun submitListWithDiff(newItems: List<Result>) {
        listItems.clear()
        listItems.addAll(newItems)
        println("UpdatedListSize = $itemCount")
    }

    fun submitUserList(users : List<User>){
        usersList.clear()
        usersList.addAll(users)
        println("Users = $users")
    }

    override fun getItemCount() = listItems.size

    fun getUsersItemCount() = usersList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (adapterType) {
            RecyclerType.BANNER -> {
                val view = inflater.inflate(R.layout.item_img_top_rated, parent, false)
                BannerViewHolder(view)
            }
            RecyclerType.TRENDING -> {
                val view = inflater.inflate(R.layout.item_img_trend, parent, false)
                TrendingViewHolder(view)
            }
            RecyclerType.UPCOMING -> {
                val view = inflater.inflate(R.layout.item_img_upcoming, parent, false)
                UpcomingViewHolder(view)
            }
            RecyclerType.ACCOUNTS -> {
                val view = inflater.inflate(R.layout.acc_item, parent, false)
                AccountViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listItems[position]
        /*val userItem = usersList[position]*/
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.backdropPath}"
        if(position == itemCount-3){
            isLoading = true
            loadMoreEndItems(position){
                isLoading = false
            }
        }
        if(position == 2 && itemCount == 80){
            isLoading = true
            loadMoreStartItems(position){
                isLoading = false
            }
        }
        println("ItemCount = $itemCount")

        when (holder) {
            is BannerViewHolder -> holder.bind(
                imageUrl = imageUrl,
                onItemClicked = {
                    onItemClicked(item)
                }
            )
            is TrendingViewHolder -> holder.bind(
                title = item.title,
                imageUrl = imageUrl,
                onItemClicked = {
                    onItemClicked(item)
                }
            )
            is UpcomingViewHolder -> holder.bind(
                title = item.title,
                imageUrl = imageUrl,
                onItemClicked = {
                    onItemClicked(item)
                }
            )
            is AccountViewHolder -> holder.bind(
                title = "userItem.email",
                onItemClicked = {

                }
            )
        }
    }

    //ViewHolder for BANNER
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bannerImageView: ImageView = itemView.findViewById(R.id.top_rated_img)
        fun bind(imageUrl: String, onItemClicked: () -> Unit) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(bannerImageView)

            bannerImageView.setOnClickListener {
                onItemClicked()
            }
        }
    }

    //ViewHolder for TRENDING
    class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trendImageView: ImageView = itemView.findViewById(R.id.trend_img)
        private val trendTxt: TextView = itemView.findViewById(R.id.trend_txt)

        fun bind(imageUrl: String, title: String, onItemClicked: () -> Unit) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(trendImageView)

            trendTxt.isSelected = true
            trendTxt.text = title ?: ""

            trendImageView.setOnClickListener {
                onItemClicked()
            }
        }
    }

    //ViewHolder for UPCOMING
    class UpcomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val upcomingImageView: ImageView = itemView.findViewById(R.id.upcoming_img)
        private val upcomingTxt: TextView = itemView.findViewById(R.id.upcoming_txt)
        fun bind(imageUrl: String, title: String, onItemClicked: () -> Unit) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(upcomingImageView)

            upcomingTxt.isSelected = true
            upcomingTxt.text = title ?: ""

            upcomingImageView.setOnClickListener {
                onItemClicked()
            }
        }
    }

    //Account ViewHolder
    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTxt: TextView = itemView.findViewById(R.id.name)
        private val accCard: CardView = itemView.findViewById(R.id.acc_card)
        fun bind(title: String, onItemClicked: () -> Unit) {
            nameTxt.isSelected = true
            nameTxt.text = title

            accCard.setOnClickListener {
                onItemClicked()
            }
        }
    }
}