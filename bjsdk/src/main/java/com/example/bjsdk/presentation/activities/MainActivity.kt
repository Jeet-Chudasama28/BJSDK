package com.example.bjsdk.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.bjsdk.R
import com.example.bjsdk.databinding.SdkActivityMainBinding
import com.example.bjsdk.presentation.viewmodel.MainViewModel
import com.example.bjsdk.utils.RecyclerType
import com.example.bjsdk.utils.ScrollingDirection
import com.example.bjsdk.utils.adapters.MyAdapter
import com.example.bjsdk.utils.bottomsheets.BottomSheetDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: SdkActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private var recyclerViewState: Parcelable? = null
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var trendingAdapter: MyAdapter
    private var position = 0
    private var recyclerViewPosition: Int = 0
    private var recyclerViewOffset: Int = 0
    private var scrollingDirection = ScrollingDirection.NONE

    companion object {
        private const val LIST_STATE_KEY = "list_state_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = SdkActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launch {
            mainViewModel.scrollingDirection.collect { value ->
                if (value != null) {
                    scrollingDirection = value
                }
            }
        }

        //1st banner RecyclerView
        binding.topRatedRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.topRatedRecycler)

        layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.trendingRecycler.layoutManager = layoutManager
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(LIST_STATE_KEY)
        }

        binding.upcomingRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val bannerAdapter = MyAdapter(
            adapterType = RecyclerType.BANNER,
            onItemClicked = { item ->
                val bottomSheet = BottomSheetDialog(
                    item = item,
                    genreList = mainViewModel.genreList.value?.genres,
                )
                bottomSheet.show(supportFragmentManager, "DetailBottomSheet")
            },
            loadMoreEndItems = { _, onComplete ->

            },
            loadMoreStartItems = { _, onComplete ->

            }
        )
        trendingAdapter = MyAdapter(
            adapterType = RecyclerType.TRENDING,
            loadMoreEndItems = { _, onComplete ->
                lifecycleScope.launch {
                    mainViewModel.fetchTrendingData(
                        page = mainViewModel.currentEndTrendingPage + 1,
                        onComplete = { onComplete() },
                        onUpdateScrollState = {
                            val layoutManager =
                                binding.trendingRecycler.layoutManager as LinearLayoutManager
                            recyclerViewPosition = layoutManager.findFirstVisibleItemPosition()
                            recyclerViewOffset =
                                layoutManager.findViewByPosition(recyclerViewPosition)?.top ?: 0
                        }
                    )
                }
            },
            loadMoreStartItems = { _, onComplete ->
                lifecycleScope.launch {
                    mainViewModel.fetchTrendingData(
                        page = if (scrollingDirection == ScrollingDirection.LEFT) {
                            mainViewModel.currentStartTrendingPage - 1
                        } else {
                            mainViewModel.currentEndTrendingPage - mainViewModel.pageLimit
                        },
                        onComplete = { onComplete() },
                        onUpdateScrollState = {
                            val layoutManager =
                                binding.trendingRecycler.layoutManager as LinearLayoutManager
                            recyclerViewPosition = layoutManager.findFirstVisibleItemPosition()
                            recyclerViewOffset =
                                layoutManager.findViewByPosition(recyclerViewPosition)?.top ?: 0
                        },
                        isStartDataEnabled = true
                    )
                }
            },
            onItemClicked = { item ->
                val bottomSheet = BottomSheetDialog(
                    item = item,
                    genreList = mainViewModel.genreList.value?.genres,
                )
                bottomSheet.show(supportFragmentManager, "DetailBottomSheet")
            }
        )

        val upcomingAdapter = MyAdapter(
            adapterType = RecyclerType.UPCOMING,
            loadMoreEndItems = { _, onComplete ->
                lifecycleScope.launch {
                    mainViewModel.fetchUpcomingData(mainViewModel.currentUpcomingPage + 1)
                }
            },
            loadMoreStartItems = { _, onComplete ->

            },
            onItemClicked = { item ->
                val bottomSheet = BottomSheetDialog(
                    item = item,
                    genreList = mainViewModel.genreList.value?.genres,
                )
                bottomSheet.show(supportFragmentManager, "DetailBottomSheet")
            }
        )

        binding.topRatedRecycler.adapter = bannerAdapter
        binding.trendingRecycler.adapter = trendingAdapter
        binding.upcomingRecycler.adapter = upcomingAdapter

        mainViewModel.bannerData.observe(this, Observer { items ->
            bannerAdapter.submitListWithDiff(items.results)
        })
        mainViewModel.trendingData.observe(this, Observer { items ->
            position = items.size
            trendingAdapter.submitListWithDiff(items)
            layoutManager.scrollToPositionWithOffset(recyclerViewPosition - 20, recyclerViewOffset)
        })
        mainViewModel.upcomingData.observe(this, Observer { items ->
            upcomingAdapter.submitListWithDiff(items)
        })

        binding.menuBtn.setOnClickListener {
            showPopUp(
                menuBtn = binding.menuBtn,
                onLogoutClick = {
                    getSharedPreferences("UserPreferences", MODE_PRIVATE).edit {
                        clear()
                    }
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onSwitchAccountClick = {
                    mainViewModel.user2List.observe(this, Observer { items ->
                        val bottomSheet =
                            BottomSheetDialog(userList = items, mainViewModel = mainViewModel)
                        bottomSheet.show(supportFragmentManager, "AccountBottomSheet")
                    })
                }
            )
        }
    }

    private fun showPopUp(
        menuBtn: ImageButton,
        onLogoutClick: () -> Unit,
        onSwitchAccountClick: () -> Unit
    ) {
        val popup = PopupMenu(this, menuBtn)
        popup.inflate(R.menu.main_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.nav_logout -> {
                    onLogoutClick()
                }
                R.id.nav_switch_ac -> {
                    onSwitchAccountClick()
                }
            }
            true
        })
        popup.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE_KEY, layoutManager.onSaveInstanceState())
    }
}