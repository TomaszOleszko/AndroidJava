package com.example.painter

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var myImage: ImageView
    private lateinit var myImage1: ImageView
    private lateinit var myImage2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myImage = view.findViewById(R.id.imageView)!!
        myImage.setOnClickListener {
            setWidthAndHeightTo0(myImage1, myImage2)
            val clp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            it.layoutParams = clp
        }
        myImage1 = view.findViewById(R.id.imageView2)!!
        myImage1.setOnClickListener {
            setWidthAndHeightTo0(myImage, myImage2)
            val clp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            it.layoutParams = clp
        }
        myImage2 = view.findViewById(R.id.imageView3)!!
        myImage2.setOnClickListener {
            setWidthAndHeightTo0(myImage, myImage1)
            val clp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            it.layoutParams = clp
        }
        val images = listOf(myImage, myImage1, myImage2)
        setImageBitmap(images)
    }

    private fun setWidthAndHeightTo0(myImage1: ImageView, myImage2: ImageView) {
        myImage1.layoutParams.height = 0
        myImage1.layoutParams.width = 0
        myImage2.layoutParams.height = 0
        myImage2.layoutParams.width = 0
    }


    private fun setImageBitmap(images: List<ImageView>) {
        val bundle = arguments
        if (bundle != null) {
            val bitmap: ArrayList<Bitmap>? = bundle.getParcelableArrayList(MainActivity.BITMAP_KEY)
            bitmap?.forEach {
                images[counter].setImageBitmap(it)
                when (counter) {
                    2 -> counter = 0
                    else -> counter++
                }
            }
        }
    }

    companion object {
        var counter = 0
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}