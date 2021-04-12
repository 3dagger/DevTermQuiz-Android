package com.dagger.devtermquiz.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.ext.dp
import com.dagger.devtermquiz.ext.getValueAnimator
import com.dagger.devtermquiz.ext.screenWidth
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.orhanobut.logger.Logger
import java.util.*
data class MainListModel(val id: Int)

class RecyclerAdapter(context: Context, private val result: List<SingleQuizResults>) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val originalWidth = context.screenWidth - 48.dp
    private val expandedWidth = context.screenWidth - 24.dp
    private var originalHeight = -1 // will be calculated dynamically
    private var expandedHeight = -1 // will be calculated dynamically

    private val listItemHorizontalPadding: Float = 16.0F
    private val listItemVerticalPadding: Float = 24.0F

    private lateinit var recyclerView: RecyclerView

    private val listItemExpandDuration: Long get() = (300L / 0.8).toLong()

    private var isScaledDown = false
    private var expandedModel: SingleQuizResults? = null


    override fun getItemCount(): Int {
        Logger.d("result.size :: ${result.size}")
        return result.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
         RecyclerViewHolder(inflater.inflate(R.layout.item_view, parent, false))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val model = result[position]

        expandItem(holder, model == expandedModel, animate = false)
        scaleDownItem(holder, position, isScaledDown)

        holder.firstCardContainer.setOnClickListener {
            if (expandedModel == null) {

                // expand clicked view
                expandItem(holder, expand = true, animate = true)
                expandedModel = model
            } else if (expandedModel == model) {

                // collapse clicked view
                expandItem(holder, expand = false, animate = true)
                expandedModel = null
            } else {

                // collapse previously expanded view
                val expandedModelPosition = result.indexOf(expandedModel!!)
                val oldViewHolder =
                    recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? RecyclerViewHolder
                if (oldViewHolder != null) expandItem(oldViewHolder, expand = false, animate = true)

                // expand clicked view
                expandItem(holder, expand = true, animate = true)
                expandedModel = model
            }
        }

    }

    private fun expandItem(holder: RecyclerViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) {progress ->  setExpandProgress(holder, progress)}

            if (expand) animator.doOnStart { holder.firstExpandView.isVisible = true }
            else animator.doOnEnd { holder.firstExpandView.isVisible = false }

            animator.start()
        } else {
            holder.firstExpandView.isVisible = expand && expandedHeight >= 0
            setExpandProgress(holder, if (expand) 1f else 0f)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (expandedHeight < 0) {
            expandedHeight = 0

            holder.firstCardContainer.doOnLayout { view ->
                originalHeight = view.height

                // show expandView and record expandedHeight in next layout pass
                // (doOnPreDraw) and hide it immediately. We use onPreDraw because
                // it's called after layout is done. doOnNextLayout is called during
                // layout phase which causes issues with hiding expandView.
                holder.firstExpandView.isVisible = true
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.firstExpandView.isVisible = false
                }
            }
        }
    }

    private fun setExpandProgress(holder: RecyclerViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.firstCardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.firstCardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

//        holder.firstCardContaier.setBackgroundColor(blendColors(originalBg, expandedBg, progress))
        holder.firstCardContainer.requestLayout()

        holder.firstChevron.rotation = 90 * progress
    }

    ///////////////////////////////////////////////////////////////////////////
    // Scale Down Animation
    ///////////////////////////////////////////////////////////////////////////

    private inline val LinearLayoutManager.visibleItemsRange: IntRange
        get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()

    fun getScaleDownAnimator(isScaledDown: Boolean): ValueAnimator {
        val lm = recyclerView.layoutManager as LinearLayoutManager

        val animator = getValueAnimator(isScaledDown,
            duration = 300L, interpolator = AccelerateDecelerateInterpolator()
        ) { progress ->

            // Get viewHolder for all visible items and animate attributes
            for (i in lm.visibleItemsRange) {
                val holder = recyclerView.findViewHolderForLayoutPosition(i) as RecyclerViewHolder
                setScaleDownProgress(holder, i, progress)
            }
        }

        // Set adapter variable when animation starts so that newly binded views in
        // onBindViewHolder will respect the new size when they come into the screen
        animator.doOnStart { this.isScaledDown = isScaledDown }

        // For all the non visible items in the layout manager, notify them to adjust the
        // view to the new size
        animator.doOnEnd {
            repeat(lm.itemCount) { if (it !in lm.visibleItemsRange) notifyItemChanged(it) }
        }
        return animator
    }

    private fun setScaleDownProgress(holder: RecyclerViewHolder, position: Int, progress: Float) {
        val itemExpanded = position >= 0
        holder.firstCardContainer.layoutParams.apply {
            width = ((if (itemExpanded) expandedWidth else originalWidth) * (1 - 0.1f * progress)).toInt()
            height = ((if (itemExpanded) expandedHeight else originalHeight) * (1 - 0.1f * progress)).toInt()
//            log("width=$width, height=$height [${"%.2f".format(progress)}]")
        }
        holder.firstCardContainer.requestLayout()

        holder.firstScaleContainer.scaleX = 1 - 0.05f * progress
        holder.firstScaleContainer.scaleY = 1 - 0.05f * progress

        holder.firstScaleContainer.setPadding(
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt()
        )

//        holder.listItemFg.alpha = progress
    }

    /** Convenience method for calling from onBindViewHolder */
    private fun scaleDownItem(holder: RecyclerViewHolder, position: Int, isScaleDown: Boolean) {
        setScaleDownProgress(holder, position, if (isScaleDown) 1f else 0f)
    }



    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstExpandView: LinearLayout = itemView.findViewById(R.id.firstExpaneView)
        val secondExpandView: LinearLayout = itemView.findViewById(R.id.secondExpandView)
        val thirdExpandView: LinearLayout = itemView.findViewById(R.id.thirdExpandView)
        val fourthExpandView: LinearLayout = itemView.findViewById(R.id.fourthExpandView)

        val firstCardContainer: FrameLayout = itemView.findViewById(R.id.firstCardContainer)
        val secondCardContainer: FrameLayout = itemView.findViewById(R.id.secondCardContainer)
        val thirdCardContainer: FrameLayout = itemView.findViewById(R.id.thirdCardContainer)
        val fourthCardContainer: FrameLayout = itemView.findViewById(R.id.fourthCardContainer)


        val firstScaleContainer: RelativeLayout = itemView.findViewById(R.id.firstScaleContainer)
        val secondScaleContainer: RelativeLayout = itemView.findViewById(R.id.secondScaleContainer)
        val thirdScaleContainer: RelativeLayout = itemView.findViewById(R.id.thirdScaleContainer)
        val fourthScaleContainer: RelativeLayout = itemView.findViewById(R.id.fourthScaleContainer)


        val firstChevron: ImageView = itemView.findViewById(R.id.firstChevron)
        val secondChevron: ImageView = itemView.findViewById(R.id.secondChevron)
        val thirdChevron: ImageView = itemView.findViewById(R.id.thirdChevron)
        val fourthChevron: ImageView = itemView.findViewById(R.id.fourthChevron)

        val firstExample: TextView = itemView.findViewById(R.id.firstExample)
        val secondExample: TextView = itemView.findViewById(R.id.secondExample)
        val thirdExample: TextView = itemView.findViewById(R.id.thirdExample)
        val fourthExample: TextView = itemView.findViewById(R.id.fourthExample)

   }

}
//private val textExampleOne: TextView = itemView.findViewById(R.id.txt_ex_one)
////        private val textExampleTwo: TextView = itemView.findViewById(R.id.txt_ex_two)
////        private val textExampleThree: TextView = itemView.findViewById(R.id.txt_ex_three)
////        private val textExampleFour: TextView = itemView.findViewById(R.id.txt_ex_four)
//val expansionLayout: ExpansionLayout = itemView.findViewById(R.id.expansionLayout)