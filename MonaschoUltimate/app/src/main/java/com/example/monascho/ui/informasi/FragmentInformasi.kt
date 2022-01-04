package com.example.monascho.ui.informasi

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.monascho.R
import com.example.monascho.adapter.InformasiAdapter
import com.example.monascho.adapter.InformasisAdapter
import com.example.monascho.databinding.FragmentInformasiBinding
import com.example.monascho.model.PayloadInformasi
import com.example.monascho.utils.ViewPagerScroller
import java.lang.reflect.Field

class FragmentInformasi : Fragment(R.layout.fragment_informasi), InformasiView {
    private var bindings: FragmentInformasiBinding? = null

    lateinit var informasiPresenter: InformasiPresenter
    private lateinit var informasiAdapter: InformasiAdapter
    private lateinit var informasisAdapter: InformasisAdapter

    private var mDots : Array<TextView?>? = null
    private var mCurrentPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInformasiBinding.bind(view)
        bindings = binding

        binding!!.recycler.isFocusable = false
        informasiPresenter = InformasiPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            bindings!!.swlayout.visibility = View.VISIBLE
            informasiPresenter.getResponse()
        }

        addDot(0)

        binding.vslide.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                try {
                    addDot(position)
                    mCurrentPage = position
                    when (position) {
                        0 -> {
                            //txtbtn!!.text = "Selanjutnya"
                        }
                        mDots!!.size - 1 -> {
                            //txtbtn!!.text = "Mulai berbelanja"
                        }
                        else -> {
                            //txtbtn!!.text = "Selanjutnya"
                        }
                    }
                } catch (e: Exception) {
                } catch (e: Throwable) {
                }
            }
        })

        changePagerScroller()
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                dot()
                mainHandler.postDelayed(this, 5000)
            }
        })
    }

    private fun dot(){
        if (mCurrentPage == 3 - 1) {
            mCurrentPage = 0
//            slideView?.setCurrentItem(0, true)
            bindings?.vslide?.setCurrentItem(mCurrentPage, true)
        }else{
            bindings?.vslide?.setCurrentItem(mCurrentPage + 1, true)
        }

    }
    private fun changePagerScroller() {
        try {
            var mScroller: Field? = null
            mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = ViewPagerScroller(bindings?.vslide?.context)
            mScroller[bindings?.vslide] = scroller
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private fun addDot(position: Int) {
        mDots = arrayOfNulls(3)
        bindings?.ldot!!.removeAllViews()
        for (i in mDots!!.indices) {
            mDots!![i] = TextView(requireActivity())
            mDots!![i]!!.text = Html.fromHtml("&#8226;")
            mDots!![i]!!.textSize = 35f
            mDots!![i]!!.setTextColor(Color.parseColor("#2C753F"))
            bindings?.ldot!!.addView(mDots!![i])
        }
        if (mDots!!.isNotEmpty()) {
            mDots!![position]!!.setTextColor(Color.parseColor("#39B54A"))
        }
    }

    override fun onStart() {
        super.onStart()
        informasiPresenter.getResponse()
    }

    override fun onSuccess(payloadInformasi: ArrayList<PayloadInformasi>?) {
        informasiAdapter = InformasiAdapter(requireContext(), payloadInformasi)
        bindings?.recycler?.adapter = informasiAdapter
        bindings?.recycler?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )

        informasisAdapter = InformasisAdapter(requireContext(), payloadInformasi)
        bindings!!.vslide?.adapter = informasisAdapter
    }

    override fun onErrorResponse() {
        Toast.makeText(
                requireContext(),
                "Data Kosong",
                Toast.LENGTH_SHORT
        ).show()
    }
}