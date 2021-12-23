package com.rickrip.andersen6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class BFragment : Fragment() {

    companion object {

        const val FRAGMENT_B_TAG = "FRAGMENT_B_TAG"

        private const val VIEW_ID_EXTRA = "VIEW_ID_EXTRA"
        private const val VIEW_TEXT = "VIEW_TEXT"

        fun newInstance() = BFragment()

        fun newInstance(int: Int, text: String) = BFragment().also {
            it.arguments = Bundle().apply {
                putInt(VIEW_ID_EXTRA, int)
                putString(VIEW_TEXT, text)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            view.findViewById<EditText>(R.id.et_main)
                .setText("${requireArguments().getString(VIEW_TEXT)}")
        }

        view.findViewById<Button>(R.id.button).run {

            setOnClickListener {
                try{
                    Log.d("'", "id="+requireArguments().getInt(VIEW_ID_EXTRA))
                    BFragment().arguments = Bundle().apply {
                        putString(VIEW_TEXT, view.findViewById<EditText>(R.id.et_main).text.toString())
                    }
                    setFragmentResult(
                        "key_previous", bundleOf(
                            "viewId" to requireArguments().getInt(VIEW_ID_EXTRA),
                            "viewText" to view.findViewById<EditText>(R.id.et_main).text.toString()
                        )
                    )

                    if (!MainActivity.isTablet) {
                        parentFragmentManager.beginTransaction().run {
                            if (parentFragmentManager.findFragmentByTag(AFragment.FRAGMENT_A_TAG) != null) {
                                show(parentFragmentManager.findFragmentByTag(AFragment.FRAGMENT_A_TAG)!!)
                            }
                        }
                        parentFragmentManager.popBackStack()
                    }
                }catch (e:Exception){
                    Toast.makeText(requireContext(),"No contact selected!",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}