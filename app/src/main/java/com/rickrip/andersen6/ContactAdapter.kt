package com.rickrip.andersen6

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactAdapter(
    private val context: Context,
    private val contacts: List<Contact>,
    private val callBack: (String, Int, Int) -> Unit
    ) : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    // Expansive operation! I assumed that onCreateViewHolder called only 7 times it depends on the screen height!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("'","onCreateViewHolder")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("'","onBindViewHolder=$position")
        val contact = contacts[position]
        holder.bind(contact)
        holder.itemView.run {

            setOnClickListener {
                callBack(contact.info,position,0)
                notifyItemChanged(position)
            }
            setOnLongClickListener {
                callBack(contact.info,position,1)
//                notifyItemRemoved(position)
//                notifyItemRangeChanged(position,itemCount)
                true
            }
        }
    }


    override fun getItemCount() = contacts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact) {
            itemView.findViewById<TextView>(R.id.tv_contactInfo).text = contact.info
            //loads image a little slowly but ok
            Glide.with(context).load(contact.imageUrl).into(itemView.findViewById<ImageView>(R.id.iv_contactImage))

        }
    }

}

data class Contact(var info:String, val index:Int){
    // using index to attach different image for every contact
    val imageUrl = "https://picsum.photos/100?random=$index"
}