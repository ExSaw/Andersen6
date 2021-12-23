package com.rickrip.andersen6

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

class AFragment : Fragment(R.layout.fragment_a),
    CustomDialogFragment.PositiveButtonOnClickListener {

    private var buttonClickListener: ButtonClickListener? = null
    private val contacts = mutableListOf<Contact>()
    private var index = 0
    lateinit var rvView: RecyclerView

    // указываем способ создания, что требуется
    companion object {
        fun newInstance() = AFragment()
        const val FRAGMENT_A_TAG = "FRAGMENT_A_TAG"
    }

    interface ButtonClickListener {
        fun onButtonClicked(index: Int, text: String)
    }

    override fun onPositiveButtonClicked() {
        Toast.makeText(requireContext(), "Item removed!", Toast.LENGTH_SHORT).show()
        contacts.removeAt(index)
        rvView.adapter?.notifyItemRemoved(index)
        rvView.adapter?.notifyItemRangeChanged(index, contacts.size)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("'", "A onAttach")

        if (context is ButtonClickListener) {
            buttonClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("'", "A onCreateView")
        return inflater.inflate(R.layout.fragment_a, container, false)
    }


    //для взаим-я с ui
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("'", "A onViewCreated")

        repeat(100) {
            contacts.add(Contact("Person #$it", it))
        }
        rvView = view.findViewById<RecyclerView>(R.id.rv_contacts_list)

        rvView.adapter = ContactAdapter(requireContext(), contacts) { info, index, touchType ->
            if (index != RecyclerView.NO_POSITION) { //check if exists

                if (touchType == 0) {
                    buttonClickListener?.onButtonClicked(index, info)
                } else {
                    CustomDialogFragment.newInstance().show(childFragmentManager, "what?")
                    this.index = index
                    //contacts.removeAt(index)
                }
            }
        }

        rvView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvView.itemAnimator = SlideInDownAnimator() // add animation doesn't work(
        rvView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        ) //decoration

        setFragmentResultListener("key_previous") { key, result ->

            val resultId = result.getInt("viewId")
            val resultText = result.getString("viewText")

            contacts[resultId].info = resultText.toString()

            Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDetach() {
        super.onDetach()
        buttonClickListener = null
    }

}