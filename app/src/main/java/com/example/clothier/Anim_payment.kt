package com.example.clothier

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView

class Anim_payment : Fragment() {

    interface AnimationCompleteListener {
        fun onAnimationComplete()
    }

    private var animationCompleteListener: AnimationCompleteListener? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_anim_payment, container, false)
        val lottieAnimationView: LottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        lottieAnimationView.playAnimation()

        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                // Notify the listener that the animation is complete
                animationCompleteListener?.onAnimationComplete()

                // Use a handler to remove the fragment after a delay
                Handler().postDelayed({
                    fragmentManager?.beginTransaction()?.remove(this@Anim_payment)?.commit()
                    DeleteFragment()

                }, 2500)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        return view
    }
    fun DeleteFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.remove(this)
        fragmentManager.popBackStack()
        fragmentTransaction.commit()
    }


}