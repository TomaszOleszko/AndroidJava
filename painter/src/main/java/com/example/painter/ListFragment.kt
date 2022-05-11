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
    private lateinit var myImage3: ImageView

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
            setWidthAndHeightTo0(myImage1, myImage2, myImage3)
            setLinearParams(it)
        }
        myImage1 = view.findViewById(R.id.imageView2)!!
        myImage1.setOnClickListener {
            setWidthAndHeightTo0(myImage, myImage2, myImage3)
            setLinearParams(it)
        }
        myImage2 = view.findViewById(R.id.imageView3)!!
        myImage2.setOnClickListener {
            setWidthAndHeightTo0(myImage, myImage1, myImage3)
            setLinearParams(it)
        }
        myImage3 = view.findViewById(R.id.imageView4)!!
        myImage3.setOnClickListener {
            setWidthAndHeightTo0(myImage, myImage1, myImage2)
            setLinearParams(it)
        }
        val images = listOf(myImage, myImage1, myImage2, myImage3)
        setImageBitmap(images)
    }

    private fun setLinearParams(it: View) {
        val clp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        it.layoutParams = clp
    }

    private fun setWidthAndHeightTo0(myImage1: ImageView, myImage2: ImageView, myImage3: ImageView) {
        myImage1.layoutParams.height = 0
        myImage1.layoutParams.width = 0
        myImage2.layoutParams.height = 0
        myImage2.layoutParams.width = 0
        myImage3.layoutParams.height = 0
        myImage3.layoutParams.width = 0
    }


    private fun setImageBitmap(images: List<ImageView>) {
        val bundle = arguments
        if (bundle != null) {
            val bitmap: ArrayList<Bitmap>? = bundle.getParcelableArrayList(MainActivity.BITMAP_KEY)
            bitmap?.forEach {
                images[counter].setImageBitmap(it)
                when (counter) {
                    3 -> counter = 0
                    else -> counter++
                }
            }
        }
    }

    companion object {
        var counter = 0
    }
}