import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clothier.databinding.CartItemBinding

class CartAdapter(
    private val cartItems: List<String>,
    private val cartItemPrice: List<String>,
    private val cartImage: List<Int>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val itemQuantities = IntArray(cartItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Move these functions outside the bind function
        fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        fun deleteItem(position: Int) {
            cartItems.toMutableList().removeAt(position)
            cartImage.toMutableList().removeAt(position)
            cartItemPrice.toMutableList().removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartclothname.text = cartItems[position]
                cartitemprice.text = cartItemPrice[position]
                CartImage.setImageResource(cartImage[position])
                cartItemQuantity.text = quantity.toString()

                minusbutton.setOnClickListener {
                    decreaseQuantity(position)
                }

                plusbutton.setOnClickListener {
                    increaseQuantity(position)
                }

                deletebutton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }
    }
}
